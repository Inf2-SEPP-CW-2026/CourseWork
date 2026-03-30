package uk.ac.ed.inf.eventsapp.view;

import java.util.Collection;

/**
 * User-facing output/input abstraction from the UML diagram.
 */
public interface View {
  String getInput(String inputPrompt);

  void displaySuccess(String successMessage);

  void displayError(String errorMessage);

  void displayListOfPerformances(Collection<String> listOfPerformanceInfo);

  void displaySpecificPerformance(String performanceInfo);

  void displayBookingRecord(String bookingRecord);
}
