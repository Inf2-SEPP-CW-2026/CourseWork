package uk.ac.ed.inf.eventsapp.unit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;
import uk.ac.ed.inf.eventsapp.model.Event;
import uk.ac.ed.inf.eventsapp.model.EventType;
import uk.ac.ed.inf.eventsapp.model.EntertainmentProvider;
import uk.ac.ed.inf.eventsapp.model.Performance;
import uk.ac.ed.inf.eventsapp.model.PerformanceStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unit-test scaffold for the UML Performance class.
 */
public class TestPerformance {
  @Test
  @Disabled("TODO: implement Performance unit tests.")
  void performanceStoresCoreMetadata() {
    EntertainmentProvider provider = new EntertainmentProvider("provider@example.com", "secret",
        "Org", "1234567890", "Provider Name", "Description");
    Event event = new Event(1L, "Live Music", EventType.MUSIC, true, provider);
    Performance performance = new Performance(1L, LocalDateTime.of(2026, 4, 1, 19, 0),
        LocalDateTime.of(2026, 4, 1, 21, 0), List.of("Band"), "Main Hall", 120, false, false, 120,
        0, 15.0, PerformanceStatus.ACTIVE, event);

    assertNotNull(performance, "TODO: replace with behaviour-specific assertions.");
  }
}
