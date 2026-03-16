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
