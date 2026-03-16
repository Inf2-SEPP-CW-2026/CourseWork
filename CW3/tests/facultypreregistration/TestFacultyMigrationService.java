package facultypreregistration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import facultypreregistration.command.PreRegisterFacultyMembersCommand;
import facultypreregistration.model.FacultyMigrationMode;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unit-test scaffold for the faculty preregistration feature.
 */
public class TestFacultyMigrationService {
    @Test
    @Disabled("TODO: implement faculty-migration unit tests.")
    void evenGroupUsesLazyMigrationMode() {
        PreRegisterFacultyMembersCommand command = new PreRegisterFacultyMembersCommand(
                "mock-faculty.csv",
                FacultyMigrationMode.LAZY);

        assertEquals(FacultyMigrationMode.LAZY, command.getMigrationMode(),
                "TODO: replace with behaviour-specific assertions.");
    }
}
