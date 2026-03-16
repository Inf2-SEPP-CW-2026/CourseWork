package facultypreregistration.model;

import model.User;

/**
 * Faculty user handled only by the experimental preregistration feature.
 */
public class FacultyMember extends User {
    private final boolean passwordChangeRecommended;

    public FacultyMember(
            String userId,
            String displayName,
            String email,
            String passwordHash,
            boolean passwordChangeRecommended) {
        super(userId, displayName, email, passwordHash);
        this.passwordChangeRecommended = passwordChangeRecommended;
    }

    public boolean isPasswordChangeRecommended() {
        return passwordChangeRecommended;
    }
}
