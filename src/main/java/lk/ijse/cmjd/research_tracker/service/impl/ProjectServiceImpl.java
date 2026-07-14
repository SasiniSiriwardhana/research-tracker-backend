package lk.ijse.cmjd.research_tracker.service.impl;

import lk.ijse.cmjd.research_tracker.dto.ProjectDTO;
import lk.ijse.cmjd.research_tracker.entity.Project;
import lk.ijse.cmjd.research_tracker.entity.User;
import lk.ijse.cmjd.research_tracker.enums.Status;
import lk.ijse.cmjd.research_tracker.exception.ResourceNotFoundException;
import lk.ijse.cmjd.research_tracker.repository.ProjectRepository;
import lk.ijse.cmjd.research_tracker.repository.UserRepository;
import lk.ijse.cmjd.research_tracker.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of ProjectService.
 * Handles project CRUD operations and status updates.
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    /**
     * Constructor injection of repositories.
     */
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieve all projects.
     */
    @Override
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a project by its ID.
     */
    @Override
    public ProjectDTO getProjectById(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        return mapToDTO(project);
    }

    /**
     * Create a new research project.
     * Validates that the referenced PI exists.
     */
    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        // Find the Principal Investigator
        User pi = userRepository.findById(projectDTO.getPiId())
                .orElseThrow(() -> new ResourceNotFoundException("User (PI)", "id", projectDTO.getPiId()));

        // Build and save the project entity
        Project project = Project.builder()
                .title(projectDTO.getTitle())
                .summary(projectDTO.getSummary())
                .status(projectDTO.getStatus())
                .pi(pi)
                .tags(projectDTO.getTags())
                .startDate(projectDTO.getStartDate())
                .endDate(projectDTO.getEndDate())
                .build();

        Project savedProject = projectRepository.save(project);
        return mapToDTO(savedProject);
    }

    /**
     * Update an existing project.
     * All mutable fields are updated; PI reference is also re-validated.
     */
    @Override
    public ProjectDTO updateProject(String id, ProjectDTO projectDTO) {
        // Find existing project
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));

        // Find the Principal Investigator
        User pi = userRepository.findById(projectDTO.getPiId())
                .orElseThrow(() -> new ResourceNotFoundException("User (PI)", "id", projectDTO.getPiId()));

        // Update fields
        project.setTitle(projectDTO.getTitle());
        project.setSummary(projectDTO.getSummary());
        project.setStatus(projectDTO.getStatus());
        project.setPi(pi);
        project.setTags(projectDTO.getTags());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());

        Project updatedProject = projectRepository.save(project);
        return mapToDTO(updatedProject);
    }

    /**
     * Update only the status of a project.
     */
    @Override
    public ProjectDTO updateProjectStatus(String id, Status status) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));

        project.setStatus(status);
        Project updatedProject = projectRepository.save(project);
        return mapToDTO(updatedProject);
    }

    /**
     * Delete a project by its ID.
     */
    @Override
    public void deleteProject(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        projectRepository.delete(project);
    }

    /**
     * Map a Project entity to a ProjectDTO.
     */
    private ProjectDTO mapToDTO(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .title(project.getTitle())
                .summary(project.getSummary())
                .status(project.getStatus())
                .piId(project.getPi().getId())
                .piName(project.getPi().getFullName())
                .tags(project.getTags())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}
