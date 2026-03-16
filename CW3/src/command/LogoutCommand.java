package command;

import controller.Controller;

/**
 * Logs the current user out of the system.
 */
public class LogoutCommand extends AbstractCommand {
    public LogoutCommand() {
        super("Log out");
    }

    @Override
    public void execute(Controller controller) {
        throw notImplemented();
    }
}
