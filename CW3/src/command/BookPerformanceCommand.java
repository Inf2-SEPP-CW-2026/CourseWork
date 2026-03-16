package command;

import controller.Controller;

/**
 * Books tickets for a performance.
 */
public class BookPerformanceCommand extends AbstractCommand {
    private final String studentEmail;
    private final String performanceId;
    private final int ticketCount;

    public BookPerformanceCommand(String studentEmail, String performanceId, int ticketCount) {
        super("Book performance");
        this.studentEmail = studentEmail;
        this.performanceId = performanceId;
        this.ticketCount = ticketCount;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getPerformanceId() {
        return performanceId;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    @Override
    public void execute(Controller controller) {
        throw notImplemented();
    }
}
