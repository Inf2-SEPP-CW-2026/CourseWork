package uk.ac.ed.inf.eventsapp.view;

import java.util.Collection;
import java.util.Set;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

/**
 * Text-based CLI
 */
public class TextUserInterface implements View {
  private static final String RESET = "\033[0m";
  private static final String ERROR_PREFIX = "\033[1;31m[ERROR]: " + RESET;
  private static final String SUCCESS_PREFIX = "\033[1;34m[SUCCESS]: " + RESET;
  private static final Set<String> EXIT_SEQUENCES = Set.of(":q", ":quit", ":exit");
  private final LineReader lineReader;

  public TextUserInterface() {
    this.lineReader = createLineReader();
  }

  @Override
  public String getInput(String inputPrompt) {
    return getInput(inputPrompt, ": ");
  }

  @Override
  public String getInput(String inputPrompt, String promptEnd) {
    String input = lineReader.readLine(inputPrompt + promptEnd);
    if (isExitSequence(input)) {
      throw new ExitRequestedException();
    }
    return input;
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

  private LineReader createLineReader() {
    try {
      Terminal terminal = TerminalBuilder.builder().system(true).build();
      return LineReaderBuilder.builder().terminal(terminal).build();
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to initialise terminal input.", exception);
    }
  }

  static boolean isExitSequence(String input) {
    return input != null && EXIT_SEQUENCES.contains(input.trim().toLowerCase());
  }
}
