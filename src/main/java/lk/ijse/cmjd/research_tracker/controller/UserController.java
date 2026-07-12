package lk.ijse.cmjd.research_tracker.controller;

import lk.ijse.cmjd.research_tracker.common.ApiResponse;
import lk.ijse.cmjd.research_tracker.dto.UserDTO;
import lk.ijse.cmjd.research_tracker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for user management endpoints.
 * Admin-only access for listing and deleting users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructor injection of UserService.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET /api/users - List all users (Admin only).
     *
     * @return list of all user DTOs
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", users));
    }

    /**
     * GET /api/users/{id} - View user profile.
     *
     * @param id the user ID
     * @return user DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable String id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", user));
    }

    /**
     * DELETE /api/users/{id} - Delete user (Admin only).
     *
     * @param id the user ID
     * @return success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully"));
    }
}
