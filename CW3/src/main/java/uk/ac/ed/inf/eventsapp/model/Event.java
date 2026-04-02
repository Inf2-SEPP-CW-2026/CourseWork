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
    throw new UnsupportedOperationException("createPerformance is not implemented yet.");
  }

  public Performance getPerformanceByID(long performanceID) {
    throw new UnsupportedOperationException("getPerformanceByID is not implemented yet.");
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
    throw new UnsupportedOperationException("hasPerformanceAtSameTimes is not implemented yet.");
  }

  private void addPerformance(Performance performance) {
    performances.add(performance);
  }

  String titleValue() {
    return title;
  }

  @Override
  public String toString() {
    throw new UnsupportedOperationException("toString is not implemented yet.");
  }
}
