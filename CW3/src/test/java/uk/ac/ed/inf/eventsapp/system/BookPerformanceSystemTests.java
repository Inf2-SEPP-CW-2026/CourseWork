package uk.ac.ed.inf.eventsapp.system;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import external.MockPaymentSystem;
import external.PaymentSystem;
import uk.ac.ed.inf.eventsapp.controller.BookingController;
import uk.ac.ed.inf.eventsapp.model.*;

public class BookPerformanceSystemTests {
  private EntertainmentProvider provider;
  private Student student;
  private Event ticketedEvent;
  private Event nonTicketedEvent;
  private Performance futurePerformance;
  private Performance nonTicketedPerformance;
  private Collection<Performance> performances;
  private Collection<Booking> bookings;

  @BeforeEach
  void setUp() {
    provider = new EntertainmentProvider("provider@gmail.com", "password", "EooEle", "123",
        "Provider", "This is EooEle");
    student =
        new Student("student@ed.ac.uk", "password", "Alice", 1234567, new StudentPreferences());

    LocalDateTime start = LocalDateTime.now().plusDays(7);
    ticketedEvent = new Event(1L, "Live Music", EventType.MUSIC, true, provider);
    nonTicketedEvent = new Event(2L, "Free Show", EventType.THEATRE, false, provider);

    futurePerformance = new Performance(1L, start, start.plusHours(2), List.of("Band"), "Hall", 100,
        false, false, 100, 0, 15.0, PerformanceStatus.ACTIVE, ticketedEvent);
    nonTicketedPerformance = new Performance(2L, start, start.plusHours(2), List.of("Actor"),
        "Stage", 50, false, false, 0, 0, 0.0, PerformanceStatus.ACTIVE, nonTicketedEvent);

    performances = new ArrayList<>();
    performances.add(futurePerformance);
    performances.add(nonTicketedPerformance);
    bookings = new ArrayList<>();
  }

  // --- Test

  @Test
  void studentCanBookAvailablePerformance() {
    ScriptedView view = new ScriptedView("1", "2");
    BookingController controller = new BookingController(view, new MockPaymentSystem(),
        new ArrayList<>(), performances, bookings);
    controller.setCurrentUser(student);

    controller.bookPerformance();

    assertEquals("SUCCESS: Booking successful", view.getLastSuccessMessage(),
        "Student should receive a success message after booking.");
    assertNotNull(view.getLastDisplayedBookingRecord(),
        "A booking record should be displayed after successful booking.");
  }

  @Test
  void bookingExactlyAllRemainingTickets() {
    ScriptedView view = new ScriptedView("1", "100");
    BookingController controller = new BookingController(view, new MockPaymentSystem(),
        new ArrayList<>(), performances, bookings);
    controller.setCurrentUser(student);

    controller.bookPerformance();

    assertEquals("SUCCESS: Booking successful", view.getLastSuccessMessage(),
        "Booking exactly all remaining tickets should succeed.");
  }

  // --- Access control ---

  @Test
  void onlyStudentsCanBookPerformances() {
    ScriptedView view = new ScriptedView();
    BookingController controller = new BookingController(view, new MockPaymentSystem(),
        new ArrayList<>(), performances, bookings);
    controller.setCurrentUser(provider);

    controller.bookPerformance();

    assertEquals("ERROR: Only students can book performances.", view.getLastErrorMessage(),
        "Non-students should be rejected.");
  }

  @Test
  void guestCannotBookPerformance() {
    ScriptedView view = new ScriptedView();
    BookingController controller = new BookingController(view, new MockPaymentSystem(),
        new ArrayList<>(), performances, bookings);

    controller.bookPerformance();

    assertEquals("ERROR: Only students can book performances.", view.getLastErrorMessage(),
        "Guest (no user) should be rejected.");
  }

  // --- Input validation ---

  @Test
  void invalidPerformanceIdFormatShowsError() {
    // "abc" → NumberFormatException. Then valid: ID 1, 1 ticket.
    ScriptedView view = new ScriptedView("abc", "1", "1");
    BookingController controller = new BookingController(view, new MockPaymentSystem(),
        new ArrayList<>(), performances, bookings);
    controller.setCurrentUser(student);

    controller.bookPerformance();

    assertTrue(view.getErrorMessages().contains("ERROR: Invalid performance ID"),
        "Non-numeric performance ID should show an error.");
  }

  @Test
  void invalidTicketCountFormatShowsError() {
    // ID 1 ok, then "abc" for tickets → error. Then valid: ID 1, 1 ticket.
    ScriptedView view = new ScriptedView("1", "abc", "1", "1");
    BookingController controller = new BookingController(view, new MockPaymentSystem(),
        new ArrayList<>(), performances, bookings);
    controller.setCurrentUser(student);

    controller.bookPerformance();

    assertTrue(view.getErrorMessages().contains("ERROR: Invalid number of tickets"),
        "Non-numeric ticket count should show an error.");
  }

  // --- Business logic errors ---

