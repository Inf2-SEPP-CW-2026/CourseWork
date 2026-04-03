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
    status = BookingStatus.CANCELLEDBYSTUDENT;
  }

  public void cancelPaymentFailed() {
    status = BookingStatus.PAYMENTFAILED;
  }

  public void cancelByProvider() {
    status = BookingStatus.CANCELLEDBYPROVIDER;
  }

  public boolean checkBookedByStudent(String email) {
    return student != null && student.getEmail().equals(email);
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

  public boolean isActive() {
    return status == BookingStatus.ACTIVE;
  }

  public int getNumTickets() {
    return numTickets;
  }

  public double getAmountPaid() {
    return amountPaid;
  }

  public String getStudentEmail() {
    return student == null ? null : student.getEmail();
  }

  public int getStudentPhone() {
    return student == null ? 0 : student.getPhoneNumber();
  }

  public boolean hasBookingNumber(long candidateBookingNumber) {
    return bookingNumber == candidateBookingNumber;
  }

  public String getPerformanceEventTitle() {
    return performance == null ? null : performance.getEventTitle();
  }

  public String getPerformanceOrganiserEmail() {
    return performance == null ? null : performance.getOrganiserEmail();
  }

}
