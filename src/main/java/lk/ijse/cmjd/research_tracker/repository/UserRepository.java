package lk.ijse.cmjd.research_tracker.repository;

import lk.ijse.cmjd.research_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity database operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Find a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Check if a user with the given username already exists.
     *
     * @param username the username to check
     * @return true if the username is taken
     */
    Boolean existsByUsername(String username);
}
