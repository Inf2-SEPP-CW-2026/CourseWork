package facultypreregistration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Unit tests for the even-group faculty registration utility.
 */
public class TestRegistrationUtility {
  @TempDir
  Path tempDir;

  @Test
  void registerFacultyMemberReturnsMatchingFacultyFromConfiguredFile() throws IOException {
    Path facultyFile = createFacultyFile("xxxxrt@ed.ac.uk,encrypted-xxxxrt-password",
        "abcde@ed.ac.uk,encrypted-abcde-password");
    RegistrationUtility utility = new RegistrationUtility(facultyFile.toString());

    FacultyMember facultyMember = utility.registerFacultyMember("xxxxrt@ed.ac.uk");

    assertEquals("xxxxrt@ed.ac.uk", facultyMember.getEmail(),
        "A matching faculty email should be lazily registered from the configured file.");
  }

  @Test
  void registerFacultyMemberReturnsNullWhenEmailIsMissingFromConfiguredFile() throws IOException {
    Path facultyFile = createFacultyFile("xxxxrt@ed.ac.uk,encrypted-xxxxrt-password");
    RegistrationUtility utility = new RegistrationUtility(facultyFile.toString());

    FacultyMember facultyMember = utility.registerFacultyMember("qwerty@ed.ac.uk");

    assertNull(facultyMember,
        "A login attempt for an email outside the faculty file should not create an account.");
  }

  @Test
  void repeatedRegistrationReturnsSameFacultyInstance() throws IOException {
    Path facultyFile = createFacultyFile("xxxxrt@ed.ac.uk,encrypted-xxxxrt-password");
    RegistrationUtility utility = new RegistrationUtility(facultyFile.toString());

    FacultyMember firstRegistration = utility.registerFacultyMember("xxxxrt@ed.ac.uk");
    FacultyMember secondRegistration = utility.registerFacultyMember("xxxxrt@ed.ac.uk");

    assertSame(firstRegistration, secondRegistration,
        "Repeated login attempts should reuse the previously created faculty account.");
  }

  @Test
  void registerFacultyMemberReloadsFacultyFileAfterItsContentsChange() throws IOException {
    Path facultyFile = createFacultyFile("xxxxrt@ed.ac.uk,encrypted-xxxxrt-password");
    RegistrationUtility utility = new RegistrationUtility(facultyFile.toString());

    utility.registerFacultyMember("xxxxrt@ed.ac.uk");
    Files.write(facultyFile, java.util.List.of("xxxxrt@ed.ac.uk,encrypted-xxxxrt-password",
        "abcde@ed.ac.uk,encrypted-abcde-password"));

    FacultyMember facultyMember = utility.registerFacultyMember("abcde@ed.ac.uk");

    assertEquals("abcde@ed.ac.uk", facultyMember.getEmail(),
        "The faculty cache should refresh when the configured file contents change.");
  }

  @Test
  void concurrentRegistrationForSameEmailCreatesOnlyOneAccount() throws Exception {
    Path facultyFile = createFacultyFile("xxxxrt@ed.ac.uk,encrypted-xxxxrt-password");
    RegistrationUtility utility = new RegistrationUtility(facultyFile.toString());
    int concurrentAttempts = 8;
    ExecutorService executorService = Executors.newFixedThreadPool(concurrentAttempts);
    CountDownLatch ready = new CountDownLatch(concurrentAttempts);
    CountDownLatch start = new CountDownLatch(1);
    List<Future<FacultyMember>> futures = new ArrayList<>();
    FacultyMember firstFacultyMember;

    try {
      for (int i = 0; i < concurrentAttempts; i++) {
        futures.add(executorService.submit(() -> {
          ready.countDown();
          start.await();
          return utility.registerFacultyMember("xxxxrt@ed.ac.uk");
        }));
      }

      ready.await();
      start.countDown();

      firstFacultyMember = futures.get(0).get();
      for (Future<FacultyMember> future : futures) {
        assertSame(firstFacultyMember, future.get(),
            "Concurrent registration attempts should all reuse the same faculty account.");
      }
    } finally {
      executorService.shutdownNow();
    }

    assertEquals(1, utility.getRegisteredFacultyMembers().size(),
        "Concurrent registration attempts should not create duplicate faculty accounts.");
    assertEquals(concurrentAttempts, firstFacultyMember.getLoginAttempts(),
        "Every concurrent login attempt should be reflected in the faculty account.");
  }

  private Path createFacultyFile(String... lines) throws IOException {
    Path facultyFile = tempDir.resolve("faculty.csv");
    Files.write(facultyFile, java.util.List.of(lines));
    return facultyFile;
  }
}
