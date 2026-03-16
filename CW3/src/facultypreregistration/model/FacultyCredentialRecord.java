package facultypreregistration.model;

/**
 * Raw faculty credentials as supplied by the university.
 */
public class FacultyCredentialRecord {
    private final String email;
    private final String encryptedPassword;

    public FacultyCredentialRecord(String email, String encryptedPassword) {
        this.email = email;
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }
}
