package service;

import model.Booking;

/**
 * Handles booking creation and cancellation.
 */
public interface BookingService {
    Booking createBooking(String studentEmail, String performanceId, int ticketCount);

    void cancelBooking(String bookingId);

    void cancelPerformance(String providerEmail, String performanceId, String organiserMessage);
}
