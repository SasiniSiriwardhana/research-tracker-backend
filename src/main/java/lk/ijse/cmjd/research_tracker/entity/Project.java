package lk.ijse.cmjd.research_tracker.entity;

import jakarta.persistence.*;
import lk.ijse.cmjd.research_tracker.enums.Status;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Project entity representing an academic or institutional research project.
 * Each project is linked to a Principal Investigator (PI).
 */
@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    /** Unique identifier for the project */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /** Title of the research project */
    @Column(nullable = false, length = 255)
    private String title;

    /** Short description / summary of the project */
    @Column(columnDefinition = "TEXT")
    private String summary;

    /** Current lifecycle status of the project */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    /** Principal Investigator assigned to this project */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pi_id", nullable = false)
    private User pi;

    /** Comma-separated tags (e.g., "AI, environment") */
    @Column(length = 500)
    private String tags;

    /** Project start date */
    private LocalDate startDate;

    /** Expected completion date */
    private LocalDate endDate;

    /** Audit field: project creation timestamp */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** Audit field: last update timestamp */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /** Automatically set timestamps before persisting */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /** Automatically update the updatedAt timestamp on modification */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
