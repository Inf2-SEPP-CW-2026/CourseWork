package uk.ac.ed.inf.eventsapp.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Student preferences from the UML diagram.
 */
public class StudentPreferences {
  private boolean preferMusicEvents;
  private boolean preferTheaterEvents;
  private boolean preferDanceEvents;
  private boolean preferMovieEvents;
  private boolean preferSportsEvents;

  public StudentPreferences() {}

  public StudentPreferences(boolean preferMusicEvents, boolean preferTheaterEvents,
      boolean preferDanceEvents, boolean preferMovieEvents, boolean preferSportsEvents) {
    this.preferMusicEvents = preferMusicEvents;
    this.preferTheaterEvents = preferTheaterEvents;
    this.preferDanceEvents = preferDanceEvents;
    this.preferMovieEvents = preferMovieEvents;
    this.preferSportsEvents = preferSportsEvents;
  }

  public boolean updatePreferences(String studentRawStringPreferences) {
    if (studentRawStringPreferences == null) {
      return false;
    }

    clearPreferences();
    String trimmedPreferences = studentRawStringPreferences.trim();
    if (trimmedPreferences.isEmpty()) {
      return true;
    }

    String[] rawPreferences = trimmedPreferences.split(",");
    if (rawPreferences.length > 3) {
      return false;
    }

    Set<String> seenPreferences = new HashSet<>();
    for (String rawPreference : rawPreferences) {
      String normalisedPreference = rawPreference.trim().toUpperCase().replace(' ', '_');
      if (normalisedPreference.isEmpty() || !seenPreferences.add(normalisedPreference)) {
        clearPreferences();
        return false;
      }

      if ("THEATER".equals(normalisedPreference)) {
        normalisedPreference = "THEATRE";
      }

      switch (normalisedPreference) {
        case "MUSIC" -> preferMusicEvents = true;
        case "THEATRE" -> preferTheaterEvents = true;
        case "DANCE" -> preferDanceEvents = true;
        case "MOVIE" -> preferMovieEvents = true;
        case "SPORTS" -> preferSportsEvents = true;
        default -> {
          clearPreferences();
          return false;
        }
      }
    }

    return true;
  }

  private void clearPreferences() {
    preferMusicEvents = false;
    preferTheaterEvents = false;
    preferDanceEvents = false;
    preferMovieEvents = false;
    preferSportsEvents = false;
  }

  public boolean isPreferMusicEvents() {
    return preferMusicEvents;
  }

  public boolean isPreferTheaterEvents() {
    return preferTheaterEvents;
  }

  public boolean isPreferDanceEvents() {
    return preferDanceEvents;
  }

  public boolean isPreferMovieEvents() {
    return preferMovieEvents;
  }

  public boolean isPreferSportsEvents() {
    return preferSportsEvents;
  }
}
