package uk.ac.ed.inf.eventsapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Entertainment provider
 */
public class EntertainmentProvider extends User {
  private String orgName;
  private String businessNumber;
  private String name;
  private String description;
  private final Collection<Event> events;

  public EntertainmentProvider() {
    this.events = new ArrayList<>();
  }

  public EntertainmentProvider(String email, String password, String orgName, String businessNumber,
      String name, String description) {
    super(email, password);
    this.orgName = orgName;
    this.businessNumber = businessNumber;
    this.name = name;
    this.description = description;
    this.events = new ArrayList<>();
  }

  public void addEvent(Event event) {
    events.add(event);
  }

  public String getOrgName() {
    return orgName;
  }

  public String getBusinessNumber() {
    return businessNumber;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Collection<Event> getEvents() {
    return Collections.unmodifiableCollection(events);
  }
}
