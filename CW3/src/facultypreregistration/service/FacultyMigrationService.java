package facultypreregistration.service;

import facultypreregistration.model.FacultyMember;
import facultypreregistration.model.FacultyMigrationMode;
import java.util.List;

/**
 * Coordinates eager or lazy faculty account migration.
 */
public interface FacultyMigrationService {
    FacultyMigrationMode getMigrationMode();

    List<FacultyMember> preRegisterFacultyMembers(String sourcePath);

    FacultyMember provisionOnFirstLogin(String email);
}
