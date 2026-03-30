package uk.ac.ed.inf.eventsapp.unit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import uk.ac.ed.inf.eventsapp.model.Booking;
import uk.ac.ed.inf.eventsapp.model.BookingStatus;
import uk.ac.ed.inf.eventsapp.model.Event;
import uk.ac.ed.inf.eventsapp.model.EventType;
import uk.ac.ed.inf.eventsapp.model.EntertainmentProvider;
import uk.ac.ed.inf.eventsapp.model.EventPerformance;
import uk.ac.ed.inf.eventsapp.model.Student;
import uk.ac.ed.inf.eventsapp.model.Student;
import uk.ac.ed.inf.eventsapp.model.StudentPreferences;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unit-test scaffold for Booking.
 */
public class TestBooking {
  @Test
  @Disabled("TODO: implement Booking unit tests.")
  void bookingRetainsPaymentAndTicketData() {
    Student student = new Student("student@example.com", "Student", "Student Name", 123456789,
        new StudentPreferences());
    EntertainmentProvider provider = new EntertainmentProvider("provider@example.com", "secret",
        "Org", "1234567890", "Provider Name", "Description");
    Event event = new Event(1L, "Live Music", EventType.MUSIC, true, provider);
    EventPerformance performance = new EventPerformance(1L, LocalDateTime.of(2026, 4, 1, 19, 0),
        LocalDateTime.of(2026, 4, 1, 21, 0), java.util.List.of("Band"), "Main Hall", 120, false,
        false, 120, 0, 15.0, false, 0.0, uk.ac.ed.inf.eventsapp.model.PerformanceStatus.ACTIVE,
        event);
    Booking booking = new Booking(1L, 2, 30.0, LocalDateTime.of(2026, 3, 20, 10, 0),
        BookingStatus.ACTIVE, student, performance);

    assertNotNull(booking, "TODO: replace with behaviour-specific assertions.");
  }
}
