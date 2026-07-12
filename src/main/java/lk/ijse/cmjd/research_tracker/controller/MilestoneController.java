package lk.ijse.cmjd.research_tracker.controller;

import jakarta.validation.Valid;
import lk.ijse.cmjd.research_tracker.common.ApiResponse;
import lk.ijse.cmjd.research_tracker.dto.MilestoneDTO;
import lk.ijse.cmjd.research_tracker.service.MilestoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for milestone management endpoints.
 * Milestones are scoped to projects.
 */
@RestController
public class MilestoneController {

    private final MilestoneService milestoneService;

    /**
     * Constructor injection of MilestoneService.
     */
    public MilestoneController(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    /**
     * GET /api/projects/{id}/milestones - List milestones for a project.
     *
     * @param id the project ID
     * @return list of milestone DTOs
     */
    @GetMapping("/api/projects/{id}/milestones")
    public ResponseEntity<ApiResponse<List<MilestoneDTO>>> getMilestonesByProject(
            @PathVariable String id
    ) {
        List<MilestoneDTO> milestones = milestoneService.getMilestonesByProject(id);
        return ResponseEntity.ok(ApiResponse.success("Milestones retrieved successfully", milestones));
    }

    /**
     * POST /api/projects/{id}/milestones - Add a milestone to a project.
     * The creator is automatically set to the authenticated user.
     *
     * @param id             the project ID
     * @param milestoneDTO   the milestone data
     * @param authentication the current authentication context
     * @return created milestone DTO
     */
    @PostMapping("/api/projects/{id}/milestones")
    public ResponseEntity<ApiResponse<MilestoneDTO>> createMilestone(
            @PathVariable String id,
            @Valid @RequestBody MilestoneDTO milestoneDTO,
            Authentication authentication
    ) {
        MilestoneDTO created = milestoneService.createMilestone(id, milestoneDTO, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Milestone created successfully", created));
    }

    /**
     * PUT /api/milestones/{id} - Update a milestone.
     *
     * @param id           the milestone ID
     * @param milestoneDTO the updated milestone data
     * @return updated milestone DTO
     */
    @PutMapping("/api/milestones/{id}")
    public ResponseEntity<ApiResponse<MilestoneDTO>> updateMilestone(
            @PathVariable String id,
            @Valid @RequestBody MilestoneDTO milestoneDTO
    ) {
        MilestoneDTO updated = milestoneService.updateMilestone(id, milestoneDTO);
        return ResponseEntity.ok(ApiResponse.success("Milestone updated successfully", updated));
    }

    /**
     * DELETE /api/milestones/{id} - Delete a milestone.
     *
     * @param id the milestone ID
     * @return success message
     */
    @DeleteMapping("/api/milestones/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteMilestone(@PathVariable String id) {
        milestoneService.deleteMilestone(id);
        return ResponseEntity.ok(ApiResponse.success("Milestone deleted successfully"));
    }
}
