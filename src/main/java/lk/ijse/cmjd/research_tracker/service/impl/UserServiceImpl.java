package lk.ijse.cmjd.research_tracker.service.impl;

import lk.ijse.cmjd.research_tracker.dto.UserDTO;
import lk.ijse.cmjd.research_tracker.entity.User;
import lk.ijse.cmjd.research_tracker.exception.ResourceNotFoundException;
import lk.ijse.cmjd.research_tracker.repository.UserRepository;
import lk.ijse.cmjd.research_tracker.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of UserService.
 * Handles user retrieval and deletion operations.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Constructor injection of UserRepository.
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieve all registered users.
     */
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a user by their ID.
     */
    @Override
    public UserDTO getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return mapToDTO(user);
    }

    /**
     * Delete a user by their ID.
     */
    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
    }

    /**
     * Map a User entity to a UserDTO (excludes password).
     */
    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
