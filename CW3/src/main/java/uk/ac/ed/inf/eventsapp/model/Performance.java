package uk.ac.ed.inf.eventsapp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Performance entity from the UML diagram.
 */
public class Performance {
  private static final DateTimeFormatter SEARCH_RESULT_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
  private PerformanceStatus status;
  private Event event;
  private final Collection<Booking> bookings;

  public Performance() {
    this.performerNames = new ArrayList<>();
    this.bookings = new ArrayList<>();
  }

  public Performance(long performanceID, LocalDateTime startDateTime, LocalDateTime endDateTime,
      Collection<String> performerNames, String venueAddress, int venueCapacity,
      boolean venueIsOutdoors, boolean venueAllowsSmoking, int numTicketsTotal, int numTicketsSold,
      double ticketPrice, PerformanceStatus status, Event event) {
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

  public LocalDateTime getStartDateTime() {
    return startDateTime;
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

  public void addBooking(Booking booking) {
    bookings.add(booking);
  }

  boolean isActive() {
    return status == PerformanceStatus.ACTIVE;
  }

  boolean hasID(long candidatePerformanceID) {
    return performanceID == candidatePerformanceID;
  }

  boolean hasSameSchedule(LocalDateTime candidateStartDateTime,
      LocalDateTime candidateEndDateTime) {
    return startDateTime != null && endDateTime != null
        && startDateTime.equals(candidateStartDateTime) && endDateTime.equals(candidateEndDateTime);
  }

  @Override
  public String toString() {
    String eventTitle = getEventTitle() == null ? "Unknown event" : getEventTitle();
    String startTime =
        startDateTime == null ? "Unknown time" : startDateTime.format(SEARCH_RESULT_TIME_FORMATTER);
    String endTime =
        endDateTime == null ? "Unknown time" : endDateTime.format(SEARCH_RESULT_TIME_FORMATTER);
    String venue = venueAddress == null || venueAddress.isBlank() ? "Unknown venue" : venueAddress;
    String organiser = event == null || event.organiserDisplayName() == null ? "Unknown provider"
        : event.organiserDisplayName();
    double eventReviewAverage = event == null ? 0.0 : event.getAverageReviewRating();

    return String.format(
        "Performance ID: %d | Event: %s | Time: %s to %s | Venue: %s | Provider: %s | Event review average: %.1f",
        performanceID, eventTitle, startTime, endTime, venue, organiser, eventReviewAverage);
  }
}
