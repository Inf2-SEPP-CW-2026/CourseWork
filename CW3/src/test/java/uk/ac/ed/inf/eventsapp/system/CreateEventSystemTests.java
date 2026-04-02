package uk.ac.ed.inf.eventsapp.system;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.ac.ed.inf.eventsapp.controller.EventPerformanceController;
import uk.ac.ed.inf.eventsapp.model.EntertainmentProvider;
import uk.ac.ed.inf.eventsapp.model.Event;
import uk.ac.ed.inf.eventsapp.view.View;

/**
 * System tests for the create-event use case.
 */
public class CreateEventSystemTests {
  private Collection<Event> events;
  private EntertainmentProvider provider;

  @BeforeEach
  void setUp() {
    events = new ArrayList<>();
    provider = new EntertainmentProvider("provider@example.com", "encrypted_password", "Some Org",
        "BN-1234567", "Provider Rep", "some description");
  }

  @Test
  void registeredProviderCanCreateATicketedEvent() {
    ScriptedView view = new ScriptedView("Spring Concert", "music", "yes", "1", "2026-05-10 19:00",
        "2026-05-10 21:00", "Alice, Bob", "McEwan Hall", "500", "no", "no", "250", "15.50");
    EventPerformanceController controller = new EventPerformanceController(view, events);
    controller.setCurrentUser(provider);

    Event createdEvent = controller.createEvent();

    assertNotNull(createdEvent);
    assertEquals(1, events.size());
    assertEquals(1, provider.getEvents().size());
    assertEquals("SUCCESS: Event created successfully.", view.getLastSuccessMessage());
    assertTrue(view.getErrorMessages().isEmpty());
    assertNotNull(createdEvent.getPerformanceByID(1L));
  }

  @Test
  void guestCannotCreateAnEvent() {
    ScriptedView view = new ScriptedView();
    EventPerformanceController controller = new EventPerformanceController(view, events);

    Event createdEvent = controller.createEvent();

    assertNull(createdEvent);
    assertTrue(events.isEmpty());
    assertEquals("ERROR: Only logged-in entertainment providers can create events.",
        view.getLastErrorMessage());
  }

  @Test
  void invalidTicketPriceFormatPreventsEventCreation() {
    ScriptedView view = new ScriptedView("Spring Concert", "music", "yes", "1", "2026-05-10 19:00",
        "2026-05-10 21:00", "Alice, Bob", "McEwan Hall", "500", "no", "no", "250", "15.999");
    EventPerformanceController controller = new EventPerformanceController(view, events);
    controller.setCurrentUser(provider);

    Event createdEvent = controller.createEvent();

    assertNull(createdEvent);
    assertTrue(events.isEmpty());
    assertTrue(provider.getEvents().isEmpty());
    assertEquals(
        "ERROR: Ticket count must be a valid non-negative integer and ticket price must have at most two decimal places.",
        view.getLastErrorMessage());
  }

  @Test
  void duplicateEventTitleAndPerformanceTimeIsRejected() {
    Event existingEvent = new Event(99L, "Spring Concert",
        uk.ac.ed.inf.eventsapp.model.EventType.MUSIC, true, provider);
    existingEvent.createPerformance(55L, java.time.LocalDateTime.of(2026, 5, 10, 19, 0),
        java.time.LocalDateTime.of(2026, 5, 10, 21, 0), List.of("Existing Artist"), "Old College",
        400, false, false, 200, 10.0);
    events.add(existingEvent);

    ScriptedView view = new ScriptedView("Spring Concert", "music", "yes", "1", "2026-05-10 19:00",
        "2026-05-10 21:00", "Alice, Bob", "McEwan Hall", "500", "no", "no", "250", "15.50");
    EventPerformanceController controller = new EventPerformanceController(view, events);
    controller.setCurrentUser(provider);

    Event createdEvent = controller.createEvent();

    assertNull(createdEvent);
    assertEquals(1, events.size());
    assertEquals("ERROR: An event with the same title already exists for the same dates and times.",
        view.getLastErrorMessage());
  }

  private static final class ScriptedView implements View {
    private final Deque<String> scriptedInputs;
    private final List<String> successMessages;
    private final List<String> errorMessages;

    private ScriptedView(String... scriptedInputs) {
      this.scriptedInputs = new ArrayDeque<>(List.of(scriptedInputs));
      this.successMessages = new ArrayList<>();
      this.errorMessages = new ArrayList<>();
    }

    @Override
    public String getInput(String inputPrompt) {
      return scriptedInputs.isEmpty() ? "" : scriptedInputs.removeFirst();
    }

    @Override
    public void displaySuccess(String successMessage) {
      successMessages.add("SUCCESS: " + successMessage);
    }

    @Override
    public void displayError(String errorMessage) {
      errorMessages.add("ERROR: " + errorMessage);
    }

    @Override
    public void displayListOfPerformances(Collection<String> listOfPerformanceInfo) {}

    @Override
    public void displaySpecificPerformance(String performanceInfo) {}

    @Override
    public void displayBookingRecord(String bookingRecord) {}

    private String getLastSuccessMessage() {
      return successMessages.isEmpty() ? null : successMessages.get(successMessages.size() - 1);
    }

    private String getLastErrorMessage() {
      return errorMessages.isEmpty() ? null : errorMessages.get(errorMessages.size() - 1);
    }

    private List<String> getErrorMessages() {
      return errorMessages;
    }
  }
}
