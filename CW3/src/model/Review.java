package model;

/**
 * Student-authored review for a performance.
 */
public class Review {
    private final Student author;
    private final EventPerformance performance;
    private final int rating;
    private final String comment;

    public Review(Student author, EventPerformance performance, int rating, String comment) {
        this.author = author;
        this.performance = performance;
        this.rating = rating;
        this.comment = comment;
    }

    public Student getAuthor() {
        return author;
    }

    public EventPerformance getPerformance() {
        return performance;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
