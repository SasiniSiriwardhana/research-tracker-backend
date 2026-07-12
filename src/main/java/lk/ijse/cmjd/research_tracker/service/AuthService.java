package lk.ijse.cmjd.research_tracker.service;

import lk.ijse.cmjd.research_tracker.dto.AuthResponse;
import lk.ijse.cmjd.research_tracker.dto.LoginRequest;
import lk.ijse.cmjd.research_tracker.dto.RegisterRequest;

/**
 * Service interface for authentication operations.
 */
public interface AuthService {

    /**
     * Register a new user with default MEMBER role.
     *
     * @param request registration details
     * @return authentication response with JWT token
     */
    AuthResponse register(RegisterRequest request);

    /**
     * Authenticate a user and issue a JWT token.
     *
     * @param request login credentials
     * @return authentication response with JWT token
     */
    AuthResponse login(LoginRequest request);
}
