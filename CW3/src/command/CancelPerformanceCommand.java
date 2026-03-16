package command;

import controller.Controller;

/**
 * Cancels a performance and triggers downstream refund handling.
 */
public class CancelPerformanceCommand extends AbstractCommand {
    private final String providerEmail;
    private final String performanceId;
    private final String organiserMessage;

    public CancelPerformanceCommand(
            String providerEmail,
            String performanceId,
            String organiserMessage) {
        super("Cancel performance");
        this.providerEmail = providerEmail;
        this.performanceId = performanceId;
        this.organiserMessage = organiserMessage;
    }

    public String getProviderEmail() {
        return providerEmail;
    }

    public String getPerformanceId() {
        return performanceId;
    }

    public String getOrganiserMessage() {
        return organiserMessage;
    }

    @Override
    public void execute(Controller controller) {
        throw notImplemented();
    }
}
