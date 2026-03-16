package system;

import command.ReviewPerformanceCommand;
import controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * System-test scaffold for performance reviews.
 */
public class ReviewPerformanceSystemTests {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @Test
    @Disabled("TODO: implement performance-review system tests.")
    void studentCanReviewAttendedPerformance() {
        controller.runCommand(new ReviewPerformanceCommand(
                "student@example.com",
                "performance-1",
                5,
                "Excellent event"));
    }
}
