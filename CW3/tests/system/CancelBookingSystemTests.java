package system;

import command.CancelBookingCommand;
import controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * System-test scaffold for booking cancellation.
 */
public class CancelBookingSystemTests {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @Test
    @Disabled("TODO: implement booking-cancellation system tests.")
    void studentCanCancelAnExistingBooking() {
        controller.runCommand(new CancelBookingCommand("booking-1"));
    }
}
