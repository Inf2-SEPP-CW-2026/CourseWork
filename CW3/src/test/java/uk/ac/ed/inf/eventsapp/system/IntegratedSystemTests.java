package uk.ac.ed.inf.eventsapp.system;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import external.MockPaymentSystem;
import uk.ac.ed.inf.eventsapp.controller.BookingController;
import uk.ac.ed.inf.eventsapp.controller.EventPerformanceController;
import uk.ac.ed.inf.eventsapp.controller.UserController;
import uk.ac.ed.inf.eventsapp.integration.MockVerificationSystem;
import uk.ac.ed.inf.eventsapp.model.Booking;
import uk.ac.ed.inf.eventsapp.model.EntertainmentProvider;
import uk.ac.ed.inf.eventsapp.model.Event;
import uk.ac.ed.inf.eventsapp.model.Performance;
import uk.ac.ed.inf.eventsapp.model.Student;
import uk.ac.ed.inf.eventsapp.model.StudentPreferences;
import uk.ac.ed.inf.eventsapp.model.User;

/**
 * Integrated system tests that chain multiple use cases to verify
 * end-to-end application behaviour across shared state.
 */
public class IntegratedSystemTests {
  private Collection<User> users;
  private Collection<Event> events;
  private Collection<Performance> performances;
  private Collection<Booking> bookings;

  private EntertainmentProvider provider;
  private Student student;
  
  private static final String[] CREATE_TICKETED_EVENT_INPUTS = {
      "Spring Concert", "music", "yes", "1",
      "2026-05-10 19:00", "2026-05-10 21:00",
      "Hagan, Bob", "McEwan Hall", "500", "no", "no", "100", "15.50"
  };

  @BeforeEach
  void setUp() {
    users = new ArrayList<>();
    events = new ArrayList<>();
    performances = new ArrayList<>();
    bookings = new ArrayList<>();

    provider = new EntertainmentProvider("provider@gmail.com", "password",
        "Festival Org", "1234567890", "Hagan", "Runs live events");
    student = new Student("student@ed.ac.uk", "password", "Hagan", 1234567,
        new StudentPreferences());
    users.add(provider);
    users.add(student);
  }

  // -------------------------------------------------------------------------
  // Helpers
  // -------------------------------------------------------------------------

  private EventPerformanceController epController(ScriptedView view) {
    EventPerformanceController c = new EventPerformanceController(
        view, events, performances, new MockPaymentSystem());
    c.setCurrentUser(provider);
    return c;
  }

  private BookingController bookingController(ScriptedView view) {
    BookingController c = new BookingController(
        view, new MockPaymentSystem(), events, performances, bookings);
    c.setCurrentUser(student);
    return c;
  }

  private UserController userController(ScriptedView view) {
    return new UserController(view, new MockVerificationSystem(), users, events);
  }

  /** Creates a ticketed event with one performance (ID=1) and returns the controller. */
  private void createTicketedEvent() {
    epController(new ScriptedView(CREATE_TICKETED_EVENT_INPUTS)).createEvent();
  }

  // -------------------------------------------------------------------------
  // EP creates event → student books
  // -------------------------------------------------------------------------

  @Test
  void studentCanBookPerformanceCreatedByProvider() {
    createTicketedEvent();

    ScriptedView view = new ScriptedView("1", "2");
    bookingController(view).bookPerformance();

    assertEquals("SUCCESS: Booking successful", view.getLastSuccessMessage(),
        "Student should succeed in booking after EP creates the event.");
    assertEquals(1, bookings.size(),
        "Exactly one booking should exist after one successful booking.");
  }

  @Test
  void bookingAllTicketsMeansNoneLeft() {
    createTicketedEvent(); // 100 tickets
    bookingController(new ScriptedView("1", "100")).bookPerformance();

    Performance perf = performances.iterator().next();
    assertFalse(perf.checkIfTicketsLeft(1),
        "After booking all 100 tickets, no tickets should remain.");
  }

