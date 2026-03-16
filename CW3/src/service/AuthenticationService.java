package service;

import model.User;

/**
 * Handles authentication and session establishment.
 */
public interface AuthenticationService {
    User login(String email, String password);

    void logout(String email);
}
