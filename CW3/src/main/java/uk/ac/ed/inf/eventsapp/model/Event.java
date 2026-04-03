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
    this.performances = new ArrayList<>();
  }

  public Event(long eventID, String title, EventType type, boolean isTicketed,
      EntertainmentProvider organiser) {
    this.eventID = eventID;
    this.title = title;
    this.type = type;
    this.isTicketed = isTicketed;
    this.organiser = organiser;
    this.performances = new ArrayList<>();
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
    ArrayList<String> performanceInfoList = new ArrayList<>();
    for (Performance performance : getPerformancesByDate(searchDateTime)) {
      performanceInfoList.add(performance.toString());
    }
    return performanceInfoList;
  }

  private String getOrganiserName() {
    if (organiser == null) {
      return null;
    }

    if (organiser.getOrgName() != null && !organiser.getOrgName().isBlank()) {
      return organiser.getOrgName();
    }

    if (organiser.getName() != null && !organiser.getName().isBlank()) {
      return organiser.getName();
    }

    return organiser.getEmail();
  }

  public String getOrganiserEmail() {
    return organiser == null ? null : organiser.getEmail();
  }

  public double getAverageReviewRating() {
    return 0.0;
  }

  public Collection<String> getAllPerformanceReivews() {
    throw new UnsupportedOperationException("getAllPerformanceReivews is not implemented yet.");
    // no need to implement
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

  @Override
  public String toString() {
    return title;
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

  public boolean matchesPreferences(StudentPreferences preferences) {
    if (preferences == null || type == null) {
      return false;
    }

    return switch (type) {
      case MUSIC -> preferences.isPreferMusicEvents();
      case THEATRE -> preferences.isPreferTheaterEvents();
      case DANCE -> preferences.isPreferDanceEvents();
      case MOVIE -> preferences.isPreferMovieEvents();
      case SPORTS -> preferences.isPreferSportsEvents();
      case GAMES -> false;
    };
  }

  public boolean isTicketed() {
    return isTicketed;
  }

  public long getEventID() {
    return eventID;
  }

  public Collection<Performance> getPerformancesByDate(LocalDateTime searchDateTime) {
    ArrayList<Performance> eligiblePerformances = new ArrayList<>();
    for (Performance performance : performances) {
      if (performance.isActive()
          && searchDateTime.toLocalDate().equals(performance.getStartDateTime().toLocalDate())) {
        eligiblePerformances.add(performance);
      }
    }
    return eligiblePerformances;
  }

  String organiserDisplayName() {
    return getOrganiserName();
  }
}