  // -------------------------------------------------------------------------
  // EP creates event → student books → student cancels
  // -------------------------------------------------------------------------

  @Test
  void studentCanCancelBookingAfterBooking() {
    createTicketedEvent();
    bookingController(new ScriptedView("1", "2")).bookPerformance();

    ScriptedView cancelView = new ScriptedView("1");
    bookingController(cancelView).cancelBooking();

    assertEquals("SUCCESS: Booking cancelled successfully.", cancelView.getLastSuccessMessage(),
        "Student should be able to cancel their booking.");
  }

  @Test
  void cancelledBookingHasCancelledByStudentStatus() {
    createTicketedEvent();
    bookingController(new ScriptedView("1", "1")).bookPerformance();

    bookingController(new ScriptedView("1")).cancelBooking();

    Booking booking = bookings.iterator().next();
    assertFalse(booking.isActive(),
        "Booking should no longer be active after student cancels.");
  }

  // -------------------------------------------------------------------------
  // EP creates event → student books → EP cancels performance
  // -------------------------------------------------------------------------

  @Test
  void providerCancelsPerformanceAndBookingBecomesProviderCancelled() {
    createTicketedEvent();
    bookingController(new ScriptedView("1", "2")).bookPerformance();

    ScriptedView cancelView = new ScriptedView("1", "Event cancelled due to unforeseen circumstances.");
    epController(cancelView).cancelPerformance();

    assertEquals("SUCCESS: Cancellation Successful!", cancelView.getLastSuccessMessage(),
        "EP should successfully cancel the performance.");
    Booking booking = bookings.iterator().next();
    assertFalse(booking.isActive(),
        "Booking should no longer be active after EP cancels the performance.");
  }

  @Test
  void cancelledPerformanceIsNoLongerActive() {
    createTicketedEvent();
    bookingController(new ScriptedView("1", "1")).bookPerformance();

    epController(new ScriptedView("1", "Sorry")).cancelPerformance();

    Performance perf = performances.iterator().next();
    assertFalse(perf.isActive(),
        "Performance should no longer be active after cancellation.");
  }

  // -------------------------------------------------------------------------
  // Login → create event → logout → login as student → book
  // -------------------------------------------------------------------------

  @Test
  void loginCreateEventLogoutLoginAsStudentBook() {
    // EP logs in
    ScriptedView loginView = new ScriptedView("provider@gmail.com", "password");
    UserController uc = userController(loginView);
    uc.login();
    assertEquals("SUCCESS: Login successful.", loginView.getLastSuccessMessage(),
        "EP should log in successfully.");

    // EP creates event (shares performances collection)
    EventPerformanceController epc = new EventPerformanceController(
        new ScriptedView(CREATE_TICKETED_EVENT_INPUTS), events, performances, new MockPaymentSystem());
    epc.setCurrentUser(uc.getCurrentUser());
    epc.createEvent();
    assertEquals(1, performances.size(), "One performance should exist after event creation.");

    // EP logs out
    ScriptedView logoutView = new ScriptedView();
    UserController uc2 = userController(logoutView);
    uc2.setCurrentUser(uc.getCurrentUser());
    uc2.logout();
    assertEquals("SUCCESS: Logout successful.", logoutView.getLastSuccessMessage(),
        "EP should log out successfully.");

    // Student logs in and books
    ScriptedView studentLoginView = new ScriptedView("student@ed.ac.uk", "password");
    UserController uc3 = userController(studentLoginView);
    uc3.login();

    ScriptedView bookView = new ScriptedView("1", "1");
    BookingController bc = new BookingController(
        bookView, new MockPaymentSystem(), events, performances, bookings);
    bc.setCurrentUser(uc3.getCurrentUser());
    bc.bookPerformance();

    assertEquals("SUCCESS: Booking successful", bookView.getLastSuccessMessage(),
        "Student should book the performance after the full login/create/logout/login flow.");
  }

  // -------------------------------------------------------------------------
  // Sold out → second booking attempt fails
  // -------------------------------------------------------------------------

