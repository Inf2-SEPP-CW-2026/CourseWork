package uk.ac.ed.inf.eventsapp.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import uk.ac.ed.inf.eventsapp.integration.VerificationSystem;
import uk.ac.ed.inf.eventsapp.model.AdminStaff;
import uk.ac.ed.inf.eventsapp.model.EntertainmentProvider;
import uk.ac.ed.inf.eventsapp.model.Event;
import uk.ac.ed.inf.eventsapp.model.Student;
import uk.ac.ed.inf.eventsapp.model.StudentPreferences;
import uk.ac.ed.inf.eventsapp.model.User;
import uk.ac.ed.inf.eventsapp.view.View;

/**
 * Handles login, logout, and provider registration.
 */
public class UserController extends Controller {
  public static final String PREREGISTERED_USERS_FILE_PATH = "preregistered-users.csv";
  public static final String PREREGISTERED_ADMIN_FILE_PATH = "preregistered-admin.csv";
  private static final String REGISTERED_EPS_FILE_PATH = "docs/registered-eps.txt";

  private final VerificationSystem verificationSystem;
  private final Collection<User> users;
  private final Collection<Event> events;

  public UserController(View view, VerificationSystem verificationSystem, Collection<User> users,
      Collection<Event> events) {
    super(view);
    this.verificationSystem = verificationSystem;
    this.users = users;
    this.events = events;
    addPreregisteredUsers();
  }

  /** Logs in a registered user by matching email and password. */
  public void login() {
    if (!checkCurrentUserIsGuest()) {
      view.displayError("You are already logged in.");
      return;
    }
    String email = view.getInput("Enter email:");
    String password = view.getInput("Enter password:");
    for (User user : users) {
      if (user.getEmail().equals(email) && user.passwordMatches(password)) {
        setCurrentUser(user);
        view.displaySuccess("Login successful.");
        return;
      }
    }
    view.displayError("Invalid email or password.");
  }

  /** Logs out the currently logged-in user. */
  public void logout() {
    if (checkCurrentUserIsGuest()) {
      view.displayError("You are not logged in.");
      return;
    }
    setCurrentUser(null);
    view.displaySuccess("Logout successful.");
  }

  /** Registers a new entertainment provider after verifying their business number. */
  public void registerEntertainmentProvider() {
    if (!checkCurrentUserIsGuest()) {
      view.displayError("You must be logged out to register.");
      return;
    }
    String email = view.getInput("Enter email:");
    String password = view.getInput("Enter password:");
    String orgName = view.getInput("Enter your organisation's name:");
    String businessNumber = view.getInput("Enter your business registration number:");
    String name = view.getInput("Enter your name:");
    String description = view.getInput("Enter description:");

    if (EPAccountAlreadyExists(email, orgName, businessNumber)) {
      view.displayError("An account with these details already exists.");
      return;
    }
    if (!verificationSystem.verifyEntertainmentProvider(businessNumber)) {
      view.displayError("Business registration number could not be verified.");
      return;
    }
    EntertainmentProvider newEP =
        new EntertainmentProvider(email, password, orgName, businessNumber, name, description);
    try (FileWriter writer = new FileWriter(REGISTERED_EPS_FILE_PATH, true)) {
      writer.write(String.format("%n%s,%s,%s,%s,%s,%s", email, password, orgName, businessNumber,
          name, description));
    } catch (IOException e) {
      view.displayError("Failed to save registration.");
      return;
    }
    addUser(newEP);
    view.displaySuccess("Registration successful.");
  }

  // Returns true if any existing EP shares the same email, org name, or business number.
  private boolean EPAccountAlreadyExists(String email, String orgName, String businessNumber) {
    for (User user : users) {
      if (user instanceof EntertainmentProvider ep) {
        if (ep.getEmail().equals(email) || ep.getOrgName().equals(orgName)
            || ep.getBusinessNumber().equals(businessNumber)) {
          return true;
        }
      }
    }
    return false;
  }

  public void editPreferences() {
    if (!checkCurrentUserIsStudent()) {
      view.displayError("Only students can edit preferences.");
      return;
    }
    Student student = (Student) getCurrentUser();
    boolean updated = false;
    while (!updated) {
      String input = view.getInput(
          "Enter preferences (5 digits, 1=yes, 0=no, order: Music Theater Dance Movie Sport)");
      updated = student.getPreferences().updatePreferences(input);
      if (!updated) {
        view.displayError("Invalid input. Enter exactly 5 characters using only 0 and 1.");
      }
    }
    view.displaySuccess("Preferences updated successfully.");
  }

  private void addUser(User user) {
    users.add(user);
  }

  /**
   * Loads pre-registered students, admin staff, and previously registered entertainment providers
   * from their respective CSV/text files.
   *
   * <p>
   * Expected formats (comma-separated, lines starting with # are ignored):
   * <ul>
   * <li>preregistered-users.csv: email,password,name,phoneNumber,preferences</li>
   * <li>preregistered-admin.csv: email,password,name</li>
   * <li>docs/registered-eps.txt: email,password,orgName,businessNumber,name,description</li>
   * </ul>
   */
  private void addPreregisteredUsers() {
    try {
      Path studentsPath = Path.of(PREREGISTERED_USERS_FILE_PATH);
      if (Files.exists(studentsPath)) {
        List<String[]> studentRecords = Files.readAllLines(studentsPath, StandardCharsets.UTF_8)
            .stream().map(String::trim).filter(l -> !l.isEmpty() && !l.startsWith("#"))
            .map(l -> l.split(",")).filter(Objects::nonNull).toList();
        for (String[] f : studentRecords) {
          StudentPreferences prefs = new StudentPreferences();
          if (f.length > 4) {
            prefs.updatePreferences(f[4].trim());
          }
          addUser(new Student(f[0].trim(), f[1].trim(), f[2].trim(), Integer.parseInt(f[3].trim()),
              prefs));
        }
      }

      Path adminPath = Path.of(PREREGISTERED_ADMIN_FILE_PATH);
      if (Files.exists(adminPath)) {
        List<String[]> adminRecords = Files.readAllLines(adminPath, StandardCharsets.UTF_8).stream()
            .map(String::trim).filter(l -> !l.isEmpty() && !l.startsWith("#"))
            .map(l -> l.split(",")).filter(Objects::nonNull).toList();
        for (String[] f : adminRecords) {
          addUser(new AdminStaff(f[0].trim(), f[1].trim(), f[2].trim()));
        }
      }

      Path epsPath = Path.of(REGISTERED_EPS_FILE_PATH);
      if (Files.exists(epsPath)) {
        List<String[]> epRecords = Files.readAllLines(epsPath, StandardCharsets.UTF_8).stream()
            .map(String::trim).filter(l -> !l.isEmpty() && !l.startsWith("#"))
            .map(l -> l.split(",")).filter(Objects::nonNull).toList();
        for (String[] f : epRecords) {
          addUser(new EntertainmentProvider(f[0].trim(), f[1].trim(), f[2].trim(), f[3].trim(),
              f[4].trim(), f[5].trim()));
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("Unable to read user preregistration file.", e);
    }
  }

  private EntertainmentProvider getEntertainmentProviderOwningEvent(long eventNumber) {
    throw new UnsupportedOperationException(
        "getEntertainmentProviderOwningEvent is not implemented yet.");
  }

  public VerificationSystem getVerificationSystem() {
    return verificationSystem;
  }

  public Collection<User> getUsers() {
    return users;
  }

  public Collection<Event> getEvents() {
    return events;
  }
}
