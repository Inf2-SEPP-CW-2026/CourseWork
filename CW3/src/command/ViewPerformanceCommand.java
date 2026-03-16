package command;

import controller.Controller;

/**
 * Retrieves details for a single performance.
 */
public class ViewPerformanceCommand extends AbstractCommand {
    private final String performanceId;

    public ViewPerformanceCommand(String performanceId) {
        super("View performance");
        this.performanceId = performanceId;
    }

    public String getPerformanceId() {
        return performanceId;
    }

    @Override
    public void execute(Controller controller) {
        throw notImplemented();
    }
}
