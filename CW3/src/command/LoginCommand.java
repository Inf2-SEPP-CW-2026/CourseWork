package command;

import controller.Controller;

/**
 * Logs a registered user into the system.
 */
public class LoginCommand extends AbstractCommand {
    private final String email;
    private final String password;

    public LoginCommand(String email, String password) {
        super("Log in");
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void execute(Controller controller) {
        throw notImplemented();
    }
}
