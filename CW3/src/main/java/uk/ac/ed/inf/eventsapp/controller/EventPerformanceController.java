package uk.ac.ed.inf.eventsapp.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import external.PaymentSystem;
import uk.ac.ed.inf.eventsapp.model.Booking;
import uk.ac.ed.inf.eventsapp.model.EntertainmentProvider;
import uk.ac.ed.inf.eventsapp.model.Event;
import uk.ac.ed.inf.eventsapp.model.EventType;
import uk.ac.ed.inf.eventsapp.model.Performance;
import uk.ac.ed.inf.eventsapp.model.Student;
import uk.ac.ed.inf.eventsapp.model.StudentPreferences;
import uk.ac.ed.inf.eventsapp.util.InputParsers;
import uk.ac.ed.inf.eventsapp.view.View;

/**
 * Handles event creation, search, view, cancellation, and sponsorship.
 */
public class EventPerformanceController extends Controller {
  private long nextEventID;
  private long nextPerformanceID;
  private final Collection<Event> events;
  private final Collection<Performance> performances;
  private final PaymentSystem paymentSystem;

  public EventPerformanceController(View view, Collection<Event> events,
      Collection<Performance> performances, PaymentSystem paymentSystem) {
    super(view);
    this.events = events;
    this.performances = performances;
    this.nextEventID = 1L;
    this.nextPerformanceID = 1L;
    this.paymentSystem = paymentSystem;
  }

  public Event createEvent() {
    if (!checkCurrentUserIsEntertainmentProvider()) {
      view.displayError("Only logged-in entertainment providers can create events.");
      return null;
    }

    EntertainmentProvider organiser = (EntertainmentProvider) currentUser;

    String title = view.getInput("Enter event title").trim();
    if (title.isEmpty()) {
      view.displayError("Event title is required.");
      return null;
    }

    EventType type = InputParsers.parseEventType(
        view.getInput("Enter event type (music, theatre, dance, movie, sports, games)"));
    if (type == null) {
      view.displayError("Invalid event type.");
      return null;
    }

    Boolean isTicketed =
        InputParsers.parseBoolean(view.getInput("Is the event ticketed? (yes/no)"));
    if (isTicketed == null) {
      view.displayError("Ticketed must be specified as yes or no.");
      return null;
    }

    Integer performanceCount =
        InputParsers.parsePositiveInteger(view.getInput("How many performances?"));
    if (performanceCount == null) {
      view.displayError("Number of performances must be a positive integer.");
      return null;
    }

    Event event = new Event(getNextEventID(), title, type, isTicketed, organiser);
    long nextPerformanceId = getNextPerformanceID();

    for (int performanceIndex = 1; performanceIndex <= performanceCount; performanceIndex++) {
      LocalDateTime startDateTime = InputParsers.parseDateTime(
          view.getInput("Performance " + performanceIndex + " start (yyyy-MM-dd HH:mm)"));
      LocalDateTime endDateTime = InputParsers.parseDateTime(
          view.getInput("Performance " + performanceIndex + " end (yyyy-MM-dd HH:mm)"));
      if (startDateTime == null || endDateTime == null || !endDateTime.isAfter(startDateTime)) {
        view.displayError("Performance dates/times are invalid.");
        return null;
      }

      List<String> performerNames = InputParsers.parseCommaSeparatedValues(
          view.getInput("Performance " + performanceIndex + " performer names (comma-separated)"));
      if (performerNames.isEmpty()) {
        view.displayError("At least one performer name is required.");
        return null;
      }

      String venueAddress =
          view.getInput("Performance " + performanceIndex + " venue address").trim();
      if (venueAddress.isEmpty()) {
        view.displayError("Venue address is required.");
        return null;
      }

      Integer venueCapacity = InputParsers.parsePositiveInteger(
          view.getInput("Performance " + performanceIndex + " venue capacity"));
      if (venueCapacity == null) {
        view.displayError("Venue capacity must be a positive integer.");
        return null;
      }

      Boolean venueIsOutdoors = InputParsers
          .parseBoolean(view.getInput("Performance " + performanceIndex + " outdoors? (yes/no)"));
      Boolean venueAllowsSmoking = InputParsers.parseBoolean(
          view.getInput("Performance " + performanceIndex + " smoking allowed? (yes/no)"));
      if (venueIsOutdoors == null || venueAllowsSmoking == null) {
        view.displayError("Venue flags must be specified as yes or no.");
        return null;
      }

      int numTickets = 0;
      double ticketPrice = 0.0;
      if (isTicketed) {
        Integer parsedTickets = InputParsers.parseNonNegativeInteger(
            view.getInput("Performance " + performanceIndex + " remaining ticket count"));
        Double parsedPrice = InputParsers.parseNonNegativeDouble(
            view.getInput("Performance " + performanceIndex + " ticket price"));
        if (parsedTickets == null || parsedPrice == null) {
          view.displayError(
              "Ticket count must be a valid non-negative integer and ticket price must have at most two decimal places.");
          return null;
        }
        numTickets = parsedTickets;
        ticketPrice = parsedPrice;
      }

      if (eventWithSameTitleHasPerformanceAtSameTimes(title, startDateTime, endDateTime)) {
        view.displayError(
            "An event with the same title already exists for the same dates and times.");
        return null;
      }

      try {
        Performance performance = event.createPerformance(nextPerformanceId, startDateTime,
            endDateTime, performerNames, venueAddress, venueCapacity, venueIsOutdoors,
            venueAllowsSmoking, numTickets, ticketPrice);
        addPerformance(performance);
      } catch (IllegalArgumentException exception) {
        view.displayError(exception.getMessage());
        return null;
      }
      nextPerformanceId++;
    }

    addEvent(event);
    organiser.addEvent(event);
    nextEventID++;
    nextPerformanceID = nextPerformanceId;
    view.displaySuccess("Event created successfully.");
    return event;
  }

