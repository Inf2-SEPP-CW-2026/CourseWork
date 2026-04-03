package uk.ac.ed.inf.eventsapp;

import uk.ac.ed.inf.eventsapp.controller.BookingController;
import uk.ac.ed.inf.eventsapp.controller.EventPerformanceController;
import uk.ac.ed.inf.eventsapp.controller.MenuController;
import uk.ac.ed.inf.eventsapp.controller.UserController;
import external.MockPaymentSystem;
import external.PaymentSystem;
import uk.ac.ed.inf.eventsapp.integration.MockVerificationSystem;
import uk.ac.ed.inf.eventsapp.integration.VerificationSystem;
import java.util.ArrayList;
import java.util.Collection;
import uk.ac.ed.inf.eventsapp.model.Booking;
import uk.ac.ed.inf.eventsapp.model.Event;
import uk.ac.ed.inf.eventsapp.model.User;
import uk.ac.ed.inf.eventsapp.view.TextUserInterface;
import uk.ac.ed.inf.eventsapp.view.View;

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
        new EventPerformanceController(view, events, paymentSystem);
    BookingController bookingController =
        new BookingController(view, paymentSystem, events, bookings);
    MenuController menuController =
        new MenuController(view, userController, eventPerformanceController, bookingController);

    // The object graph is intentionally constructed only as a framework placeholder.
  }
}
