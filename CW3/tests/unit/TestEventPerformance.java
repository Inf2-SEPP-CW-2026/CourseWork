package unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import model.EntertainmentProvider;
import model.Event;
import model.EventPerformance;
import model.PerformanceStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unit-test scaffold for EventPerformance.
 */
public class TestEventPerformance {
    @Test
    @Disabled("TODO: implement EventPerformance unit tests.")
    void eventPerformanceStoresCoreMetadata() {
        EntertainmentProvider provider = new EntertainmentProvider(
                "ep-1",
                "Provider",
                "provider@example.com",
                "hashed",
                "1234567890");
        Event event = new Event("event-1", "Live Music", "Evening concert", provider);
        EventPerformance performance = new EventPerformance(
                "performance-1",
                event,
                LocalDateTime.of(2026, 4, 1, 19, 0),
                "Main Hall",
                120,
                15.0,
                PerformanceStatus.DRAFT);

        assertEquals("performance-1", performance.getPerformanceId(),
                "TODO: replace with behaviour-specific assertions.");
    }
}
