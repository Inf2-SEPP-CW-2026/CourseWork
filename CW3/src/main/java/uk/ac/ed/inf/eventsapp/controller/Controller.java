package uk.ac.ed.inf.eventsapp.controller;

import java.util.Collection;
import uk.ac.ed.inf.eventsapp.model.AdminStaff;
import uk.ac.ed.inf.eventsapp.model.EntertainmentProvider;
import uk.ac.ed.inf.eventsapp.model.Student;
import uk.ac.ed.inf.eventsapp.model.User;
import uk.ac.ed.inf.eventsapp.view.View;

/**
 * Shared base for the concrete controllers in the UML uk.ac.ed.inf.eventsapp.model.
 */
public abstract class Controller {
  protected final View view;
  protected User currentUser;

  protected Controller(View view) {
    this.view = view;
  }

  public User getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(User currentUser) {
    this.currentUser = currentUser;
  }

  protected boolean checkCurrentUserIsGuest() {
    return currentUser == null;
  }

  protected boolean checkCurrentUserIsAdmin() {
    return currentUser instanceof AdminStaff;
  }

  protected boolean checkCurrentUserIsStudent() {
    return currentUser instanceof Student;
  }

  protected boolean checkCurrentUserIsEntertainmentProvider() {
    return currentUser instanceof EntertainmentProvider;
  }

  public <T> int selectFromMenu(Collection<T> options, String prompt) {
    throw new UnsupportedOperationException("Menu selection is not implemented yet.");
  }
}
