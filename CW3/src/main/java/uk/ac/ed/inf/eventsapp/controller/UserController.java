package uk.ac.ed.inf.eventsapp.controller;

import java.util.Collection;

import uk.ac.ed.inf.eventsapp.integration.VerificationSystem;
import uk.ac.ed.inf.eventsapp.model.EntertainmentProvider;
import uk.ac.ed.inf.eventsapp.model.Event;
import uk.ac.ed.inf.eventsapp.model.User;
import uk.ac.ed.inf.eventsapp.view.View;
import uk.ac.ed.inf.eventsapp.model.Student;

/**
 * Handles login, logout, and provider registration.
 */
public class UserController extends Controller {
  public static final String PREREGISTERED_USERS_FILE_PATH = "docs/preregistered-users.txt";
  public static final String PREREGISTERED_ADMIN_FILE_PATH = "docs/preregistered-admin.txt";

  private final VerificationSystem verificationSystem;
  private final Collection<User> users;
  private final Collection<Event> events;

  public UserController(View view, VerificationSystem verificationSystem, Collection<User> users,
      Collection<Event> events) {
    super(view);
    this.verificationSystem = verificationSystem;
    this.users = users;
    this.events = events;
  }

  public void login() {
    throw new UnsupportedOperationException("login is not implemented yet.");
  }

  public void logout() {
    throw new UnsupportedOperationException("logout is not implemented yet.");
  }

  public void registerEntertainmentProvider() {
    throw new UnsupportedOperationException(
        "registerEntertainmentProvider is not implemented yet.");
  }

  private boolean EPAccountAlreadyExists(String email, String orgName, String businessNumber) {
    throw new UnsupportedOperationException("EPAccountAlreadyExists is not implemented yet.");
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
          "Enter preferences (5 digits, 1=yes, 0=no, order: Music Theater Dance Movie Sport): ");
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

  private void addPreregisteredUsers() {
    throw new UnsupportedOperationException("addPreregisteredUsers is not implemented yet.");
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
