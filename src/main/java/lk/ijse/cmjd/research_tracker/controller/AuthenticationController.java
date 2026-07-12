package lk.ijse.cmjd.research_tracker.controller;

import jakarta.validation.Valid;
import lk.ijse.cmjd.research_tracker.common.ApiResponse;
import lk.ijse.cmjd.research_tracker.dto.AuthResponse;
import lk.ijse.cmjd.research_tracker.dto.LoginRequest;
import lk.ijse.cmjd.research_tracker.dto.RegisterRequest;
import lk.ijse.cmjd.research_tracker.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for authentication endpoints.
 * Handles user registration and login with JWT token issuance.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthService authService;

    /**
     * Constructor injection of AuthService.
     */
    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * POST /api/auth/signup - Register a new user.
     * Default role assigned: MEMBER.
     *
     * @param request registration details (username, password, fullName)
     * @return JWT token and user details
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        AuthResponse authResponse = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", authResponse));
    }

    /**
     * POST /api/auth/login - Authenticate user and issue JWT token.
     *
     * @param request login credentials (username, password)
     * @return JWT token and user details
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        AuthResponse authResponse = authService.login(request);
        return ResponseEntity
                .ok(ApiResponse.success("Login successful", authResponse));
    }
}
