package lk.ijse.cmjd.research_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.ijse.cmjd.research_tracker.enums.Status;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for transferring project data between layers.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDTO {

    /** Project ID (auto-generated, ignored on create) */
    private String id;

    /** Title of the project */
    @NotBlank(message = "Project title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    /** Short description / summary */
    private String summary;

    /** Project status */
    @NotNull(message = "Project status is required")
    private Status status;

    /** Principal Investigator's user ID */
    @NotBlank(message = "Principal Investigator (PI) ID is required")
    private String piId;

    /** PI's full name (read-only, populated on fetch) */
    private String piName;

    /** Comma-separated tags */
    private String tags;

    /** Project start date */
    private LocalDate startDate;

    /** Expected completion date */
    private LocalDate endDate;

    /** Creation timestamp (read-only) */
    private LocalDateTime createdAt;

    /** Last update timestamp (read-only) */
    private LocalDateTime updatedAt;
}
