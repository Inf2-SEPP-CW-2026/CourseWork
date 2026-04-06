package uk.ac.ed.inf.eventsapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Student user from the UML diagram.
 */
public class Student extends User {
  private String name;
  private int phoneNumber;
  private final Collection<Booking> bookings;
  private final StudentPreferences preferences;

  public Student() {
    this.preferences = new StudentPreferences();
    this.bookings = new ArrayList<>();
  }

  public Student(String email, String password, String name, int phoneNumber,
      StudentPreferences preferences) {
    super(email, password);
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.preferences = preferences == null ? new StudentPreferences() : preferences;
    this.bookings = new ArrayList<>();
  }

  public void addBooking(Booking booking) {
    bookings.add(booking);
  }

  public String getName() {
    return name;
  }

  public int getPhoneNumber() {
    return phoneNumber;
  }

  public Collection<Booking> getBookings() {
    return Collections.unmodifiableCollection(bookings);
  }

  public StudentPreferences getPreferences() {
    return preferences;
  }
}
