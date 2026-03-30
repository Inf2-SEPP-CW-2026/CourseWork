package uk.ac.ed.inf.eventsapp.system;

import uk.ac.ed.inf.eventsapp.controller.BookingController;
import external.MockPaymentSystem;
import java.util.ArrayList;
import uk.ac.ed.inf.eventsapp.model.Booking;
import uk.ac.ed.inf.eventsapp.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import uk.ac.ed.inf.eventsapp.view.TextUserInterface;
import uk.ac.ed.inf.eventsapp.view.View;

/**
 * System-test scaffold for performance reviews.
 */
public class ReviewPerformanceSystemTests {
  private BookingController bookingController;

  @BeforeEach
  void setUp() {
    View view = new TextUserInterface();
    bookingController = new BookingController(view, new MockPaymentSystem(), new ArrayList<Event>(),
        new ArrayList<Booking>());
  }

  @Test
  @Disabled("TODO: implement performance-review system tests.")
  void studentCanReviewAttendedPerformance() {
    bookingController.reviewPerformance();
  }
}
