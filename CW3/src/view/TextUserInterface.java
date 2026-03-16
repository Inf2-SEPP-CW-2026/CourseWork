package view;

import java.util.Collection;

/**
 * Text-based UI placeholder.
 */
public class TextUserInterface implements View {
  @Override
  public String getInput(String inputPrompt) {
    throw new UnsupportedOperationException("getInput is not implemented yet.");
  }

  @Override
  public void displaySuccess(String successMessage) {
    throw new UnsupportedOperationException("displaySuccess is not implemented yet.");
  }

  @Override
  public void displayError(String errorMessage) {
    throw new UnsupportedOperationException("displayError is not implemented yet.");
  }

  @Override
  public void displayListOfPerformances(Collection<String> listOfPerformanceInfo) {
    throw new UnsupportedOperationException("displayListOfPerformances is not implemented yet.");
  }

  @Override
  public void displaySpecificPerformance(String performanceInfo) {
    throw new UnsupportedOperationException("displaySpecificPerformance is not implemented yet.");
  }

  @Override
  public void displayBookingRecord(String bookingRecord) {
    throw new UnsupportedOperationException("displayBookingRecord is not implemented yet.");
  }
}
