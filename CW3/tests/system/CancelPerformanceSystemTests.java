package system;

import command.CancelPerformanceCommand;
import controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * System-test scaffold for performance cancellation.
 */
public class CancelPerformanceSystemTests {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @Test
    @Disabled("TODO: implement performance-cancellation system tests.")
    void providerCanCancelAnExistingPerformance() {
        controller.runCommand(new CancelPerformanceCommand(
                "provider@example.com",
                "performance-1",
                "Unexpected venue issue"));
    }
}
