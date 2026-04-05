package uk.ac.ed.inf.eventsapp.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import external.PaymentSystem;
import uk.ac.ed.inf.eventsapp.model.Booking;
import uk.ac.ed.inf.eventsapp.model.EntertainmentProvider;
import uk.ac.ed.inf.eventsapp.model.Event;
import uk.ac.ed.inf.eventsapp.model.EventType;
import uk.ac.ed.inf.eventsapp.model.Performance;
import uk.ac.ed.inf.eventsapp.model.Student;
import uk.ac.ed.inf.eventsapp.model.StudentPreferences;
import uk.ac.ed.inf.eventsapp.view.View;

/**
 * Handles event creation, search, view, cancellation, and sponsorship.
 */
public class EventPerformanceController extends Controller {
  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static final Pattern MONEY_PATTERN = Pattern.compile("^(?:0|[1-9]\\d*)(?:\\.\\d{1,2})?$");
  // non-negative number with at most two decimal places

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

    EventType type = parseEventType(
        view.getInput("Enter event type (music, theatre, dance, movie, sports, games)"));
    if (type == null) {
      view.displayError("Invalid event type.");
      return null;
    }

    Boolean isTicketed = parseBoolean(view.getInput("Is the event ticketed? (yes/no)"));
    if (isTicketed == null) {
      view.displayError("Ticketed must be specified as yes or no.");
      return null;
    }

    Integer performanceCount = parsePositiveInteger(view.getInput("How many performances?"));
    if (performanceCount == null) {
      view.displayError("Number of performances must be a positive integer.");
      return null;
    }

    Event event = new Event(getNextEventID(), title, type, isTicketed, organiser);
    long nextPerformanceId = getNextPerformanceID();

    for (int performanceIndex = 1; performanceIndex <= performanceCount; performanceIndex++) {
      LocalDateTime startDateTime =
          parseTime(view.getInput("Performance " + performanceIndex + " start (yyyy-MM-dd HH:mm)"));
      LocalDateTime endDateTime =
          parseTime(view.getInput("Performance " + performanceIndex + " end (yyyy-MM-dd HH:mm)"));
      if (startDateTime == null || endDateTime == null || !endDateTime.isAfter(startDateTime)) {
        view.displayError("Performance dates/times are invalid.");
        return null;
      }

      List<String> performerNames = parsePerformerNames(
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

      Integer venueCapacity = parsePositiveInteger(
          view.getInput("Performance " + performanceIndex + " venue capacity"));
      if (venueCapacity == null) {
        view.displayError("Venue capacity must be a positive integer.");
        return null;
      }

      Boolean venueIsOutdoors =
          parseBoolean(view.getInput("Performance " + performanceIndex + " outdoors? (yes/no)"));
      Boolean venueAllowsSmoking = parseBoolean(
          view.getInput("Performance " + performanceIndex + " smoking allowed? (yes/no)"));
      if (venueIsOutdoors == null || venueAllowsSmoking == null) {
        view.displayError("Venue flags must be specified as yes or no.");
        return null;
      }

      int numTickets = 0;
      double ticketPrice = 0.0;
      if (isTicketed) {
        Integer parsedTickets = parseNonNegativeInteger(
            view.getInput("Performance " + performanceIndex + " remaining ticket count"));
        Double parsedPrice = parseNonNegativeDouble(
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

    LocalDate performanceDate = parseDate(view.getInput("Enter search date (yyyy-MM-dd)"));
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

    Long performanceID = parsePositiveLong(view.getInput("Performance ID"));
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
      Long performanceID = parsePositiveLong(view.getInput("Enter performance ID to cancel"));
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

  private EventType parseEventType(String rawType) {
    if (rawType == null) {
      return null;
    }

    String normalised = rawType.trim().toUpperCase().replace(' ', '_');
    if (normalised.isEmpty()) {
      return null;
    }

    if ("THEATER".equals(normalised)) { // American spelling
      normalised = "THEATRE";
    }

    try {
      return EventType.valueOf(normalised);
    } catch (IllegalArgumentException exception) {
      return null;
    }
  }

  private Boolean parseBoolean(String rawBoolean) {
    if (rawBoolean == null) {
      return null;
    }

    String normalised = rawBoolean.trim().toLowerCase();
    return switch (normalised) {
      case "y", "yes", "true", "ticketed", "outdoors", "smoking" -> Boolean.TRUE;
      case "n", "no", "false", "non-ticketed", "indoors", "non-smoking" -> Boolean.FALSE;
      default -> null;
    };
    // robustness to common variations
  }

  private Integer parsePositiveInteger(String rawInteger) {
    Integer parsed = parseNonNegativeInteger(rawInteger);
    return parsed != null && parsed > 0 ? parsed : null;
  }

  private Long parsePositiveLong(String rawLong) {
    if (rawLong == null) {
      return null;
    }

    try {
      long parsed = Long.parseLong(rawLong.trim());
      return parsed > 0 ? parsed : null;
    } catch (NumberFormatException exception) {
      return null;
    }
  }

  private Integer parseNonNegativeInteger(String rawInteger) {
    if (rawInteger == null) {
      return null;
    }

    try {
      int parsed = Integer.parseInt(rawInteger.trim());
      return parsed >= 0 ? parsed : null;
    } catch (NumberFormatException exception) {
      return null;
    }
  }

  private Double parseNonNegativeDouble(String rawDouble) {
    if (rawDouble == null) {
      return null;
    }

    String trimmedValue = rawDouble.trim();
    if (!MONEY_PATTERN.matcher(trimmedValue).matches()) {
      return null;
    }

    try {
      double parsed = Double.parseDouble(trimmedValue);
      return parsed >= 0.0 ? parsed : null;
    } catch (NumberFormatException exception) {
      return null;
    }
  }

  private LocalDateTime parseTime(String rawDateTime) {
    if (rawDateTime == null) {
      return null;
    }

    try {
      return LocalDateTime.parse(rawDateTime.trim(), DATE_TIME_FORMATTER);
    } catch (DateTimeParseException exception) {
      return null;
    }
  }

  private LocalDate parseDate(String rawDate) {
    if (rawDate == null) {
      return null;
    }

    try {
      return LocalDate.parse(rawDate.trim(), DATE_FORMATTER);
    } catch (DateTimeParseException exception) {
      return null;
    }
  }

  private List<String> parsePerformerNames(String rawPerformerNames) {
    List<String> performerNames = new ArrayList<>();
    if (rawPerformerNames == null) {
      return performerNames;
    }

    for (String performerName : rawPerformerNames.split(",")) {
      String trimmedPerformerName = performerName.trim();
      if (!trimmedPerformerName.isEmpty()) {
        performerNames.add(trimmedPerformerName);
      }
    }

    return performerNames;
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
