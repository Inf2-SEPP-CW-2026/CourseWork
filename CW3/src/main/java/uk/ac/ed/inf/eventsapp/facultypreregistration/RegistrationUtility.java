package uk.ac.ed.inf.eventsapp.facultypreregistration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Lazy registration utility for faculty preregistration.
 */
public class RegistrationUtility {
  private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
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
    String normalizedEmail = normalizeEmail(email);

    FacultyMember registeredFacultyMember = registeredFacultyMembers.get(normalizedEmail);
    if (registeredFacultyMember != null) {
      return registeredFacultyMember;
    }

    FacultyRecord facultyRecord = findFacultyRecord(normalizedEmail);
    if (facultyRecord == null) {
      return null;
    }

    FacultyMember facultyMember =
        new FacultyMember(facultyRecord.email(), facultyRecord.password(), 0);
    registeredFacultyMembers.put(normalizeEmail(facultyRecord.email()), facultyMember);
    return facultyMember;
  }

  /**
   * Attempts a faculty login by lazily creating the account on the first matching login attempt.
   */
  public synchronized FacultyMember loginFacultyMember(String email, String password) {
    if (password == null || password.isBlank()) {
      throw new IllegalArgumentException("password must not be blank.");
    }

    FacultyMember facultyMember = registerFacultyMember(email);
    if (facultyMember == null) {
      return null;
    }

    facultyMember.recordLoginAttempt();
    if (!facultyMember.passwordMatches(password)) {
      return null;
    }

    return facultyMember;
  }

  public synchronized Collection<FacultyMember> getRegisteredFacultyMembers() {
    return Collections.unmodifiableList(List.copyOf(registeredFacultyMembers.values()));
  }

  private FacultyRecord findFacultyRecord(String email) throws IllegalStateException {
    for (FacultyRecord facultyRecord : readFacultyRecords()) {
      if (normalizeEmail(facultyRecord.email()).equals(email)) {
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

      List<FacultyRecord> parsedRecords = Files.readAllLines(facultyFile, StandardCharsets.UTF_8)
          .stream().map(String::trim).filter(line -> !line.isEmpty() && !line.startsWith("#"))
          .map(this::parseFacultyRecord).filter(Objects::nonNull).toList();

      cachedFacultyRecords = parsedRecords;
      cachedLastModifiedTime = lastModifiedTime;
      cachedFileSize = fileSize;
      return cachedFacultyRecords;
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to read faculty preregistration file.", exception);
    }
  }

  /**
   * Parses a single faculty preregistration line in the form {@code email,password}. Invalid rows
   * are ignored so a partially malformed input file can still be used.
   */
  private FacultyRecord parseFacultyRecord(String line) {
    String[] parts = line.split(",", 2);

    if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
      return null;
    }

    String email = parts[0].trim();
    String password = parts[1].trim();
    if (!email.matches(EMAIL_PATTERN)) {
      return null;
    }

    return new FacultyRecord(email, password);
  }

  private String normalizeEmail(String email) {
    return email.trim().toLowerCase();
  }

  private record FacultyRecord(String email, String password) {}
}
