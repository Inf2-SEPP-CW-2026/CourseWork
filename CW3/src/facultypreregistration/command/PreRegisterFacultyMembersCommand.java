package facultypreregistration.command;

import command.AbstractCommand;
import controller.Controller;
import facultypreregistration.model.FacultyMigrationMode;

/**
 * Entry-point command for the experimental faculty preregistration use case.
 */
public class PreRegisterFacultyMembersCommand extends AbstractCommand {
    private final String sourcePath;
    private final FacultyMigrationMode migrationMode;

    public PreRegisterFacultyMembersCommand(
            String sourcePath,
            FacultyMigrationMode migrationMode) {
        super("Pre-register faculty members");
        this.sourcePath = sourcePath;
        this.migrationMode = migrationMode;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public FacultyMigrationMode getMigrationMode() {
        return migrationMode;
    }

    @Override
    public void execute(Controller controller) {
        throw notImplemented();
    }
}
