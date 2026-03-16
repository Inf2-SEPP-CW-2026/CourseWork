package service;

import model.Event;

/**
 * Handles event and performance creation workflows.
 */
public interface EventService {
    Event createEvent(String providerEmail, String title, String description);
}
