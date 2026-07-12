package lk.ijse.cmjd.research_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO for transferring document data between layers.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentDTO {

    /** Document ID (auto-generated, ignored on create) */
    private String id;

    /** Associated project ID */
    private String projectId;

    /** File name or label */
    @NotBlank(message = "Document title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    /** Notes or summary */
    private String description;

    /** File URL or server path */
    @NotBlank(message = "URL or file path is required")
    @Size(max = 500, message = "URL/path must not exceed 500 characters")
    private String urlOrPath;

    /** ID of the user who uploaded this document */
    private String uploadedById;

    /** Name of the user who uploaded (read-only) */
    private String uploadedByName;

    /** Timestamp of upload (read-only) */
    private LocalDateTime uploadedAt;
}
