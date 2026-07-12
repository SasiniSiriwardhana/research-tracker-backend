package lk.ijse.cmjd.research_tracker.service;

import lk.ijse.cmjd.research_tracker.dto.MilestoneDTO;

import java.util.List;

/**
 * Service interface for milestone management operations.
 */
public interface MilestoneService {

    /**
     * Get all milestones for a specific project.
     *
     * @param projectId the project ID
     * @return list of milestone DTOs
     */
    List<MilestoneDTO> getMilestonesByProject(String projectId);

    /**
     * Create a new milestone for a project.
     *
     * @param projectId    the project ID
     * @param milestoneDTO the milestone data
     * @param username     the username of the creator
     * @return created milestone DTO
     */
    MilestoneDTO createMilestone(String projectId, MilestoneDTO milestoneDTO, String username);

    /**
     * Update an existing milestone.
     *
     * @param id           the milestone ID
     * @param milestoneDTO the updated milestone data
     * @return updated milestone DTO
     */
    MilestoneDTO updateMilestone(String id, MilestoneDTO milestoneDTO);

    /**
     * Delete a milestone by its ID.
     *
     * @param id the milestone ID
     */
    void deleteMilestone(String id);
}
