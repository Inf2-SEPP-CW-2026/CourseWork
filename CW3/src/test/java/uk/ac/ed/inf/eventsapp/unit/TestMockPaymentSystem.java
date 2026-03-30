package uk.ac.ed.inf.eventsapp.unit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import external.MockPaymentSystem;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unit-test scaffold for the provided mock payment system.
 */
public class TestMockPaymentSystem {
  @Test
  @Disabled("TODO: implement MockPaymentSystem unit tests.")
  void processPaymentAcceptsValidInput() {
    MockPaymentSystem paymentSystem = new MockPaymentSystem();

    assertTrue(paymentSystem.processPayment(2, "Live Music", "student@example.com", 123456789,
        "provider@example.com", 30.0), "TODO: replace with scenario-specific checks.");
  }
}
