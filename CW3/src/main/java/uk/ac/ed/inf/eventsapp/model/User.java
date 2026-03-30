package uk.ac.ed.inf.eventsapp.model;

/** Base type for application users. */
public abstract class User {
  private String email;
  private String password;

  /** Creates an empty user. */
  protected User() {}

  /**
   * Creates a user with the supplied credentials.
   *
   * @param email user email address
   * @param password user password used for authentication
   */
  protected User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  /** @return the user's email address */
  public String getEmail() {
    return email;
  }

  /**
   * Checks whether a candidate password matches the stored password.
   *
   * @param candidatePassword password provided for authentication
   * @return {@code true} if the password matches, otherwise {@code false}
   */
  public boolean passwordMatches(String candidatePassword) {
    return password != null && password.equals(candidatePassword);
  }
}
