package service;

import model.Review;

/**
 * Handles review submission for completed performances.
 */
public interface ReviewService {
    Review createReview(String studentEmail, String performanceId, int rating, String comment);
}