  public void searchforPerformances() {
    if (checkCurrentUserIsGuest()) {
      view.displayError("Only logged-in users can search for performances.");
      return;
    }

    LocalDate performanceDate =
        InputParsers.parseDate(view.getInput("Enter search date (yyyy-MM-dd)"));
    if (performanceDate == null) {
      view.displayError("Date format is invalid. Use yyyy-MM-dd.");
      return;
    }

    Collection<String> prioritisedPerformanceInfo = new ArrayList<>();
    Collection<String> otherPerformanceInfo = new ArrayList<>();
    StudentPreferences preferences = getStudentPreferences();
    boolean shouldPrioritisePreferences = hasSpecifiedPreferences(preferences);

    for (Event event : getEvents()) {
      Collection<String> performanceInfo =
          event.getInfoOfPerformancesOnDate(performanceDate.atStartOfDay());
      if (performanceInfo.isEmpty()) {
        continue;
      }

      if (shouldPrioritisePreferences && event.matchesPreferences(preferences)) {
        prioritisedPerformanceInfo.addAll(performanceInfo);
      } else {
        otherPerformanceInfo.addAll(performanceInfo);
      }
    }

    if (prioritisedPerformanceInfo.isEmpty() && otherPerformanceInfo.isEmpty()) {
      view.displayError("There are no performances on that date.");
      return;
    }

    Collection<String> orderedPerformanceInfo = new ArrayList<>(prioritisedPerformanceInfo);
    orderedPerformanceInfo.addAll(otherPerformanceInfo);
    view.displayListOfPerformances(orderedPerformanceInfo);
  }

  public void viewPerformance() {
    if (checkCurrentUserIsGuest()) {
      view.displayError("Only logged-in users can view performances.");
      return;
    }

    Long performanceID = InputParsers.parsePositiveLong(view.getInput("Performance ID"));
    if (performanceID == null) {
      view.displayError("Performance ID must be a valid positive whole number.");
      return;
    }

    Performance performance = getPerformanceByID(performanceID);
    if (performance == null) {
      view.displayError("Performance not found.");
      return;
    }

    view.displaySpecificPerformance(performance.toString(true));
  }

