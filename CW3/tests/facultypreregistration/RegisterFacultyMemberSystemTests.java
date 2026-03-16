package facultypreregistration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * System-test scaffold for the even-group lazy faculty registration feature.
 */
public class RegisterFacultyMemberSystemTests {
  @Test
  @Disabled("TODO: implement lazy faculty-registration system tests.")
  void facultyMemberCanBeRegisteredFromConfiguredFile() {
    RegistrationUtility utility = new RegistrationUtility("mock-faculty.csv");

    assertNotNull(utility, "TODO: replace with end-to-end assertions.");
  }
}
