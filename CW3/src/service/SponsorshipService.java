package service;

import model.Sponsorship;

/**
 * Handles sponsorship workflows.
 */
public interface SponsorshipService {
    Sponsorship sponsorPerformance(String sponsorName, String performanceId, double amount);
}
