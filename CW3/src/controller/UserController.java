package controller;

import java.util.Collection;

import integration.VerificationSystem;
import model.EntertainmentProvider;
import model.Event;
import model.User;
import view.View;

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
    throw new UnsupportedOperationException("editPreferences is not implemented yet.");
  }

  private void addUser(User user) {
    users.add(user);
  }

  private void addPreRegisteredUsers() {
    throw new UnsupportedOperationException("addPreRegisteredUsers is not implemented yet.");
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
