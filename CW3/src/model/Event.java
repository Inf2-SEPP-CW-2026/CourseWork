package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Event aggregate containing one or more performances.
 */
public class Event {
    private final String eventId;
    private final String title;
    private final String description;
    private final EntertainmentProvider organiser;
    private final List<EventPerformance> performances;

    public Event(
            String eventId,
            String title,
            String description,
            EntertainmentProvider organiser) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.organiser = organiser;
        this.performances = new ArrayList<>();
    }

    public String getEventId() {
        return eventId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public EntertainmentProvider getOrganiser() {
        return organiser;
    }

    public List<EventPerformance> getPerformances() {
        return Collections.unmodifiableList(performances);
    }
}
