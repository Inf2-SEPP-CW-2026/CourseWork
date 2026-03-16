package facultypreregistration;

import model.User;

/**
 * Faculty-member user for the even-group preregistration feature.
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

  public int getLoginAttempts() {
    return loginAttempts;
  }
}
