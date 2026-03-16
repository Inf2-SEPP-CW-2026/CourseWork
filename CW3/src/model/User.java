package model;

/**
 * Base type for application users.
 */
public abstract class User {
    private final String userId;
    private final String displayName;
    private final String email;
    private final String passwordHash;

    protected User(String userId, String displayName, String email, String passwordHash) {
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
