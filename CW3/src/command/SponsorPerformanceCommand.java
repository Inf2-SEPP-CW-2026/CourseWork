package command;

import controller.Controller;

/**
 * Records a sponsorship for a performance.
 */
public class SponsorPerformanceCommand extends AbstractCommand {
    private final String sponsorName;
    private final String performanceId;
    private final double amount;

    public SponsorPerformanceCommand(String sponsorName, String performanceId, double amount) {
        super("Sponsor performance");
        this.sponsorName = sponsorName;
        this.performanceId = performanceId;
        this.amount = amount;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public String getPerformanceId() {
        return performanceId;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public void execute(Controller controller) {
        throw notImplemented();
    }
}
