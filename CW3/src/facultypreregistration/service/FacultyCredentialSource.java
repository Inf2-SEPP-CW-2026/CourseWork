package facultypreregistration.service;

import facultypreregistration.model.FacultyCredentialRecord;
import java.util.List;

/**
 * Reads faculty credentials from a supplied data source.
 */
public interface FacultyCredentialSource {
    List<FacultyCredentialRecord> readAllRecords(String sourcePath);
}
