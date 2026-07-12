package lk.ijse.cmjd.research_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO for user login / authentication requests.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    /** Username or email */
    @NotBlank(message = "Username is required")
    private String username;

    /** Password */
    @NotBlank(message = "Password is required")
    private String password;
}
