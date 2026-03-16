package system;

import command.SponsorPerformanceCommand;
import controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * System-test scaffold for sponsorship.
 */
public class SponsorPerformanceSystemTests {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @Test
    @Disabled("TODO: implement sponsorship system tests.")
    void sponsorCanFundAPerformance() {
        controller.runCommand(new SponsorPerformanceCommand(
                "Alumni Association",
                "performance-1",
                500.0));
    }
}
