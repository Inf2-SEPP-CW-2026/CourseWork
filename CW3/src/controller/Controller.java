package controller;

import command.Command;
import logging.Logger;

/**
 * Entry point for use-case commands.
 */
public class Controller {
    private final Logger logger;

    public Controller() {
        this(Logger.getInstance());
    }

    public Controller(Logger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("logger must not be null");
        }
        this.logger = logger;
    }

    /**
     * Runs a command against the current application context.
     *
     * @param command use-case command to run
     */
    public void runCommand(Command command) {
        if (command == null) {
            throw new IllegalArgumentException("command must not be null");
        }
        logger.log("Executing use case: " + command.getName());
        command.execute(this);
    }

    public Logger getLogger() {
        return logger;
    }
}
