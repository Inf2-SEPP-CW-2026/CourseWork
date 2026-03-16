package command;

import controller.Controller;

/**
 * Registers a new entertainment provider.
 */
public class RegisterEntertainmentProviderCommand extends AbstractCommand {
    private final String providerName;
    private final String email;
    private final String businessRegistrationNumber;

    public RegisterEntertainmentProviderCommand(
            String providerName,
            String email,
            String businessRegistrationNumber) {
        super("Register entertainment provider");
        this.providerName = providerName;
        this.email = email;
        this.businessRegistrationNumber = businessRegistrationNumber;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getEmail() {
        return email;
    }

    public String getBusinessRegistrationNumber() {
        return businessRegistrationNumber;
    }

    @Override
    public void execute(Controller controller) {
        throw notImplemented();
    }
}
