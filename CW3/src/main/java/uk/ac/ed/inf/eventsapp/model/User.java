package uk.ac.ed.inf.eventsapp.model;

/**
 * Abstract User
 */
public abstract class User {
  private String email;
  private String password;

  protected User() {}

  protected User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public boolean passwordMatches(String candidatePassword) {
    return password != null && password.equals(candidatePassword);
  }
}
