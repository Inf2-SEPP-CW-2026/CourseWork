package uk.ac.ed.inf.eventsapp.model;

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
    if (studentRawStringPreferences == null || studentRawStringPreferences.length() != 5) {
      return false;
    }
    for (char c : studentRawStringPreferences.toCharArray()) {
      if (c != '0' && c != '1') {
        return false;
      }
    }
    preferMusicEvents = studentRawStringPreferences.charAt(0) == '1';
    preferTheaterEvents = studentRawStringPreferences.charAt(1) == '1';
    preferDanceEvents = studentRawStringPreferences.charAt(2) == '1';
    preferMovieEvents = studentRawStringPreferences.charAt(3) == '1';
    preferSportsEvents = studentRawStringPreferences.charAt(4) == '1';
    return true;
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
