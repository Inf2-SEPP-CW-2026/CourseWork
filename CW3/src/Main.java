import controller.BookingController;
import controller.EventPerformanceController;
import controller.MenuController;
import controller.UserController;
import external.MockPaymentSystem;
import external.PaymentSystem;
import integration.MockVerificationSystem;
import integration.VerificationSystem;
import java.util.ArrayList;
import java.util.Collection;
import model.Booking;
import model.Event;
import model.User;
import view.TextUserInterface;
import view.View;

/**
 * Application entry point placeholder.
 */
public class Main {
  public static void main(String[] args) {
    View view = new TextUserInterface();
    Collection<User> users = new ArrayList<>();
    Collection<Event> events = new ArrayList<>();
    Collection<Booking> bookings = new ArrayList<>();
    VerificationSystem verificationSystem = new MockVerificationSystem();
    PaymentSystem paymentSystem = new MockPaymentSystem();

    UserController userController = new UserController(view, verificationSystem, users, events);
    EventPerformanceController eventPerformanceController =
        new EventPerformanceController(view, events);
    BookingController bookingController =
        new BookingController(view, paymentSystem, events, bookings);
    MenuController menuController =
        new MenuController(view, userController, eventPerformanceController, bookingController);

    // The object graph is intentionally constructed only as a framework placeholder.
  }
}
