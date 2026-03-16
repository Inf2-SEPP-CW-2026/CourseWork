package command;

import controller.Controller;
import model.StudentPreferences;

/**
 * Updates the saved student preferences.
 */
public class EditPreferencesCommand extends AbstractCommand {
    private final String studentEmail;
    private final StudentPreferences preferences;

    public EditPreferencesCommand(String studentEmail, StudentPreferences preferences) {
        super("Edit preferences");
        this.studentEmail = studentEmail;
        this.preferences = preferences;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public StudentPreferences getPreferences() {
        return preferences;
    }

    @Override
    public void execute(Controller controller) {
        throw notImplemented();
    }
}
