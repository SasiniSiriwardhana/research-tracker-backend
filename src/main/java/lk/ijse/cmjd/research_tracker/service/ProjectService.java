package lk.ijse.cmjd.research_tracker.service;

import lk.ijse.cmjd.research_tracker.dto.ProjectDTO;
import lk.ijse.cmjd.research_tracker.enums.Status;

import java.util.List;

/**
 * Service interface for project management operations.
 */
public interface ProjectService {

    /**
     * Get all projects.
     *
     * @return list of project DTOs
     */
    List<ProjectDTO> getAllProjects();

    /**
     * Get a project by its ID.
     *
     * @param id the project ID
     * @return project DTO
     */
    ProjectDTO getProjectById(String id);

    /**
     * Create a new project.
     *
     * @param projectDTO the project data
     * @return created project DTO
     */
    ProjectDTO createProject(ProjectDTO projectDTO);

    /**
     * Update an existing project.
     *
     * @param id         the project ID
     * @param projectDTO the updated project data
     * @return updated project DTO
     */
    ProjectDTO updateProject(String id, ProjectDTO projectDTO);

    /**
     * Update the status of a project.
     *
     * @param id     the project ID
     * @param status the new status
     * @return updated project DTO
     */
    ProjectDTO updateProjectStatus(String id, Status status);

    /**
     * Delete a project by its ID.
     *
     * @param id the project ID
     */
    void deleteProject(String id);
}
