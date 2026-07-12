package lk.ijse.cmjd.research_tracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Milestone entity representing key stages or deliverables of a research project.
 * Each milestone belongs to a project and tracks completion status.
 */
@Entity
@Table(name = "milestones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Milestone {

    /** Unique identifier for the milestone */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /** The project this milestone belongs to */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    /** Title of the milestone */
    @Column(nullable = false, length = 255)
    private String title;

    /** Notes or task details */
    @Column(columnDefinition = "TEXT")
    private String description;

    /** Deadline date for this milestone */
    private LocalDate dueDate;

    /** Whether this milestone has been completed */
    @Column(nullable = false)
    @Builder.Default
    private Boolean isCompleted = false;

    /** User who created this milestone */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
}
