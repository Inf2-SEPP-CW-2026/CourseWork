package command;

import controller.Controller;

/**
 * Creates a review for a performance.
 */
public class ReviewPerformanceCommand extends AbstractCommand {
    private final String studentEmail;
    private final String performanceId;
    private final int rating;
    private final String comment;

    public ReviewPerformanceCommand(
            String studentEmail,
            String performanceId,
            int rating,
            String comment) {
        super("Review performance");
        this.studentEmail = studentEmail;
        this.performanceId = performanceId;
        this.rating = rating;
        this.comment = comment;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getPerformanceId() {
        return performanceId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public void execute(Controller controller) {
        throw notImplemented();
    }
}
