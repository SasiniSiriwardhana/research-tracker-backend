package lk.ijse.cmjd.research_tracker.dto;

import lombok.*;

/**
 * DTO for authentication response containing JWT token and user details.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    /** JWT access token */
    private String token;

    /** Token type (always "Bearer") */
    @Builder.Default
    private String tokenType = "Bearer";

    /** Authenticated user's ID */
    private String userId;

    /** Authenticated user's username */
    private String username;

    /** Authenticated user's role */
    private String role;
}
