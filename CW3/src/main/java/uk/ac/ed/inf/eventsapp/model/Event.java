package uk.ac.ed.inf.eventsapp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Event aggregate from the UML diagram.
 */
public class Event {
  private long eventID;
  private String title;
  private EventType type;
  private boolean isTicketed;
  private EntertainmentProvider organiser;
  private final Collection<Performance> performances;

  public Event() {
    this.performances = new ArrayList<Performance>();
  }

  public Event(long eventID, String title, EventType type, boolean isTicketed,
      EntertainmentProvider organiser) {
    this.eventID = eventID;
    this.title = title;
    this.type = type;
    this.isTicketed = isTicketed;
    this.organiser = organiser;
    this.performances = new ArrayList<Performance>();
  }

  public Performance createPerformance(long performanceID, LocalDateTime startDateTime,
      LocalDateTime endDateTime, Collection<String> performerNames, String venueAddress,
      int venueCapacity, boolean venueIsOutdoors, boolean venueAllowsSmoking, int numTickets,
      double ticketPrice) {
    if (startDateTime == null || endDateTime == null || !endDateTime.isAfter(startDateTime)) {
      throw new IllegalArgumentException("Performance dates/times are invalid.");
    }

    if (performerNames == null || performerNames.isEmpty()) {
      throw new IllegalArgumentException("At least one performer name is required.");
    }

    if (venueAddress == null || venueAddress.isBlank()) {
      throw new IllegalArgumentException("Venue address is required.");
    }

    if (venueCapacity <= 0) {
      throw new IllegalArgumentException("Venue capacity must be a positive integer.");
    }

    if (hasPerformanceAtSameTimes(startDateTime, endDateTime)) {
      throw new IllegalArgumentException(
          "A performance already exists for the same dates and times.");
    }

    int totalTickets = isTicketed ? numTickets : 0;
    double finalTicketPrice = isTicketed ? ticketPrice : 0.0;
    if (isTicketed && (numTickets < 0 || ticketPrice < 0.0)) {
      throw new IllegalArgumentException(
          "Ticket count and ticket price must be valid non-negative numbers.");
    }

    Performance performance = new Performance(performanceID, startDateTime, endDateTime,
        performerNames, venueAddress, venueCapacity, venueIsOutdoors, venueAllowsSmoking,
        totalTickets, 0, finalTicketPrice, PerformanceStatus.ACTIVE, this);
    addPerformance(performance);
    return performance;
  }

  public Performance getPerformanceByID(long performanceID) {
    for (Performance performance : performances) {
      if (performance.hasID(performanceID)) {
        return performance;
      }
    }
    return null;
  }

  public Collection<String> getInfoOfPerformancesOnDate(LocalDateTime searchDateTime) {
    throw new UnsupportedOperationException("getInfoOfPerformancesOnDate is not implemented yet.");
  }

  private String getOrganiserName() {
    return organiser == null ? null : organiser.getName();
  }

  public String getOrganiserEmail() {
    return organiser == null ? null : organiser.getEmail();
  }

  private boolean hasPerformanceAtSameTimes(LocalDateTime startDateTime,
      LocalDateTime endDateTime) {
    for (Performance performance : performances) {
      if (performance.hasSameSchedule(startDateTime, endDateTime)) {
        return true;
      }
    }
    return false;
  }

  private void addPerformance(Performance performance) {
    performances.add(performance);
  }

  String titleValue() {
    return title;
  }

  public boolean hasTitle(String candidateTitle) {
    return title != null && title.equalsIgnoreCase(candidateTitle);
  }

  public boolean hasPerformanceAtTimes(LocalDateTime startDateTime, LocalDateTime endDateTime) {
    return hasPerformanceAtSameTimes(startDateTime, endDateTime);
  }

  @Override
  public String toString() {
    return title;
  }
}
