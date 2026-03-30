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
 * System-test scaffold for sponsorship.
 */
public class SponsorPerformanceSystemTests {
  private EventPerformanceController eventPerformanceController;

  @BeforeEach
  void setUp() {
    View view = new TextUserInterface();
    eventPerformanceController = new EventPerformanceController(view, new ArrayList<Event>());
  }

  @Test
  @Disabled("TODO: implement sponsorship system tests.")
  void sponsorCanFundAPerformance() {
    eventPerformanceController.sponsorPerformance();
  }
}
