package command;

import controller.Controller;

/**
 * Cancels an existing booking.
 */
public class CancelBookingCommand extends AbstractCommand {
    private final String bookingId;

    public CancelBookingCommand(String bookingId) {
        super("Cancel booking");
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return bookingId;
    }

    @Override
    public void execute(Controller controller) {
        throw notImplemented();
    }
}
