package uk.ac.ed.inf.eventsapp.view;

import java.util.Collection;
import java.util.Scanner;

/**
 * Text-based CLI placeholder.
 */
public class TextUserInterface implements View {
  private static final Scanner INPUT_SCANNER = new Scanner(System.in);

  @Override
  public String getInput(String inputPrompt) {
    System.out.print(inputPrompt + ": ");
    return INPUT_SCANNER.hasNextLine() ? INPUT_SCANNER.nextLine() : "";
  }

  @Override
  public void displaySuccess(String successMessage) {
    System.out.println("SUCCESS: " + successMessage);
  }

  @Override
  public void displayError(String errorMessage) {
    System.out.println("ERROR: " + errorMessage);
  }

  @Override
  public void displayListOfPerformances(Collection<String> listOfPerformanceInfo) {
    for (String performanceInfo : listOfPerformanceInfo) {
      System.out.println(performanceInfo);
    }
  }

  @Override
  public void displaySpecificPerformance(String performanceInfo) {
    System.out.println(performanceInfo);
  }

  @Override
  public void displayBookingRecord(String bookingRecord) {
    System.out.println(bookingRecord);
  }
}
