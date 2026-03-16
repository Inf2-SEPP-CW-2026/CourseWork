package controller;

import java.util.Collection;
import model.Event;
import model.Performance;
import view.View;

/**
 * Handles event creation, search, view, cancellation, and sponsorship.
 */
public class EventPerformanceController extends Controller {
  private long nextEventID;
  private long nextPerformanceID;
  private final Collection<Event> events;

  public EventPerformanceController(View view, Collection<Event> events) {
    super(view);
    this.events = events;
    this.nextEventID = 1L;
    this.nextPerformanceID = 1L;
  }

  public Event createEvent() {
    throw new UnsupportedOperationException("createEvent is not implemented yet.");
  }

  public void searchForPerformances() {
    throw new UnsupportedOperationException("searchForPerformances is not implemented yet.");
  }

  public void viewPerformance() {
    throw new UnsupportedOperationException("viewPerformance is not implemented yet.");
  }

  public void cancelPerformance() {
    throw new UnsupportedOperationException("cancelPerformance is not implemented yet.");
  }

  private boolean checkIfSponsorshipPossible(Performance performance, int amount) {
    throw new UnsupportedOperationException("checkIfSponsorshipPossible is not implemented yet.");
  }

  public void sponsorPerformance() {
    throw new UnsupportedOperationException("sponsorPerformance is not implemented yet.");
  }

  private void addEvent(Event event) {
    events.add(event);
  }

  private void addPerformance(Performance performance) {
    throw new UnsupportedOperationException("addPerformance is not implemented yet.");
  }

  private Event getEventByID(long eventID) {
    throw new UnsupportedOperationException("getEventByID is not implemented yet.");
  }

  private Event getEventByTitle(String title) {
    throw new UnsupportedOperationException("getEventByTitle is not implemented yet.");
  }

  private Performance getPerformanceByID(long performanceID) {
    throw new UnsupportedOperationException("getPerformanceByID is not implemented yet.");
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
}
