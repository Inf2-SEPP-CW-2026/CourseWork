package uk.ac.ed.inf.eventsapp.system;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import external.MockPaymentSystem;
import uk.ac.ed.inf.eventsapp.controller.EventPerformanceController;
import uk.ac.ed.inf.eventsapp.view.TextUserInterface;
import uk.ac.ed.inf.eventsapp.view.View;

/**
 * System-test scaffold for performance cancellation.
 */
public class CancelPerformanceSystemTests {
  private EventPerformanceController eventPerformanceController;

  @BeforeEach
  @SuppressWarnings("unused")
  void setUp() {
    View view = new TextUserInterface();
    eventPerformanceController = new EventPerformanceController(view, new ArrayList<>(),
        new ArrayList<>(), new MockPaymentSystem());
  }

  @Test
  @Disabled("TODO: implement performance-cancellation system tests.")
  void providerCanCancelAnExistingPerformance() {
    eventPerformanceController.cancelPerformance();
  }
}
