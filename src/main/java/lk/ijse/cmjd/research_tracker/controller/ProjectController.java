package lk.ijse.cmjd.research_tracker.controller;

import jakarta.validation.Valid;
import lk.ijse.cmjd.research_tracker.common.ApiResponse;
import lk.ijse.cmjd.research_tracker.dto.ProjectDTO;
import lk.ijse.cmjd.research_tracker.enums.Status;
import lk.ijse.cmjd.research_tracker.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for project management endpoints.
 * Supports full CRUD operations with role-based access control.
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    /**
     * Constructor injection of ProjectService.
     */
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * GET /api/projects - List all projects.
     *
     * @return list of all project DTOs
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectDTO>>> getAllProjects() {
        List<ProjectDTO> projects = projectService.getAllProjects();
        return ResponseEntity.ok(ApiResponse.success("Projects retrieved successfully", projects));
    }

    /**
     * GET /api/projects/{id} - View project details.
     *
     * @param id the project ID
     * @return project DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectDTO>> getProjectById(@PathVariable String id) {
        ProjectDTO project = projectService.getProjectById(id);
        return ResponseEntity.ok(ApiResponse.success("Project retrieved successfully", project));
    }

    /**
     * POST /api/projects - Create a new project (PI or Admin only).
     *
     * @param projectDTO the project data
     * @return created project DTO
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProjectDTO>> createProject(
            @Valid @RequestBody ProjectDTO projectDTO
    ) {
        ProjectDTO created = projectService.createProject(projectDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Project created successfully", created));
    }

    /**
     * PUT /api/projects/{id} - Update project details.
     *
     * @param id         the project ID
     * @param projectDTO the updated project data
     * @return updated project DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectDTO>> updateProject(
            @PathVariable String id,
            @Valid @RequestBody ProjectDTO projectDTO
    ) {
        ProjectDTO updated = projectService.updateProject(id, projectDTO);
        return ResponseEntity.ok(ApiResponse.success("Project updated successfully", updated));
    }

    /**
     * PATCH /api/projects/{id}/status - Update project status.
     *
     * @param id      the project ID
     * @param request map containing the "status" field
     * @return updated project DTO
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ProjectDTO>> updateProjectStatus(
            @PathVariable String id,
            @RequestBody Map<String, String> request
    ) {
        Status status = Status.valueOf(request.get("status"));
        ProjectDTO updated = projectService.updateProjectStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Project status updated successfully", updated));
    }

    /**
     * DELETE /api/projects/{id} - Delete project (Admin only).
     *
     * @param id the project ID
     * @return success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(ApiResponse.success("Project deleted successfully"));
    }
}
