package logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Minimal in-memory logger for command execution and test cleanup.
 */
public final class Logger {
    private static final Logger INSTANCE = new Logger();

    private final List<String> entries;

    private Logger() {
        this.entries = new ArrayList<>();
    }

    public static Logger getInstance() {
        return INSTANCE;
    }

    public void log(String message) {
        entries.add(message);
    }

    public List<String> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public void clearLog() {
        entries.clear();
    }
}
