package uk.ac.ed.inf.eventsapp.facultypreregistration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for faculty-member accounts.
 */
public class TestFacultyMember {
  @Test
  void facultyMemberTracksInitialLoginAttempts() {
    FacultyMember facultyMember =
        new FacultyMember("xxxxrt@ed.ac.uk", "encrypted-xxxxrt-password", 1);

    assertEquals(1, facultyMember.getLoginAttempts(),
        "A lazily created faculty account should record the triggering login attempt.");
  }

  @Test
  void recordLoginAttemptIncrementsTheAttemptCount() {
    FacultyMember facultyMember =
        new FacultyMember("xxxxrt@ed.ac.uk", "encrypted-xxxxrt-password", 1);

    facultyMember.recordLoginAttempt();

    assertEquals(2, facultyMember.getLoginAttempts(),
        "Each additional login attempt should increment the faculty member's counter.");
  }

  @Test
  void passwordMatchesReturnsTrueWhenTheProvidedPasswordMatches() {
    FacultyMember facultyMember =
        new FacultyMember("xxxxrt@ed.ac.uk", "encrypted-xxxxrt-password", 0);

    assertTrue(facultyMember.passwordMatches("encrypted-xxxxrt-password"),
        "Password matching should succeed when the supplied password matches the stored one.");
  }

  @Test
  void passwordMatchesReturnsFalseWhenTheProvidedPasswordDoesNotMatch() {
    FacultyMember facultyMember =
        new FacultyMember("xxxxrt@ed.ac.uk", "encrypted-xxxxrt-password", 0);

    assertFalse(facultyMember.passwordMatches("wrong-password"),
        "Password matching should fail when the supplied password does not match.");
  }
}
