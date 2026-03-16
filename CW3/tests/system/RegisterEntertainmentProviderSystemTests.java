package system;

import command.RegisterEntertainmentProviderCommand;
import controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * System-test scaffold for entertainment-provider registration.
 */
public class RegisterEntertainmentProviderSystemTests {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @Test
    @Disabled("TODO: implement entertainment-provider registration system tests.")
    void providerCanBeRegisteredWithValidBusinessNumber() {
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "Provider",
                "provider@example.com",
                "1234567890"));
    }
}
