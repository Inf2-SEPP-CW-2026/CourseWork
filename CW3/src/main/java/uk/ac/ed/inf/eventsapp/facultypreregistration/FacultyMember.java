package uk.ac.ed.inf.eventsapp.facultypreregistration;

import uk.ac.ed.inf.eventsapp.model.User;

/**
 * Faculty-member user for preregistration.
 */
public class FacultyMember extends User {
  private int loginAttempts;

  public FacultyMember() {
    super();
  }

  public FacultyMember(String email, String password, int loginAttempts) {
    super(email, password);
    this.loginAttempts = loginAttempts;
  }

  public synchronized int getLoginAttempts() {
    return loginAttempts;
  }

  public synchronized void recordLoginAttempt() {
    loginAttempts++;
  }
}
