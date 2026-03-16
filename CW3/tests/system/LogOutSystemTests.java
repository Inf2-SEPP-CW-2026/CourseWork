package system;

import command.LogoutCommand;
import controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * System-test scaffold for the log-out use case.
 */
public class LogOutSystemTests {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @Test
    @Disabled("TODO: implement log-out system tests.")
    void loggedInUserCanLogOut() {
        controller.runCommand(new LogoutCommand());
    }
}
