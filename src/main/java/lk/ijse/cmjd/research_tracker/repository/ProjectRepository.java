package lk.ijse.cmjd.research_tracker.repository;

import lk.ijse.cmjd.research_tracker.entity.Project;
import lk.ijse.cmjd.research_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Project entity database operations.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

    /**
     * Find all projects assigned to a specific Principal Investigator.
     *
     * @param pi the Principal Investigator user
     * @return list of projects for the given PI
     */
    List<Project> findByPi(User pi);

    /**
     * Find all projects by PI's user ID.
     *
     * @param piId the PI user ID
     * @return list of projects for the given PI ID
     */
    List<Project> findByPiId(String piId);
}
