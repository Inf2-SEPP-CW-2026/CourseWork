package uk.ac.ed.inf.eventsapp.view;

import java.util.Collection;
import java.util.Scanner;

/**
 * Text-based CLI
 */
public class TextUserInterface implements View {
  private static final String RESET = "\033[0m";
  private static final String ERROR_PREFIX = "\033[1;31m[ERROR]: " + RESET;
  private static final String SUCCESS_PREFIX = "\033[1;34m[SUCCESS]: " + RESET;
  private static final Scanner INPUT_SCANNER = new Scanner(System.in);

  @Override
  public String getInput(String inputPrompt) {
    System.out.print(inputPrompt + ": ");
    return INPUT_SCANNER.hasNextLine() ? INPUT_SCANNER.nextLine() : "";
  }

  @Override
  public void displaySuccess(String successMessage) {
    System.out.println(SUCCESS_PREFIX + successMessage);
  }

  @Override
  public void displayError(String errorMessage) {
    System.out.println(ERROR_PREFIX + errorMessage);
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



