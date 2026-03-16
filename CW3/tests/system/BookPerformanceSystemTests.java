package system;

import controller.BookingController;
import external.MockPaymentSystem;
import java.util.ArrayList;
import model.Booking;
import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import view.TextUserInterface;
import view.View;

/**
 * System-test scaffold for booking.
 */
public class BookPerformanceSystemTests {
  private BookingController bookingController;

  @BeforeEach
  void setUp() {
    View view = new TextUserInterface();
    bookingController = new BookingController(view, new MockPaymentSystem(), new ArrayList<Event>(),
        new ArrayList<Booking>());
  }

  @Test
  @Disabled("TODO: implement booking system tests.")
  void studentCanBookAnAvailablePerformance() {
    bookingController.bookPerformance();
  }
}
