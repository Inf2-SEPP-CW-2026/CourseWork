package system;

import controller.EventPerformanceController;
import java.util.ArrayList;
import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import view.TextUserInterface;
import view.View;

/**
 * System-test scaffold for event creation.
 */
public class CreateEventSystemTests {
  private EventPerformanceController eventPerformanceController;

  @BeforeEach
  void setUp() {
    View view = new TextUserInterface();
    eventPerformanceController = new EventPerformanceController(view, new ArrayList<Event>());
  }

  @Test
  @Disabled("TODO: implement event-creation system tests.")
  void registeredProviderCanCreateAnEvent() {
    eventPerformanceController.createEvent();
  }
}
