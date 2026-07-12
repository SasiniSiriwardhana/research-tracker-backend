package lk.ijse.cmjd.research_tracker.repository;

import lk.ijse.cmjd.research_tracker.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Document entity database operations.
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {

    /**
     * Find all documents belonging to a specific project.
     *
     * @param projectId the project ID
     * @return list of documents for the given project
     */
    List<Document> findByProjectId(String projectId);
}
