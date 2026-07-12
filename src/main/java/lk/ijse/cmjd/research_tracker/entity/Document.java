package lk.ijse.cmjd.research_tracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Document entity representing research-related files or reference materials
 * uploaded to the system. Each document is linked to a project.
 */
@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    /** Unique identifier for the document */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /** The project this document belongs to */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    /** File name or label */
    @Column(nullable = false, length = 255)
    private String title;

    /** Notes or summary about the document */
    @Column(columnDefinition = "TEXT")
    private String description;

    /** File URL or server path */
    @Column(nullable = false, length = 500)
    private String urlOrPath;

    /** User who uploaded the file */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by", nullable = false)
    private User uploadedBy;

    /** Timestamp of upload */
    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    /** Automatically set uploadedAt before persisting */
    @PrePersist
    protected void onCreate() {
        this.uploadedAt = LocalDateTime.now();
    }
}
