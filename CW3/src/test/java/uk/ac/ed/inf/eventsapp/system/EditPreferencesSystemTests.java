package uk.ac.ed.inf.eventsapp.system;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.ed.inf.eventsapp.controller.UserController;
import uk.ac.ed.inf.eventsapp.integration.MockVerificationSystem;
import uk.ac.ed.inf.eventsapp.model.*;

public class EditPreferencesSystemTests {
  private Student student;
  private EntertainmentProvider provider;

  @BeforeEach
  void setUp() {
    provider = new EntertainmentProvider("provider@gmail.com", "password", "EooEle", "123",
        "Provider", "This is EooEle");
    student =
        new Student("student@ed.ac.uk", "password", "Alice", 1234567, new StudentPreferences());
  }

  @Test
  void studentCanUpdatePreferences() {
    ScriptedView view = new ScriptedView("11010");
    UserController controller = new UserController(view, new MockVerificationSystem(),
        new ArrayList<>(), new ArrayList<>());
    controller.setCurrentUser(student);

    controller.editPreferences();

    assertEquals("SUCCESS: Preferences updated successfully.", view.getLastSuccessMessage(),
        "Student should receive a success message after updating preferences.");
  }

  @Test
  void allZerosIsValidInput() {
    ScriptedView view = new ScriptedView("00000");
    UserController controller = new UserController(view, new MockVerificationSystem(),
        new ArrayList<>(), new ArrayList<>());
    controller.setCurrentUser(student);

    controller.editPreferences();

    assertEquals("SUCCESS: Preferences updated successfully.", view.getLastSuccessMessage(),
        "All zeros should be accepted as valid preferences.");
  }

  @Test
  void allOnesIsValidInput() {
    ScriptedView view = new ScriptedView("11111");
    UserController controller = new UserController(view, new MockVerificationSystem(),
        new ArrayList<>(), new ArrayList<>());
    controller.setCurrentUser(student);

    controller.editPreferences();

    assertEquals("SUCCESS: Preferences updated successfully.", view.getLastSuccessMessage(),
        "All ones should be accepted as valid preferences.");
  }

  // --- Access control ---

  @Test
  void onlyStudentsCanEditPreferences() {
    ScriptedView view = new ScriptedView();
    UserController controller = new UserController(view, new MockVerificationSystem(),
        new ArrayList<>(), new ArrayList<>());
    controller.setCurrentUser(provider);

    controller.editPreferences();

    assertEquals("ERROR: Only students can edit preferences.", view.getLastErrorMessage(),
        "Non-students should be rejected.");
  }

  @Test
  void guestCannotEditPreferences() {
    ScriptedView view = new ScriptedView();
    UserController controller = new UserController(view, new MockVerificationSystem(),
        new ArrayList<>(), new ArrayList<>());

    controller.editPreferences();

    assertEquals("ERROR: Only students can edit preferences.", view.getLastErrorMessage(),
        "Guest (no user) should be rejected.");
  }

  // --- Input validation ---

  @Test
  void invalidPreferenceInputIsRejectedAndRetried() {
    ScriptedView view = new ScriptedView("abc", "11010");
    UserController controller = new UserController(view, new MockVerificationSystem(),
        new ArrayList<>(), new ArrayList<>());
    controller.setCurrentUser(student);

    controller.editPreferences();

    assertTrue(view.getErrorMessages().stream().anyMatch(e -> e.contains("Invalid input")),
        "Invalid preference string should show an error.");
    assertEquals("SUCCESS: Preferences updated successfully.", view.getLastSuccessMessage(),
        "Valid retry should succeed.");
  }

  @Test
  void tooShortInputIsRejected() {
    ScriptedView view = new ScriptedView("1101", "11010");
    UserController controller = new UserController(view, new MockVerificationSystem(),
        new ArrayList<>(), new ArrayList<>());
    controller.setCurrentUser(student);

    controller.editPreferences();

    assertTrue(view.getErrorMessages().stream().anyMatch(e -> e.contains("Invalid input")),
        "Input with wrong length (4 chars) should show an error.");
  }

  @Test
  void tooLongInputIsRejected() {
    ScriptedView view = new ScriptedView("110100", "11010");
    UserController controller = new UserController(view, new MockVerificationSystem(),
        new ArrayList<>(), new ArrayList<>());
    controller.setCurrentUser(student);

    controller.editPreferences();

    assertTrue(view.getErrorMessages().stream().anyMatch(e -> e.contains("Invalid input")),
        "Input with wrong length (6 chars) should show an error.");
  }

  @Test
  void nonBinaryDigitsRejected() {
    ScriptedView view = new ScriptedView("12345", "11010");
    UserController controller = new UserController(view, new MockVerificationSystem(),
        new ArrayList<>(), new ArrayList<>());
    controller.setCurrentUser(student);

    controller.editPreferences();

    assertTrue(view.getErrorMessages().stream().anyMatch(e -> e.contains("Invalid input")),
        "Input with non-binary digits should show an error.");
  }

  @Test
  void emptyStringInputIsRejected() {
    ScriptedView view = new ScriptedView("", "11010");
    UserController controller = new UserController(view, new MockVerificationSystem(),
        new ArrayList<>(), new ArrayList<>());
    controller.setCurrentUser(student);

    controller.editPreferences();

    assertTrue(view.getErrorMessages().stream().anyMatch(e -> e.contains("Invalid input")),
        "Empty string should be rejected as invalid input.");
  }

  // --- State verification ---

  @Test
  void preferencesAreActuallySavedAfterUpdate() {
    ScriptedView view = new ScriptedView("10110");
    UserController controller = new UserController(view, new MockVerificationSystem(),
        new ArrayList<>(), new ArrayList<>());
    controller.setCurrentUser(student);

    controller.editPreferences();

    StudentPreferences prefs = student.getPreferences();
    assertTrue(prefs.isPreferMusicEvents(), "Music preference should be set.");
    assertFalse(prefs.isPreferTheaterEvents(), "Theater preference should not be set.");
    assertTrue(prefs.isPreferDanceEvents(), "Dance preference should be set.");
    assertTrue(prefs.isPreferMovieEvents(), "Movie preference should be set.");
    assertFalse(prefs.isPreferSportsEvents(), "Sports preference should not be set.");
  }
}
