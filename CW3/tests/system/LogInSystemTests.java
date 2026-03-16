package system;

import controller.UserController;
import integration.MockVerificationSystem;
import java.util.ArrayList;
import model.Event;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import view.TextUserInterface;
import view.View;

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
