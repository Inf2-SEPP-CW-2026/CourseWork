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
    status = PerformanceStatus.CANCELLED;
  }

  public boolean checkIfEventIsTicketed() {
    return event != null && event.isTicketed();
  }

  public boolean checkIfTicketsLeft(int numTicketsToBuy) {
    return numTicketsTotal - numTicketsSold >= numTicketsToBuy;
  }

  public double getFinalTicketPrice() {
    return ticketPrice;
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
    return startDateTime != null && startDateTime.isAfter(LocalDateTime.now());
  }

  public boolean checkCreatedByEP(String email) {
    return event != null && email.equals(event.getOrganiserEmail());
  }

  public boolean hasActiveBookings() {
    return !getActiveBookings().isEmpty();
  }

  public Collection<Booking> getActiveBookings() {
    Collection<Booking> activeBookings = new ArrayList<>();
    for (Booking booking : bookings) {
      if (booking.isActive()) {
        activeBookings.add(booking);
      }
    }
    return activeBookings;
  }

  public String getBookingDetailsForRefund() {
    throw new UnsupportedOperationException("getBookingDetailsForRefund is not implemented yet.");
  }

  public void addBooking(Booking booking) {
    bookings.add(booking);
  }

  public void addNumTicketsSold(int numTickets) {
    numTicketsSold += numTickets;
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

  private String toSummaryString() {
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

  private String toDetailedString() {
    String eventTitle = getEventTitle() == null ? "Unknown event" : getEventTitle();
    String startTime =
        startDateTime == null ? "Unknown time" : startDateTime.format(SEARCH_RESULT_TIME_FORMATTER);
    String endTime =
        endDateTime == null ? "Unknown time" : endDateTime.format(SEARCH_RESULT_TIME_FORMATTER);
    String venue = venueAddress == null || venueAddress.isBlank() ? "Unknown venue" : venueAddress;
    String organiser = event == null || event.organiserDisplayName() == null ? "Unknown provider"
        : event.organiserDisplayName();
    String performerList = performerNames == null || performerNames.isEmpty() ? "Unknown performers"
        : String.join(", ", performerNames);
    String venueEnvironment = venueIsOutdoors ? "Outdoors" : "Indoors";
    String smokingPolicy = venueAllowsSmoking ? "Smoking allowed" : "Non-smoking";
    String ticketing = numTicketsTotal > 0 || ticketPrice > 0.0 ? "Ticketed" : "Non-ticketed";
    int ticketsRemaining = Math.max(numTicketsTotal - numTicketsSold, 0);
    String ticketInfo = "Ticketed".equals(ticketing)
        ? String.format("Price: %.2f | Tickets remaining: %d", ticketPrice, ticketsRemaining)
        : "No tickets required";
    String statusLabel = status == null ? "Unknown" : status.name();

    return String.format(
        "Performance ID: %d%nEvent: %s%nTime: %s to %s%nVenue: %s%nVenue details: capacity %d, %s, %s%nPerformers: %s%nProvider: %s%nTicketing: %s%nTicket details: %s%nStatus: %s",
        performanceID, eventTitle, startTime, endTime, venue, venueCapacity, venueEnvironment,
        smokingPolicy, performerList, organiser, ticketing, ticketInfo, statusLabel);
  }

  @Override
  public String toString() {
    return toString(false);
  }

  public String toString(boolean detailed) {
    return detailed ? toDetailedString() : toSummaryString();
  }
}
