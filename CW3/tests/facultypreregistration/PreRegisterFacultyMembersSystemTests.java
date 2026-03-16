package facultypreregistration;

import controller.Controller;
import facultypreregistration.command.PreRegisterFacultyMembersCommand;
import facultypreregistration.model.FacultyMigrationMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * System-test scaffold for faculty preregistration.
 */
public class PreRegisterFacultyMembersSystemTests {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @Test
    @Disabled("TODO: implement faculty preregistration system tests.")
    void facultyAccountsAreProvisionedLazilyForEvenGroups() {
        controller.runCommand(new PreRegisterFacultyMembersCommand(
                "mock-faculty.csv",
                FacultyMigrationMode.LAZY));
    }
}
