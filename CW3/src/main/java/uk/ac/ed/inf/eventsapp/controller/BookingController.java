package uk.ac.ed.inf.eventsapp.controller;

import external.PaymentSystem;
import java.util.Collection;
import uk.ac.ed.inf.eventsapp.model.Booking;
import uk.ac.ed.inf.eventsapp.model.Event;
import uk.ac.ed.inf.eventsapp.model.Performance;
import uk.ac.ed.inf.eventsapp.view.View;

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
    throw new UnsupportedOperationException("bookPerformance is not implemented yet.");
  }

  public void reviewPerformance() {
    throw new UnsupportedOperationException("reviewPerformance is not implemented yet.");
  }

  public void cancelBooking() {
    throw new UnsupportedOperationException("cancelBooking is not implemented yet.");
  }

  private void addBooking(Booking booking) {
    bookings.add(booking);
  }

  private Performance getPerformanceByID(long performanceID) {
    throw new UnsupportedOperationException("getPerformanceByID is not implemented yet.");
  }

  private boolean checkIfBookingPossible(Performance performance, int numTickets) {
    throw new UnsupportedOperationException("checkIfBookingPossible is not implemented yet.");
  }

  private Collection<Booking> findBookingsByEventID(long eventID) {
    throw new UnsupportedOperationException("findBookingsByEventID is not implemented yet.");
  }

  private Booking getBookingByNumber(long bookingNumber) {
    throw new UnsupportedOperationException("getBookingByNumber is not implemented yet.");
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
