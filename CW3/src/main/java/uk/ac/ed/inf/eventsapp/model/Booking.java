package uk.ac.ed.inf.eventsapp.model;

import java.time.LocalDateTime;

/**
 * Booking entity from the UML diagram.
 */
public class Booking {
  private long bookingNumber;
  private int numTickets;
  private double amountPaid;
  private LocalDateTime bookingDateTime;
  private BookingStatus status;
  private Student student;
  private Performance performance;

  public Booking() {};

  public Booking(long bookingNumber, int numTickets, double amountPaid,
      LocalDateTime bookingDateTime, BookingStatus status, Student student,
      Performance performance) {
    this.bookingNumber = bookingNumber;
    this.numTickets = numTickets;
    this.amountPaid = amountPaid;
    this.bookingDateTime = bookingDateTime;
    this.status = status;
    this.student = student;
    this.performance = performance;
  }

  public void cancelByStudent() {
    throw new UnsupportedOperationException("cancelByStudent is not implemented yet.");
  }

  public void cancelPaymentFailed() {
    status = BookingStatus.PAYMENTFAILED;
  }

  public void cancelByProvider() {
    throw new UnsupportedOperationException("cancelByProvider is not implemented yet.");
  }

  public boolean checkBookedByStudent(String email) {
    throw new UnsupportedOperationException("checkBookedByStudent is not implemented yet.");
  }

  public String getStudentDetails() {
    throw new UnsupportedOperationException("getStudentDetails is not implemented yet.");
  }

  public String generateBookingRecord() {
    return "Booking #" + bookingNumber + "\nStudent: " + student.getName() + "\nEmail: "
        + student.getEmail() + "\nPhone: " + student.getPhoneNumber() + "\nEvent: "
        + performance.getEventTitle() + "\nPerformance: " + performance.toString() + "\nTickets: "
        + numTickets + "\nAmount paid: £" + amountPaid;
  }


}
