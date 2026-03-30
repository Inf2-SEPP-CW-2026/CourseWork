package uk.ac.ed.inf.eventsapp.system;

import uk.ac.ed.inf.eventsapp.controller.EventPerformanceController;
import java.util.ArrayList;
import uk.ac.ed.inf.eventsapp.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import uk.ac.ed.inf.eventsapp.view.TextUserInterface;
import uk.ac.ed.inf.eventsapp.view.View;

/**
 * System-test scaffold for performance viewing.
 */
public class ViewPerformanceSystemTests {
  private EventPerformanceController eventPerformanceController;

  @BeforeEach
  void setUp() {
    View view = new TextUserInterface();
    eventPerformanceController = new EventPerformanceController(view, new ArrayList<Event>());
  }

  @Test
  @Disabled("TODO: implement performance-view system tests.")
  void userCanViewPerformanceDetails() {
    eventPerformanceController.viewPerformance();
  }
}
