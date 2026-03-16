package system;

import command.LoginCommand;
import controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * System-test scaffold for the log-in use case.
 */
public class LogInSystemTests {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @Test
    @Disabled("TODO: implement log-in system tests.")
    void registeredUserCanLogIn() {
        controller.runCommand(new LoginCommand("student@example.com", "password"));
    }
}