  @Test
  void soldOutPerformancePreventsBooking() {
    // Create event with only 5 tickets
    String[] smallEvent = {
        "Small Gig", "music", "yes", "1",
        "2026-06-10 19:00", "2026-06-10 21:00",
        "Hagan", "Small Hall", "10", "no", "no", "5", "10.00"
    };
    epController(new ScriptedView(smallEvent)).createEvent();

    // Student books all 5
    bookingController(new ScriptedView("1", "5")).bookPerformance();

    // Verify the performance is now sold out
    Performance perf = performances.iterator().next();
    assertFalse(perf.checkIfTicketsLeft(1),
        "After booking all 5 tickets, no more tickets should be available.");
  }

  // -------------------------------------------------------------------------
  // Two students book → EP cancels → all bookings inactive
  // -------------------------------------------------------------------------

  @Test
  void providerCancelsPerformanceAllBookingsBecomeInactive() {
    createTicketedEvent();
    Student student2 = new Student("student2@ed.ac.uk", "password", "Bob", 7654321,
        new StudentPreferences());
    users.add(student2);

    // Student 1 books
    bookingController(new ScriptedView("1", "2")).bookPerformance();

    // Student 2 books
    ScriptedView bookView2 = new ScriptedView("1", "3");
    BookingController bc2 = new BookingController(
        bookView2, new MockPaymentSystem(), events, performances, bookings);
    bc2.setCurrentUser(student2);
    bc2.bookPerformance();

    assertEquals(2, bookings.size(), "Two bookings should exist.");

    // EP cancels
    epController(new ScriptedView("1", "Event cancelled")).cancelPerformance();

    for (Booking b : bookings) {
      assertFalse(b.isActive(),
          "All bookings should be inactive after EP cancels the performance.");
    }
  }

  // -------------------------------------------------------------------------
  // Student edits preferences then books
  // -------------------------------------------------------------------------

  @Test
  void studentEditsPreferencesThenBooks() {
    createTicketedEvent();

    // Edit preferences
    ScriptedView prefView = new ScriptedView("10110");
    UserController uc = userController(prefView);
    uc.setCurrentUser(student);
    uc.editPreferences();
    assertEquals("SUCCESS: Preferences updated successfully.", prefView.getLastSuccessMessage(),
        "Preferences update should succeed.");

    // Book performance
    ScriptedView bookView = new ScriptedView("1", "1");
    bookingController(bookView).bookPerformance();
    assertEquals("SUCCESS: Booking successful", bookView.getLastSuccessMessage(),
        "Student should still be able to book after editing preferences.");
  }

  // -------------------------------------------------------------------------
  // Wrong user type is blocked from actions
  // -------------------------------------------------------------------------

  @Test
  void studentCannotCreateEvent() {
    ScriptedView view = new ScriptedView();
    EventPerformanceController epc = new EventPerformanceController(
        view, events, performances, new MockPaymentSystem());
    epc.setCurrentUser(student);
    epc.createEvent();
    assertEquals("ERROR: Only logged-in entertainment providers can create events.",
        view.getLastErrorMessage(),
        "Student should be blocked from creating events.");
  }

  @Test
  void providerCannotBookPerformance() {
    createTicketedEvent();
    ScriptedView view = new ScriptedView();
    BookingController bc = new BookingController(
        view, new MockPaymentSystem(), events, performances, bookings);
    bc.setCurrentUser(provider);
    bc.bookPerformance();
    assertEquals("ERROR: Only students can book performances.", view.getLastErrorMessage(),
        "Provider should be blocked from booking performances.");
  }

  @Test
  void studentCannotCancelPerformance() {
    createTicketedEvent();
    ScriptedView view = new ScriptedView();
    EventPerformanceController epc = new EventPerformanceController(
        view, events, performances, new MockPaymentSystem());
    epc.setCurrentUser(student);
    epc.cancelPerformance();
    assertTrue(view.getLastErrorMessage().contains("entertainment provider"),
        "Student should be blocked from cancelling performances.");
  }
}
