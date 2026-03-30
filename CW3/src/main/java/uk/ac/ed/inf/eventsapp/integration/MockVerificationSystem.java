package uk.ac.ed.inf.eventsapp.integration;

/**
 * Adapter around the provided external mock verification service.
 */
public class MockVerificationSystem implements VerificationSystem {
  private final external.VerificationService delegate;

  public MockVerificationSystem() {
    this(new external.MockVerificationService());
  }

  public MockVerificationSystem(external.VerificationService delegate) {
    this.delegate = delegate;
  }

  @Override
  public boolean verifyEntertainmentProvider(String businessRegistrationNumber) {
    return delegate.verifyEntertainmentProvider(businessRegistrationNumber);
  }
}