  public void cancelPerformance() {
    if (!checkCurrentUserIsEntertainmentProvider()) {
      view.displayError("Only entertainment providers can cancel performance.");
      return;
    }
    EntertainmentProvider ep = (EntertainmentProvider) currentUser;

    Performance performance = null;
    boolean validPerformance = false;

    while (performance == null || !validPerformance) {
      Long performanceID =
          InputParsers.parsePositiveLong(view.getInput("Enter performance ID to cancel"));
      if (performanceID == null) {
        view.displayError("Performance ID must be a valid positive number.");
        continue;
      }
      performance = getPerformanceByID(performanceID);
      validPerformance = false;

      if (performance == null) {
        view.displayError("Performance with given ID does not exist.");
      } else if (!performance.checkCreatedByEP(ep.getEmail())) {
        view.displayError("You can only cancel your own performance.");
      } else if (!performance.checkHasNotHappenedYet()) {
        view.displayError("Performance has already happened");
      } else {
        validPerformance = true;
      }
    }

    if (performance.hasActiveBookings()) {
      String cancellationMessage =
          view.getInput("Enter cancellation message for affected students");
      Collection<Booking> activeBookings = performance.getActiveBookings();

      for (Booking booking : activeBookings) {
        boolean refundSuccessful = paymentSystem.processRefund(booking.getNumTickets(),
            performance.getEventTitle(), booking.getStudentEmail(), booking.getStudentPhone(),
            performance.getOrganiserEmail(), booking.getAmountPaid(), cancellationMessage);
        if (!refundSuccessful) {
          view.displayError(
              "There was an issue with a refund. The performance cannot be cancelled.");
          return;
        }
      }

      for (Booking booking : activeBookings) {
        booking.cancelByProvider();
      }
    }

    performance.cancel();
    view.displaySuccess("Cancellation Successful!");
  }

  @SuppressWarnings("unused")
  private Boolean checkIfSponsorshipPossible(Performance performance, int amount) {
    throw new UnsupportedOperationException("checkIfSponsorshipPossible is not implemented yet.");
    // no need to implement
  }

  @SuppressWarnings("unused")
  public void sponserPeformance() {
    throw new UnsupportedOperationException("sponserPeformance is not implemented yet.");
    // no need to implement
  }

  private void addEvent(Event event) {
    events.add(event);
  }

  private void addPerformance(Performance performance) {
    performances.add(performance);
  }

  private Event getEventByID(long eventID) {
    for (Event event : getEvents()) {
      if (event.getEventID() == eventID) {
        return event;
      }
    }
    return null;
  }

  private Event getEventByTitle(String title) {
    throw new UnsupportedOperationException("getEventByTitle is not implemented yet.");
  }

  private Performance getPerformanceByID(long performanceID) {
    for (Performance performance : performances) {
      if (performance.hasID(performanceID)) {
        return performance;
      }
    }
    return null;
  }

  private long getNextEventID() {
    return nextEventID;
  }

  private long getNextPerformanceID() {
    return nextPerformanceID;
  }

  private Collection<Event> getEvents() {
    return events;
  }

  private boolean eventWithSameTitleHasPerformanceAtSameTimes(String title,
      LocalDateTime startDateTime, LocalDateTime endDateTime) {
    for (Event event : events) {
      if (event.hasTitle(title) && event.hasPerformanceAtTimes(startDateTime, endDateTime)) {
        return true;
      }
    }
    return false;
  }

  private StudentPreferences getStudentPreferences() {
    if (!checkCurrentUserIsStudent()) {
      return null;
    }

    return ((Student) currentUser).getPreferences();
  }

  private boolean hasSpecifiedPreferences(StudentPreferences preferences) {
    return preferences != null && (preferences.isPreferMusicEvents()
        || preferences.isPreferTheaterEvents() || preferences.isPreferDanceEvents()
        || preferences.isPreferMovieEvents() || preferences.isPreferSportsEvents());
  }
}
