package system;

import command.SearchPerformancesCommand;
import controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * System-test scaffold for performance search.
 */
public class SearchPerformancesSystemTests {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @Test
    @Disabled("TODO: implement performance-search system tests.")
    void studentCanSearchForPerformances() {
        controller.runCommand(new SearchPerformancesCommand("music"));
    }
}
