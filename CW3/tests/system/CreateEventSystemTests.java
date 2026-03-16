package system;

import command.CreateEventCommand;
import controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * System-test scaffold for event creation.
 */
public class CreateEventSystemTests {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @Test
    @Disabled("TODO: implement event-creation system tests.")
    void registeredProviderCanCreateAnEvent() {
        controller.runCommand(new CreateEventCommand(
                "provider@example.com",
                "Live Music",
                "Evening concert"));
    }
}
