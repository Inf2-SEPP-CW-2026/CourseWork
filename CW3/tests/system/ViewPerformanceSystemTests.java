package system;

import command.ViewPerformanceCommand;
import controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * System-test scaffold for performance viewing.
 */
public class ViewPerformanceSystemTests {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @Test
    @Disabled("TODO: implement performance-view system tests.")
    void userCanViewPerformanceDetails() {
        controller.runCommand(new ViewPerformanceCommand("performance-1"));
    }
}
