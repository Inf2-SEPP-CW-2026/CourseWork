package unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.HashSet;
import model.Booking;
import model.BookingStatus;
import model.EntertainmentProvider;
import model.Event;
import model.EventPerformance;
import model.PerformanceStatus;
import model.Student;
import model.StudentPreferences;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unit-test scaffold for Booking.
 */
public class TestBooking {
    @Test
    @Disabled("TODO: implement Booking unit tests.")
    void bookingRetainsPaymentAndTicketData() {
        Student student = new Student(
                "student-1",
                "Student",
                "student@example.com",
                "hashed",
                123456789,
                new StudentPreferences(new HashSet<String>()));
        EntertainmentProvider provider = new EntertainmentProvider(
                "ep-1",
                "Provider",
                "provider@example.com",
                "hashed",
                "1234567890");
        Event event = new Event("event-1", "Live Music", "Evening concert", provider);
        EventPerformance performance = new EventPerformance(
                "performance-1",
                event,
                LocalDateTime.of(2026, 4, 1, 19, 0),
                "Main Hall",
                120,
                15.0,
                PerformanceStatus.PUBLISHED);
        Booking booking = new Booking(
                "booking-1",
                student,
                performance,
                2,
                30.0,
                LocalDateTime.of(2026, 3, 20, 10, 0),
                BookingStatus.ACTIVE);

        assertEquals("booking-1", booking.getBookingId(),
                "TODO: replace with behaviour-specific assertions.");
    }
}
