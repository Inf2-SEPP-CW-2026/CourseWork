package facultypreregistration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lazy registration utility for faculty preregistration.
 */
public class RegistrationUtility {
  private final String filePath;
  private final Map<String, FacultyMember> registeredFacultyMembers;
  private List<FacultyRecord> cachedFacultyRecords;
  private FileTime cachedLastModifiedTime;
  private long cachedFileSize;

  public RegistrationUtility(String filePath) throws IllegalArgumentException {
    if (filePath == null || filePath.isBlank()) {
      throw new IllegalArgumentException("filePath must not be blank.");
    }
    this.filePath = filePath;
    this.registeredFacultyMembers = new HashMap<>();

    this.cachedFacultyRecords = List.of();
    this.cachedLastModifiedTime = null;
    this.cachedFileSize = -1L;
  }

  /**
   * Lazily creates a faculty account when a matching email appears in the configured file
   */
  public synchronized FacultyMember registerFacultyMember(String email)
      throws IllegalArgumentException {
    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("email must not be blank.");
    }

    FacultyMember registeredFacultyMember = registeredFacultyMembers.get(email);
    if (registeredFacultyMember != null) {
      registeredFacultyMember.recordLoginAttempt();
      return registeredFacultyMember;
    }

    FacultyRecord facultyRecord = findFacultyRecord(email);
    if (facultyRecord == null) {
      return null;
    }

    FacultyMember facultyMember =
        new FacultyMember(facultyRecord.email(), facultyRecord.password(), 1);
    registeredFacultyMembers.put(facultyRecord.email(), facultyMember);
    return facultyMember;
  }

  public synchronized Collection<FacultyMember> getRegisteredFacultyMembers() {
    return Collections.unmodifiableList(List.copyOf(registeredFacultyMembers.values()));
  }

  private FacultyRecord findFacultyRecord(String email) throws IllegalStateException {
    for (FacultyRecord facultyRecord : readFacultyRecords()) {
      if (facultyRecord.email().equals(email)) {
        return facultyRecord;
      }
    }

    return null;
  }

  /**
   * Reads and parses the faculty preregistration file, refreshing the cache only when the file
   * metadata changes.
   */
  private List<FacultyRecord> readFacultyRecords() throws IllegalStateException {
    Path facultyFile = Path.of(filePath);

    try {
      FileTime lastModifiedTime = Files.getLastModifiedTime(facultyFile);
      long fileSize = Files.size(facultyFile);

      if (cachedLastModifiedTime != null && cachedLastModifiedTime.equals(lastModifiedTime)
          && cachedFileSize == fileSize) {
        return cachedFacultyRecords;
      }

      List<FacultyRecord> parsedRecords = Files.readAllLines(facultyFile).stream().map(String::trim)
          .filter(line -> !line.isEmpty() && !line.startsWith("#")).map(this::parseFacultyRecord)
          .toList();

      cachedFacultyRecords = parsedRecords;
      cachedLastModifiedTime = lastModifiedTime;
      cachedFileSize = fileSize;
      return cachedFacultyRecords;
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to read faculty preregistration file.", exception);
    }
  }

  /**
   * Parses a single faculty preregistration line in the form {@code email,password}.
   */
  private FacultyRecord parseFacultyRecord(String line) throws IllegalStateException {
    String[] parts = line.split(",", 2);

    if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
      throw new IllegalStateException("Faculty preregistration file contains an invalid line.");
    }

    return new FacultyRecord(parts[0].trim(), parts[1].trim());
  }

  private record FacultyRecord(String email, String password) {}
}
