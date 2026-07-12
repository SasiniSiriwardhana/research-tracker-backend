package lk.ijse.cmjd.research_tracker.service.impl;

import lk.ijse.cmjd.research_tracker.dto.MilestoneDTO;
import lk.ijse.cmjd.research_tracker.entity.Milestone;
import lk.ijse.cmjd.research_tracker.entity.Project;
import lk.ijse.cmjd.research_tracker.entity.User;
import lk.ijse.cmjd.research_tracker.exception.ResourceNotFoundException;
import lk.ijse.cmjd.research_tracker.repository.MilestoneRepository;
import lk.ijse.cmjd.research_tracker.repository.ProjectRepository;
import lk.ijse.cmjd.research_tracker.repository.UserRepository;
import lk.ijse.cmjd.research_tracker.service.MilestoneService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of MilestoneService.
 * Handles milestone CRUD operations scoped to projects.
 */
@Service
public class MilestoneServiceImpl implements MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    /**
     * Constructor injection of repositories.
     */
    public MilestoneServiceImpl(
            MilestoneRepository milestoneRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository
    ) {
        this.milestoneRepository = milestoneRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    /**
     * Get all milestones for a specific project.
     */
    @Override
    public List<MilestoneDTO> getMilestonesByProject(String projectId) {
        // Verify project exists
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "id", projectId);
        }

        return milestoneRepository.findByProjectId(projectId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new milestone for a project.
     * Associates the milestone with the authenticated user as creator.
     */
    @Override
    public MilestoneDTO createMilestone(String projectId, MilestoneDTO milestoneDTO, String username) {
        // Find the project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));

        // Find the creator user by username
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Build and save the milestone entity
        Milestone milestone = Milestone.builder()
                .project(project)
                .title(milestoneDTO.getTitle())
                .description(milestoneDTO.getDescription())
                .dueDate(milestoneDTO.getDueDate())
                .isCompleted(milestoneDTO.getIsCompleted() != null ? milestoneDTO.getIsCompleted() : false)
                .createdBy(creator)
                .build();

        Milestone savedMilestone = milestoneRepository.save(milestone);
        return mapToDTO(savedMilestone);
    }

    /**
     * Update an existing milestone.
     */
    @Override
    public MilestoneDTO updateMilestone(String id, MilestoneDTO milestoneDTO) {
        Milestone milestone = milestoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Milestone", "id", id));

        // Update fields
        milestone.setTitle(milestoneDTO.getTitle());
        milestone.setDescription(milestoneDTO.getDescription());
        milestone.setDueDate(milestoneDTO.getDueDate());
        if (milestoneDTO.getIsCompleted() != null) {
            milestone.setIsCompleted(milestoneDTO.getIsCompleted());
        }

        Milestone updatedMilestone = milestoneRepository.save(milestone);
        return mapToDTO(updatedMilestone);
    }

    /**
     * Delete a milestone by its ID.
     */
    @Override
    public void deleteMilestone(String id) {
        Milestone milestone = milestoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Milestone", "id", id));
        milestoneRepository.delete(milestone);
    }

    /**
     * Map a Milestone entity to a MilestoneDTO.
     */
    private MilestoneDTO mapToDTO(Milestone milestone) {
        return MilestoneDTO.builder()
                .id(milestone.getId())
                .projectId(milestone.getProject().getId())
                .title(milestone.getTitle())
                .description(milestone.getDescription())
                .dueDate(milestone.getDueDate())
                .isCompleted(milestone.getIsCompleted())
                .createdById(milestone.getCreatedBy().getId())
                .createdByName(milestone.getCreatedBy().getFullName())
                .build();
    }
}
