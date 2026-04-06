package uk.ac.ed.inf.eventsapp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * Utilities for salted password hashing and verification.
 */
public final class PasswordUtils {
  private static final String ALGORITHM = "SHA-256";
  private static final String PREFIX = "sha256";
  private static final int SALT_BYTES = 16;

  private PasswordUtils() {}

  public static String hashPassword(String plainPassword) {
    if (plainPassword == null || plainPassword.isBlank()) {
      throw new IllegalArgumentException("Password must not be blank.");
    }

    byte[] salt = new byte[SALT_BYTES];
    new SecureRandom().nextBytes(salt);
    byte[] hash = deriveHash(plainPassword, salt);
    return PREFIX + "$" + Base64.getEncoder().encodeToString(salt) + "$"
        + Base64.getEncoder().encodeToString(hash);
  }

  public static boolean verifyPassword(String candidatePassword, String storedPasswordHash) {
    if (candidatePassword == null || storedPasswordHash == null || storedPasswordHash.isBlank()) {
      return false;
    }

    String[] parts = storedPasswordHash.split("\\$");
    if (parts.length != 3 || !PREFIX.equals(parts[0])) {
      return false;
    }

    try {
      byte[] salt = Base64.getDecoder().decode(parts[1]);
      byte[] expectedHash = Base64.getDecoder().decode(parts[2]);
      byte[] actualHash = deriveHash(candidatePassword, salt);
      return Arrays.equals(expectedHash, actualHash);
    } catch (IllegalArgumentException exception) {
      return false;
    }
  }

  public static boolean isStoredPasswordHash(String value) {
    return value != null && value.startsWith(PREFIX + "$");
  }

  public static String normalizePassword(String password) {
    if (password == null || password.isBlank()) {
      throw new IllegalArgumentException("Password must not be blank.");
    }

    if (isStoredPasswordHash(password)) {
      return password;
    }

    return hashPassword(password);
  }

  private static byte[] deriveHash(String password, byte[] salt) {
    try {
      MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
      digest.update(salt);
      digest.update(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
      return digest.digest();
    } catch (NoSuchAlgorithmException exception) {
      throw new IllegalStateException("Unable to hash password.", exception);
    }
  }
}
