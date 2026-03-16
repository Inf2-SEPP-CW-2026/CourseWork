package service;

import model.StudentPreferences;

/**
 * Updates student profile preferences.
 */
public interface PreferenceService {
    void updatePreferences(String studentEmail, StudentPreferences preferences);
}
