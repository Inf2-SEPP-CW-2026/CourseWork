package uk.ac.ed.inf.eventsapp.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import uk.ac.ed.inf.eventsapp.model.EventType;

/**
 * Shared parsing helpers for user-entered text values.
 */
public final class InputParsers {
  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static final Pattern MONEY_PATTERN = Pattern.compile("^(?:0|[1-9]\\d*)(?:\\.\\d{1,2})?$");

  private InputParsers() {}

  public static EventType parseEventType(String rawType) {
    if (rawType == null) {
      return null;
    }

    String normalised = rawType.trim().toUpperCase().replace(' ', '_');
    if (normalised.isEmpty()) {
      return null;
    }

    if ("THEATER".equals(normalised)) {
      normalised = "THEATRE";
    }

    try {
      return EventType.valueOf(normalised);
    } catch (IllegalArgumentException exception) {
      return null;
    }
  }

  public static Boolean parseBoolean(String rawBoolean) {
    if (rawBoolean == null) {
      return null;
    }

    String normalised = rawBoolean.trim().toLowerCase();
    return switch (normalised) {
      case "y", "yes", "true", "ticketed", "outdoors", "smoking" -> Boolean.TRUE;
      case "n", "no", "false", "non-ticketed", "indoors", "non-smoking" -> Boolean.FALSE;
      default -> null;
    };
  }

  public static Integer parsePositiveInteger(String rawInteger) {
    Integer parsed = parseNonNegativeInteger(rawInteger);
    return parsed != null && parsed > 0 ? parsed : null;
  }

  public static Long parsePositiveLong(String rawLong) {
    if (rawLong == null) {
      return null;
    }

    try {
      long parsed = Long.parseLong(rawLong.trim());
      return parsed > 0 ? parsed : null;
    } catch (NumberFormatException exception) {
      return null;
    }
  }

  public static Integer parseNonNegativeInteger(String rawInteger) {
    if (rawInteger == null) {
      return null;
    }

    try {
      int parsed = Integer.parseInt(rawInteger.trim());
      return parsed >= 0 ? parsed : null;
    } catch (NumberFormatException exception) {
      return null;
    }
  }

  public static Double parseNonNegativeDouble(String rawDouble) {
    if (rawDouble == null) {
      return null;
    }

    String trimmedValue = rawDouble.trim();
    if (!MONEY_PATTERN.matcher(trimmedValue).matches()) {
      return null;
    }

    try {
      double parsed = Double.parseDouble(trimmedValue);
      return parsed >= 0.0 ? parsed : null;
    } catch (NumberFormatException exception) {
      return null;
    }
  }

  public static LocalDateTime parseDateTime(String rawDateTime) {
    if (rawDateTime == null) {
      return null;
    }

    try {
      return LocalDateTime.parse(rawDateTime.trim(), DATE_TIME_FORMATTER);
    } catch (DateTimeParseException exception) {
      return null;
    }
  }

  public static LocalDate parseDate(String rawDate) {
    if (rawDate == null) {
      return null;
    }

    try {
      return LocalDate.parse(rawDate.trim(), DATE_FORMATTER);
    } catch (DateTimeParseException exception) {
      return null;
    }
  }

  public static List<String> parseCommaSeparatedValues(String rawValues) {
    List<String> values = new ArrayList<>();
    if (rawValues == null) {
      return values;
    }

    for (String rawValue : rawValues.split(",")) {
      String trimmedValue = rawValue.trim();
      if (!trimmedValue.isEmpty()) {
        values.add(trimmedValue);
      }
    }

    return values;
  }
}
