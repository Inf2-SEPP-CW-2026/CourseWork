package uk.ac.ed.inf.eventsapp.system;

import uk.ac.ed.inf.eventsapp.controller.UserController;
import uk.ac.ed.inf.eventsapp.integration.MockVerificationSystem;
import java.util.ArrayList;
import uk.ac.ed.inf.eventsapp.model.Event;
import uk.ac.ed.inf.eventsapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import uk.ac.ed.inf.eventsapp.view.TextUserInterface;
import uk.ac.ed.inf.eventsapp.view.View;

/**
 * System-test scaffold for the log-in use case.
 */
public class LogInSystemTests {
  private UserController userController;

  @BeforeEach
  void setUp() {
    View view = new TextUserInterface();
    userController = new UserController(view, new MockVerificationSystem(), new ArrayList<User>(),
        new ArrayList<Event>());
  }

  @Test
  @Disabled("TODO: implement log-in system tests.")
  void registeredUserCanLogIn() {
    userController.login();
  }
}
