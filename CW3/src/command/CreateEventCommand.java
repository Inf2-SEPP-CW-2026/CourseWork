package command;

import controller.Controller;

/**
 * Creates a new event owned by an entertainment provider.
 */
public class CreateEventCommand extends AbstractCommand {
    private final String providerEmail;
    private final String title;
    private final String description;

    public CreateEventCommand(String providerEmail, String title, String description) {
        super("Create event");
        this.providerEmail = providerEmail;
        this.title = title;
        this.description = description;
    }

    public String getProviderEmail() {
        return providerEmail;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void execute(Controller controller) {
        throw notImplemented();
    }
}
