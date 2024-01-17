package app.repositories;

import app.models.User;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for UsersRepository
 * FAST Principles Applied:
 * - Fast: Tests are designed for quick execution to provide immediate feedback.
 * - Autonomous: Uses in-memory database to ensure tests are self-contained.
 * - Simple: Each test case is focused on a single functionality.
 * - Trustworthy: Reflects real-world scenarios to ensure reliability.
 *
 * @author Jiaming Yan
 */
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
        classes = { Repository.class, Entity.class }))
public class UsersRepositoryTest {
    @Autowired
    private UsersRepositoryMock usersRepo;
    private List<User> users;

    @BeforeEach
    public void setup() {
        // Arrange: Setting up the test environment by fetching all users (AAA: Arrange)
        this.users = this.usersRepo.findAll();
    }

    @Test
    public void repoFindAllReturnsAll() {
        // Assert: Verifying that all users are returned (Right-BICEP: Right Things)
        assertTrue(this.users.size() > 0);
    }

    @Test
    public void repoFindByIdReturnsProperUser() {
        // Act & Assert: Testing findById for each user and ensuring correct user is returned (CORRECT: Reference)
        // Also testing edge case with non-existent user ID (CORRECT: Existence)
        for (int i = 0; i < this.users.size(); i++) {
            User userToFind = this.usersRepo.findById(this.users.get(i).getId());
            assertNotNull(userToFind);
            assertEquals(users.get(i), userToFind,
                    "Both user objects should be the same");
        }
        User user = this.usersRepo.findById(9999999);
        assertNull(user);
    }

    @Test
    public void repoSavesNewUser() {
        // Act: Saving a new user (AAA: Act)
        // Assert: Verifying new user is added (Right-BICEP: Cross-Check Results)
        User newUser = new User(0, "Jerry69", "J69erry!", false);
        User savedUser = this.usersRepo.save(newUser);
        assertTrue(savedUser.getId() > 0, "New user should have unique user id");
        assertNotNull(this.usersRepo.findById(savedUser.getId()), "New user should be saved immediately");
    }
    @Test
    public void repoUpdatesUser() {
        // Act: Updating an user (AAA: Act)
        // Assert: Verifying user is updated (Right-BICEP: Cross-Check Results)
        User toUpdate = this.usersRepo.findById(1);
        String oldUsername = toUpdate.getUsername();
        toUpdate.setUsername("Jerry6969");
        User updatedUser = this.usersRepo.save(toUpdate);
        assertNotEquals(oldUsername, updatedUser.getUsername(), "The username should be updated");
    }

    @Test
    public void repoDeleteUser() {
        // Act: Deleting a user (AAA: Act)
        // Assert: Ensuring the user is no longer present in the repository (Right-BICEP: Errors)
        User userToDelete = this.usersRepo.deleteById(1);
        assertNull(this.usersRepo.findById(1), "The deleted user should not exist anymore");
    }
}