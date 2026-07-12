package lk.ijse.cmjd.research_tracker.service;

import lk.ijse.cmjd.research_tracker.dto.UserDTO;

import java.util.List;

/**
 * Service interface for user management operations.
 */
public interface UserService {

    /**
     * Get all registered users.
     *
     * @return list of user DTOs
     */
    List<UserDTO> getAllUsers();

    /**
     * Get a user by their ID.
     *
     * @param id the user ID
     * @return user DTO
     */
    UserDTO getUserById(String id);

    /**
     * Delete a user by their ID.
     *
     * @param id the user ID
     */
    void deleteUser(String id);
}
