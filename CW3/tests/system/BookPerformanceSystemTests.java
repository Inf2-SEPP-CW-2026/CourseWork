package system;

import command.BookPerformanceCommand;
import controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * System-test scaffold for booking.
 */
public class BookPerformanceSystemTests {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @Test
    @Disabled("TODO: implement booking system tests.")
    void studentCanBookAnAvailablePerformance() {
        controller.runCommand(new BookPerformanceCommand(
                "student@example.com",
                "performance-1",
                2));
    }
}
