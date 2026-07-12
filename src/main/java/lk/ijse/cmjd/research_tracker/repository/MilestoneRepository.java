package lk.ijse.cmjd.research_tracker.repository;

import lk.ijse.cmjd.research_tracker.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Milestone entity database operations.
 */
@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, String> {

    /**
     * Find all milestones belonging to a specific project.
     *
     * @param projectId the project ID
     * @return list of milestones for the given project
     */
    List<Milestone> findByProjectId(String projectId);
}
