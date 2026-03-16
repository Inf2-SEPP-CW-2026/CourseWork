package facultypreregistration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unit-test scaffold for the even-group faculty registration utility.
 */
public class TestRegistrationUtility {
  @Test
  @Disabled("TODO: implement RegistrationUtility unit tests.")
  void registrationUtilityCanBeConstructed() {
    RegistrationUtility utility = new RegistrationUtility("mock-faculty.csv");

    assertNotNull(utility, "TODO: replace with behaviour-specific assertions.");
  }
}
