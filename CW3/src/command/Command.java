package command;

import controller.Controller;

/**
 * Contract for all use-case commands.
 */
public interface Command {
    /**
     * @return human-readable use-case name
     */
    String getName();

    /**
     * Executes the command.
     *
     * @param controller application controller
     */
    void execute(Controller controller);
}
