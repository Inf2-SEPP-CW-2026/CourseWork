package uk.ac.ed.inf.eventsapp.controller;

import external.PaymentSystem;
import java.time.LocalDateTime;
import java.util.Collection;
import uk.ac.ed.inf.eventsapp.model.Booking;
import uk.ac.ed.inf.eventsapp.model.Event;
import uk.ac.ed.inf.eventsapp.model.Performance;
import uk.ac.ed.inf.eventsapp.view.View;
import uk.ac.ed.inf.eventsapp.model.BookingStatus;
import uk.ac.ed.inf.eventsapp.model.Student;

/**
 * Handles booking, review, and booking cancellation workflows.
 */
public class BookingController extends Controller {
  private long nextBookingNumber;
  private final PaymentSystem paymentSystem;
  private final Collection<Event> events;
  private final Collection<Booking> bookings;

  public BookingController(View view, PaymentSystem paymentSystem, Collection<Event> events,
      Collection<Booking> bookings) {
    super(view);
    this.paymentSystem = paymentSystem;
    this.events = events;
    this.bookings = bookings;
    this.nextBookingNumber = 1L;
  }

  public void bookPerformance() {
    Performance performance = null;
    boolean bookingPossible = false;
    int numTicketsRequested = 0;

    if (!checkCurrentUserIsStudent()) {
      view.displayError("Only students can book performances.");
      return;
    }  

    while (performance == null || !bookingPossible) {
      long performanceID;
      try {
        performanceID = Long.parseLong(view.getInput("Enter performance ID"));
      } catch (NumberFormatException e) {
        view.displayError("Invalid performance ID");
        continue;
      }

      try {
        numTicketsRequested = Integer.parseInt(view.getInput("Enter number of tickets: "));
      } catch (NumberFormatException e) {
        view.displayError("Invalid number of tickets");
        continue;
      }

      performance = getPerformanceByID(performanceID);
      bookingPossible = false;

      if (performance == null) {
        view.displayError("Performance with given number does not exist.");
      } else {
        bookingPossible = checkIfBookingPossible(performance, numTicketsRequested);
      }
    }

    Student student = (Student) getCurrentUser();
    double amountPaid = numTicketsRequested * performance.getFinalTicketPrice();

    Booking booking = new Booking(getNextBookingNumber(), numTicketsRequested, amountPaid,
        LocalDateTime.now(), BookingStatus.ACTIVE, student, performance);
    nextBookingNumber++;

    performance.addBooking(booking);
    student.addBooking(booking);
    addBooking(booking);

    boolean paymentSuccessful = paymentSystem.processPayment(numTicketsRequested,
        performance.getEventTitle(), student.getEmail(), student.getPhoneNumber(),
        performance.getOrganiserEmail(), amountPaid);

    if (!paymentSuccessful) {
      view.displayError("There was an issue with payment.");
      booking.cancelPaymentFailed();
    } else {
      performance.addNumTicketsSold(numTicketsRequested);
      view.displaySuccess("Booking successful");
      view.displayBookingRecord(booking.generateBookingRecord());
    }
  }


  public void cancelBooking() {
    if (!checkCurrentUserIsStudent()) {
      view.displayError("Only students can cancel bookings.");
      return;
    }
    Student student = (Student) getCurrentUser();

    Booking booking = null;
    while (booking == null) {
      long bookingNumber;
      try {
        bookingNumber = Long.parseLong(view.getInput("Enter booking number to cancel: "));
      } catch (NumberFormatException e) {
        view.displayError("Invalid booking number");
        continue;
      }
      booking = getBookingByNumber(bookingNumber);
      if (booking == null) {
        view.displayError("Booking with given number does not exist");
      } else if (!booking.checkBookedByStudent(student.getEmail())) {
        view.displayError("You can only cancel your own bookings");
        booking = null;
      } else if (!booking.isActive()) {
        view.displayError("Booking is not active and cannot be cancelled");
        booking = null;
      }
    }

    boolean refundSuccessful = paymentSystem.processRefund(booking.getNumTickets(),
        booking.getPerformanceEventTitle(), student.getEmail(), student.getPhoneNumber(),
        booking.getPerformanceOrganiserEmail(), booking.getAmountPaid(), "");
    
    if (!refundSuccessful) {
      view.displayError("There was an issue processing the refund.");
      return;
    }

    booking.cancelByStudent();
    view.displaySuccess("Booking cancelled successfully.");  
    }


  private void addBooking(Booking booking) {
    bookings.add(booking);
  }

  private Performance getPerformanceByID(long performanceID) {
    for (Event event : events) {
      Performance performance = event.getPerformanceByID(performanceID);
      if (performance != null) {
        return performance;
      }
    }
    return null;
  }

  private boolean checkIfBookingPossible(Performance performance, int numTickets) {
    if (!performance.checkIfEventIsTicketed()) {
      view.displayError(
          "The requested performance's event is not ticketed. There is no need to book it.");
      return false;
    }
    if (!performance.checkIfTicketsLeft(numTickets)) {
      view.displayError("Requested performance has no tickets left");
      return false;
    }
    return true;
  }

  private Collection<Booking> findBookingsByEventID(long eventID) {
    throw new UnsupportedOperationException("findBookingsByEventID is not implemented yet.");
  }

  private Booking getBookingByNumber(long bookingNumber) {
    for (Booking booking : bookings) {
      if (booking.hasBookingNumber(bookingNumber)) {
        return booking;
      }
    }
    return null;
  }

  private long getNextBookingNumber() {
    return nextBookingNumber;
  }

  private PaymentSystem getPaymentSystem() {
    return paymentSystem;
  }

  private Collection<Event> getEvents() {
    return events;
  }

  private Collection<Booking> getBookings() {
    return bookings;
  }
}
