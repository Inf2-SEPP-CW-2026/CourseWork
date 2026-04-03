package uk.ac.ed.inf.eventsapp.system;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import external.MockPaymentSystem;
import uk.ac.ed.inf.eventsapp.controller.BookingController;
import uk.ac.ed.inf.eventsapp.view.TextUserInterface;
import uk.ac.ed.inf.eventsapp.view.View;

/**
 * System-test scaffold for booking cancellation.
 */
public class CancelBookingSystemTests {
  private BookingController bookingController;

  @BeforeEach
  @SuppressWarnings("unused")
  void setUp() {
    View view = new TextUserInterface();
    bookingController = new BookingController(view, new MockPaymentSystem(), new ArrayList<>(),
        new ArrayList<>(), new ArrayList<>());
  }

  @Test
  @Disabled("TODO: implement booking-cancellation system tests.")
  void studentCanCancelAnExistingBooking() {
    bookingController.cancelBooking();
  }
}
