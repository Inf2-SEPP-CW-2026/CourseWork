package command;

/**
 * Shared helper for placeholder use-case commands.
 */
public abstract class AbstractCommand implements Command {
    private final String name;

    protected AbstractCommand(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    protected UnsupportedOperationException notImplemented() {
        return new UnsupportedOperationException(name + " is not implemented yet.");
    }
}
