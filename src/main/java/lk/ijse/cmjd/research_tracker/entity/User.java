package lk.ijse.cmjd.research_tracker.entity;

import jakarta.persistence.*;
import lk.ijse.cmjd.research_tracker.enums.UserRole;
import lombok.*;

import java.time.LocalDateTime;

/**
 * User entity representing any registered system user.
 * Roles: ADMIN, PI (Principal Investigator), MEMBER, VIEWER.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /** Unique identifier for the user */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /** Unique username or email */
    @Column(nullable = false, unique = true, length = 100)
    private String username;

    /** Encrypted password (BCrypt) */
    @Column(nullable = false)
    private String password;

    /** User's full name */
    @Column(nullable = false, length = 150)
    private String fullName;

    /** Role of the user within the system */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;

    /** Account creation timestamp */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** Automatically set createdAt before persisting */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
