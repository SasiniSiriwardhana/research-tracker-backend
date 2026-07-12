package lk.ijse.cmjd.research_tracker.service.impl;

import lk.ijse.cmjd.research_tracker.dto.AuthResponse;
import lk.ijse.cmjd.research_tracker.dto.LoginRequest;
import lk.ijse.cmjd.research_tracker.dto.RegisterRequest;
import lk.ijse.cmjd.research_tracker.entity.User;
import lk.ijse.cmjd.research_tracker.enums.UserRole;
import lk.ijse.cmjd.research_tracker.exception.BadRequestException;
import lk.ijse.cmjd.research_tracker.repository.UserRepository;
import lk.ijse.cmjd.research_tracker.security.JwtUtil;
import lk.ijse.cmjd.research_tracker.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Implementation of AuthService.
 * Handles user registration with BCrypt password encoding
 * and login with JWT token generation.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructor injection of all dependencies.
     */
    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Register a new user with the default MEMBER role.
     * Encodes the password using BCrypt and generates a JWT token.
     */
    @Override
    public AuthResponse register(RegisterRequest request) {

        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username '" + request.getUsername() + "' is already taken");
        }

        // Build and save the new user entity
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role(UserRole.MEMBER) // Default role is MEMBER
                .build();

        User savedUser = userRepository.save(user);

        // Create UserDetails for JWT generation
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                savedUser.getUsername(),
                savedUser.getPassword(),
                Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + savedUser.getRole().name())
                )
        );

        // Generate JWT token
        String token = jwtUtil.generateToken(userDetails);

        // Build and return the authentication response
        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(savedUser.getId())
                .username(savedUser.getUsername())
                .role(savedUser.getRole().name())
                .build();
    }

    /**
     * Authenticate a user with their credentials and issue a JWT token.
     */
    @Override
    public AuthResponse login(LoginRequest request) {

        // Authenticate using Spring Security's AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Load the user from the database
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("User not found"));

        // Create UserDetails for JWT generation
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
                )
        );

        // Generate JWT token
        String token = jwtUtil.generateToken(userDetails);

        // Build and return the authentication response
        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}
