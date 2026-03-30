package uk.ac.ed.inf.eventsapp.model;

/**
 * Admin-staff user from the UML diagram.
 */
public class AdminStaff extends User {
  private String name;

  public AdminStaff() {}

  public AdminStaff(String email, String password, String name) {
    super(email, password);
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
