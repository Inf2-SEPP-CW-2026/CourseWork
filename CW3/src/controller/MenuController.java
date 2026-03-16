package controller;

import view.View;

/**
 * Top-level main menu dispatcher.
 */
public class MenuController extends Controller {
  private final UserController userController;
  private final EventPerformanceController eventPerformanceController;
  private final BookingController bookingController;

  public MenuController(View view, UserController userController,
      EventPerformanceController eventPerformanceController, BookingController bookingController) {
    super(view);
    this.userController = userController;
    this.eventPerformanceController = eventPerformanceController;
    this.bookingController = bookingController;
  }

  public void mainMenu() {
    throw new UnsupportedOperationException("mainMenu is not implemented yet.");
  }

  private boolean handleGuestMainMenu() {
    throw new UnsupportedOperationException("handleGuestMainMenu is not implemented yet.");
  }

  private boolean handleStudentMainMenu() {
    throw new UnsupportedOperationException("handleStudentMainMenu is not implemented yet.");
  }

  private boolean handleTeachingStaffMainMenu() {
    throw new UnsupportedOperationException("handleTeachingStaffMainMenu is not implemented yet.");
  }

  private boolean handleAdminStaffMainMenu() {
    throw new UnsupportedOperationException("handleAdminStaffMainMenu is not implemented yet.");
  }

  private UserController getUserController() {
    return userController;
  }

  private EventPerformanceController getEventPerformanceController() {
    return eventPerformanceController;
  }

  private BookingController getBookingController() {
    return bookingController;
  }
}
