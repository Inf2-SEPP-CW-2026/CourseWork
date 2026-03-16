package model;

/**
 * Student account in the main system.
 */
public class Student extends User {
    private final int phoneNumber;
    private final StudentPreferences preferences;

    public Student(
            String userId,
            String displayName,
            String email,
            String passwordHash,
            int phoneNumber,
            StudentPreferences preferences) {
        super(userId, displayName, email, passwordHash);
        this.phoneNumber = phoneNumber;
        this.preferences = preferences;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public StudentPreferences getPreferences() {
        return preferences;
    }
}
