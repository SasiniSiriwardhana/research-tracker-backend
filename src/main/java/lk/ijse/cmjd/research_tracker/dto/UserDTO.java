package lk.ijse.cmjd.research_tracker.dto;

import lk.ijse.cmjd.research_tracker.enums.UserRole;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO for transferring user data (excludes password for security).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    /** User ID */
    private String id;

    /** Username or email */
    private String username;

    /** User's full name */
    private String fullName;

    /** Role of the user */
    private UserRole role;

    /** Account creation timestamp */
    private LocalDateTime createdAt;
}
