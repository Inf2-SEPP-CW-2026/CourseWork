package facultypreregistration;

import model.User;

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

  /* Thread Safe */
  public synchronized int getLoginAttempts() {
    return loginAttempts;
  }

  public synchronized void recordLoginAttempt() {
    loginAttempts++;
  }
}
