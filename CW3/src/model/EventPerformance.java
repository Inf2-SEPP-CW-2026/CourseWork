package model;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Compatibility shim for older coursework wording that refers to EventPerformance.
 *
 * <p>
 * The UML diagram uses the class name {@link Performance}, so that is the canonical model class.
 */
public class EventPerformance extends Performance {
  public EventPerformance() {
    super();
  }

  public EventPerformance(long performanceID, LocalDateTime startDateTime,
      LocalDateTime endDateTime, Collection<String> performerNames, String venueAddress,
      int venueCapacity, boolean venueIsOutdoors, boolean venueAllowsSmoking, int numTicketsTotal,
      int numTicketsSold, double ticketPrice, boolean isSponsored, double sponsoredAmount,
      PerformanceStatus status, Event event) {
    super(performanceID, startDateTime, endDateTime, performerNames, venueAddress, venueCapacity,
        venueIsOutdoors, venueAllowsSmoking, numTicketsTotal, numTicketsSold, ticketPrice,
        isSponsored, sponsoredAmount, status, event);
  }
}
