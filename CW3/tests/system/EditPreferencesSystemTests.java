package system;

import command.EditPreferencesCommand;
import controller.Controller;
import java.util.HashSet;
import model.StudentPreferences;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * System-test scaffold for preference editing.
 */
public class EditPreferencesSystemTests {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @Test
    @Disabled("TODO: implement preference-editing system tests.")
    void studentCanUpdatePreferences() {
        controller.runCommand(new EditPreferencesCommand(
                "student@example.com",
                new StudentPreferences(new HashSet<String>())));
    }
}
