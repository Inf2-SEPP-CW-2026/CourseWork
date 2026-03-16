package model;

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
    throw new UnsupportedOperationException("updatePreferences is not implemented yet.");
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
