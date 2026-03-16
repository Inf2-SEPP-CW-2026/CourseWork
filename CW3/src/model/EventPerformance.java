package model;

import java.time.LocalDateTime;

/**
 * Individual performance instance for an event.
 */
public class EventPerformance {
    private final String performanceId;
    private final Event event;
    private final LocalDateTime startDateTime;
    private final String venue;
    private final int capacity;
    private final double ticketPrice;
    private final PerformanceStatus status;

    public EventPerformance(
            String performanceId,
            Event event,
            LocalDateTime startDateTime,
            String venue,
            int capacity,
            double ticketPrice,
            PerformanceStatus status) {
        this.performanceId = performanceId;
        this.event = event;
        this.startDateTime = startDateTime;
        this.venue = venue;
        this.capacity = capacity;
        this.ticketPrice = ticketPrice;
        this.status = status;
    }

    public String getPerformanceId() {
        return performanceId;
    }

    public Event getEvent() {
        return event;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public String getVenue() {
        return venue;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public PerformanceStatus getStatus() {
        return status;
    }
}
