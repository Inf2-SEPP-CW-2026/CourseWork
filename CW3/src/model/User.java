package model;

/**
 * Abstract user from the UML diagram.
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
}
