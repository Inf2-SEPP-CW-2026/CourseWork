package uk.ac.ed.inf.eventsapp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Performance entity from the UML diagram.
 */
public class Performance {
  private long performanceID;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  private Collection<String> performerNames;
  private String venueAddress;
  private int venueCapacity;
  private boolean venueIsOutdoors;
  private boolean venueAllowsSmoking;
  private int numTicketsTotal;
  private int numTicketsSold;
  private double ticketPrice;
  private boolean isSponsored;
  private double sponsoredAmount;
  private Collection<Integer> reviewRatings;
  private Collection<String> reviewComments;
  private PerformanceStatus status;
  private Event event;
  private final Collection<Booking> bookings;

  public Performance() {
    this.performerNames = new ArrayList<>();
    this.reviewRatings = new ArrayList<>();
    this.reviewComments = new ArrayList<>();
    this.bookings = new ArrayList<>();
  }

  public Performance(long performanceID, LocalDateTime startDateTime, LocalDateTime endDateTime,
      Collection<String> performerNames, String venueAddress, int venueCapacity,
      boolean venueIsOutdoors, boolean venueAllowsSmoking, int numTicketsTotal, int numTicketsSold,
      double ticketPrice, boolean isSponsored, double sponsoredAmount, PerformanceStatus status,
      Event event) {
    this.performanceID = performanceID;
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
    this.performerNames = new ArrayList<>(performerNames);
    this.venueAddress = venueAddress;
    this.venueCapacity = venueCapacity;
    this.venueIsOutdoors = venueIsOutdoors;
    this.venueAllowsSmoking = venueAllowsSmoking;
    this.numTicketsTotal = numTicketsTotal;
    this.numTicketsSold = numTicketsSold;
    this.ticketPrice = ticketPrice;
    this.isSponsored = isSponsored;
    this.sponsoredAmount = sponsoredAmount;
    this.reviewRatings = new ArrayList<>();
    this.reviewComments = new ArrayList<>();
    this.status = status;
    this.event = event;
    this.bookings = new ArrayList<>();
  }

  public void cancel() {
    throw new UnsupportedOperationException("cancel is not implemented yet.");
  }

  public boolean checkIfEventIsTicketed() {
    throw new UnsupportedOperationException("checkIfEventIsTicketed is not implemented yet.");
  }

  public boolean checkIfTicketsLeft(int numTicketsToBuy) {
    throw new UnsupportedOperationException("checkIfTicketsLeft is not implemented yet.");
  }

  public double getFinalTicketPrice() {
    throw new UnsupportedOperationException("getFinalTicketPrice is not implemented yet.");
  }

  public String getOrganiserEmail() {
    return event == null ? null : event.getOrganiserEmail();
  }

  public String getEventTitle() {
    return event == null ? null : event.titleValue();
  }

  public boolean checkHasNotHappenedYet() {
    throw new UnsupportedOperationException("checkHasNotHappenedYet is not implemented yet.");
  }

  public boolean checkCreatedByEP(String email) {
    throw new UnsupportedOperationException("checkCreatedByEP is not implemented yet.");
  }

  public boolean hasActiveBookings() {
    throw new UnsupportedOperationException("hasActiveBookings is not implemented yet.");
  }

  public String getBookingDetailsForRefund() {
    throw new UnsupportedOperationException("getBookingDetailsForRefund is not implemented yet.");
  }

  public void sponsor(double amount) {
    throw new UnsupportedOperationException("sponsor is not implemented yet.");
  }

  public void review(int rating, String comment) {
    throw new UnsupportedOperationException("review is not implemented yet.");
  }

  public void addBooking(Booking booking) {
    bookings.add(booking);
  }

  @Override
  public String toString() {
    throw new UnsupportedOperationException("toString is not implemented yet.");
  }
}