  @Test
  void bookingWithNonExistentPerformanceIdShowsError() {
    ScriptedView view = new ScriptedView("999", "1", "1", "1");
    BookingController controller = new BookingController(view, new MockPaymentSystem(),
        new ArrayList<>(), performances, bookings);
    controller.setCurrentUser(student);

    controller.bookPerformance();

    assertTrue(
        view.getErrorMessages().contains("ERROR: Performance with given number does not exist."),
        "Non-existent performance ID should show an error.");
  }

  @Test
  void bookingNonTicketedPerformanceShowsError() {
    ScriptedView view = new ScriptedView("2", "1", "1", "1");
    BookingController controller = new BookingController(view, new MockPaymentSystem(),
        new ArrayList<>(), performances, bookings);
    controller.setCurrentUser(student);

    controller.bookPerformance();

    assertTrue(view.getErrorMessages().stream().anyMatch(e -> e.contains("not ticketed")),
        "Booking a non-ticketed performance should show an error.");
  }

  @Test
  void bookingSoldOutPerformanceShowsError() {
    LocalDateTime start = LocalDateTime.now().plusDays(7);
    Performance soldOut = new Performance(3L, start, start.plusHours(2), List.of("Band"), "Hall",
        50, false, false, 50, 50, 15.0, PerformanceStatus.ACTIVE, ticketedEvent);
    performances.add(soldOut);

    ScriptedView view = new ScriptedView("3", "1", "1", "1");
    BookingController controller = new BookingController(view, new MockPaymentSystem(),
        new ArrayList<>(), performances, bookings);
    controller.setCurrentUser(student);

    controller.bookPerformance();

    assertTrue(view.getErrorMessages().stream().anyMatch(e -> e.contains("no tickets left")),
        "Booking a sold-out performance should show an error.");
  }

  @Test
  void bookingMoreTicketsThanAvailableShowsError() {
    LocalDateTime start = LocalDateTime.now().plusDays(7);
    Performance fewTickets = new Performance(4L, start, start.plusHours(2), List.of("Band"), "Hall",
        50, false, false, 10, 0, 15.0, PerformanceStatus.ACTIVE, ticketedEvent);
    performances.add(fewTickets);

    // ID 4 (10 tickets), request 20 → error. Then ID 1, 1 ticket → success.
    ScriptedView view = new ScriptedView("4", "20", "1", "1");
    BookingController controller = new BookingController(view, new MockPaymentSystem(),
        new ArrayList<>(), performances, bookings);
    controller.setCurrentUser(student);

    controller.bookPerformance();

    assertTrue(view.getErrorMessages().stream().anyMatch(e -> e.contains("no tickets left")),
        "Requesting more tickets than available should show an error.");
  }

  // --- Payment failure ---

  @Test
  void paymentFailureCancelsBooking() {
    PaymentSystem failingPayment = new PaymentSystem() {
      @Override
      public boolean processPayment(int n, String t, String se, int sp, String ep, double a) {
        return false;
      }

      @Override
      public boolean processRefund(int n, String t, String se, int sp, String ep, double a,
          String m) {
        return false;
      }
    };

    ScriptedView view = new ScriptedView("1", "1");
    BookingController controller =
        new BookingController(view, failingPayment, new ArrayList<>(), performances, bookings);
    controller.setCurrentUser(student);

    controller.bookPerformance();

    assertTrue(view.getErrorMessages().stream().anyMatch(e -> e.contains("issue with payment")),
        "Payment failure should display an error.");
  }

  // --- State verification ---

  @Test
  void successfulBookingAddsToBookingsCollection() {
    ScriptedView view = new ScriptedView("1", "2");
    BookingController controller = new BookingController(view, new MockPaymentSystem(),
        new ArrayList<>(), performances, bookings);
    controller.setCurrentUser(student);

    controller.bookPerformance();

    assertEquals(1, bookings.size(),
        "Booking should be added to the bookings collection after success.");
  }

  @Test
  void bookingRecordContainsStudentAndEventDetails() {
    ScriptedView view = new ScriptedView("1", "2");
    BookingController controller = new BookingController(view, new MockPaymentSystem(),
        new ArrayList<>(), performances, bookings);
    controller.setCurrentUser(student);

    controller.bookPerformance();

    String record = view.getLastDisplayedBookingRecord();
    assertNotNull(record, "Booking record should be displayed.");
    assertTrue(record.contains("Alice"), "Booking record should contain student name.");
    assertTrue(record.contains("Live Music"), "Booking record should contain event title.");
  }

  @Test
  void bookingSingleTicketSucceeds() {
    ScriptedView view = new ScriptedView("1", "1");
    BookingController controller = new BookingController(view, new MockPaymentSystem(),
        new ArrayList<>(), performances, bookings);
    controller.setCurrentUser(student);

    controller.bookPerformance();

    assertEquals("SUCCESS: Booking successful", view.getLastSuccessMessage(),
        "Booking a single ticket should succeed.");
  }
}
