package service;

import model.EntertainmentProvider;

/**
 * Handles registration and provider-related administration.
 */
public interface EntertainmentProviderService {
    EntertainmentProvider registerProvider(
            String providerName,
            String email,
            String businessRegistrationNumber);
}
