package lk.ijse.cmjd.research_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO for transferring milestone data between layers.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MilestoneDTO {

    /** Milestone ID (auto-generated, ignored on create) */
    private String id;

    /** Associated project ID */
    private String projectId;

    /** Milestone title */
    @NotBlank(message = "Milestone title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    /** Notes or task details */
    private String description;

    /** Deadline date */
    private LocalDate dueDate;

    /** Completion status */
    private Boolean isCompleted;

    /** ID of the user who created this milestone */
    private String createdById;

    /** Name of the user who created this milestone (read-only) */
    private String createdByName;
}
