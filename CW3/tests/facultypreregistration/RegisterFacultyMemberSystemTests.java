package facultypreregistration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * System tests for the even-group lazy faculty registration feature.
 */
public class RegisterFacultyMemberSystemTests {
  @TempDir
  Path tempDir;

  @Test
  void firstLoginAttemptCreatesFacultyAccountAndStoresIt() throws IOException {
    Path facultyFile = createFacultyFile("xxxxrt@ed.ac.uk,encrypted-xxxxrt-password",
        "abcde@ed.ac.uk,encrypted-abcde-password");
    RegistrationUtility utility = new RegistrationUtility(facultyFile.toString());

    utility.registerFacultyMember("abcde@ed.ac.uk");

    assertEquals(1, utility.getRegisteredFacultyMembers().size(),
        "The first login attempt by a listed faculty member should create exactly one account.");
  }

  @Test
  void repeatedLoginAttemptsDoNotCreateDuplicateFacultyAccounts() throws IOException {
    Path facultyFile = createFacultyFile("xxxxrt@ed.ac.uk,encrypted-xxxxrt-password");
    RegistrationUtility utility = new RegistrationUtility(facultyFile.toString());

    utility.registerFacultyMember("xxxxrt@ed.ac.uk");
    utility.registerFacultyMember("xxxxrt@ed.ac.uk");

    assertEquals(1, utility.getRegisteredFacultyMembers().size(),
        "Repeated login attempts by the same faculty member should not create duplicates.");
  }

  @Test
  void unknownEmailDoesNotProduceAFacultyAccount() throws IOException {
    Path facultyFile = createFacultyFile("xxxxrt@ed.ac.uk,encrypted-xxxxrt-password");
    RegistrationUtility utility = new RegistrationUtility(facultyFile.toString());

    FacultyMember facultyMember = utility.registerFacultyMember("qwerty@ed.ac.uk");

    assertNull(facultyMember,
        "Only email addresses present in the configured faculty file should be registered.");
  }

  private Path createFacultyFile(String... lines) throws IOException {
    Path facultyFile = tempDir.resolve("faculty.csv");
    Files.write(facultyFile, java.util.List.of(lines));
    return facultyFile;
  }
}
