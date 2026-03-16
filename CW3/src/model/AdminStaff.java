package model;

/**
 * Administrative user account.
 */
public class AdminStaff extends User {
    public AdminStaff(String userId, String displayName, String email, String passwordHash) {
        super(userId, displayName, email, passwordHash);
    }
}
