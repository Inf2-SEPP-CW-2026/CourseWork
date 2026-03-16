package model;

/**
 * Sponsorship contribution tied to a performance.
 */
public class Sponsorship {
    private final String sponsorName;
    private final EventPerformance performance;
    private final double amount;

    public Sponsorship(String sponsorName, EventPerformance performance, double amount) {
        this.sponsorName = sponsorName;
        this.performance = performance;
        this.amount = amount;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public EventPerformance getPerformance() {
        return performance;
    }

    public double getAmount() {
        return amount;
    }
}
