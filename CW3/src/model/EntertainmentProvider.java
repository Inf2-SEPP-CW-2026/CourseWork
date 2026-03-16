package model;

/**
 * Entertainment provider account.
 */
public class EntertainmentProvider extends User {
    private final String businessRegistrationNumber;

    public EntertainmentProvider(
            String userId,
            String displayName,
            String email,
            String passwordHash,
            String businessRegistrationNumber) {
        super(userId, displayName, email, passwordHash);
        this.businessRegistrationNumber = businessRegistrationNumber;
    }

    public String getBusinessRegistrationNumber() {
        return businessRegistrationNumber;
    }
}
