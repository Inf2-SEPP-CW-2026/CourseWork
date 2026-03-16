package model;

import java.time.LocalDateTime;

/**
 * Booking made by a student for a performance.
 */
public class Booking {
    private final String bookingId;
    private final Student student;
    private final EventPerformance performance;
    private final int ticketCount;
    private final double amountPaid;
    private final LocalDateTime createdAt;
    private final BookingStatus status;

    public Booking(
            String bookingId,
            Student student,
            EventPerformance performance,
            int ticketCount,
            double amountPaid,
            LocalDateTime createdAt,
            BookingStatus status) {
        this.bookingId = bookingId;
        this.student = student;
        this.performance = performance;
        this.ticketCount = ticketCount;
        this.amountPaid = amountPaid;
        this.createdAt = createdAt;
        this.status = status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Student getStudent() {
        return student;
    }

    public EventPerformance getPerformance() {
        return performance;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BookingStatus getStatus() {
        return status;
    }
}
