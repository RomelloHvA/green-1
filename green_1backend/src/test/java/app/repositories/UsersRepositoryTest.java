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
        this.users = this.usersRepo.findAll();
    }

    @Test
    public void repoFindAllReturnsAll() {
        assertTrue(this.users.size() > 0);
    }
    @Test
    public void repoFindByIdReturnsProperUser() {
        for (int i = 0; i < this.users.size(); i++) {
            User userToFind = this.usersRepo.findById(this.users.get(i).getId());
            assert userToFind != null;
            assertEquals(users.get(i), userToFind,
                    "Both user objects should be the same");
        }
        User user = this.usersRepo.findById(9999999);
        assertNull(user);
    }
    @Test
    public void repoSaveAddsOrUpdatesUser() {
        // Saving a new user
        User newUser = new User(0, "Jerry69", "J69erry!", false);
        User savedUser = this.usersRepo.save(newUser);

        assertTrue(savedUser.getId() > 0, "New user should have unique user id");
        assertNotNull(this.usersRepo.findById(savedUser.getId()), "New user should be saved immediately");

        // Updating an existing user
        String oldUsername = savedUser.getUsername();
        savedUser.setUsername("Jerry6969");
        User updatedUser = this.usersRepo.save(savedUser);
        assertNotEquals(oldUsername, updatedUser.getUsername(), "The username should be updated");
    }
    @Test
    public void repoDeleteUser() {
        User userToDelete = this.usersRepo.deleteById(1);
        assertNull(this.usersRepo.findById(1), "The deleted user should not exist anymore");
    }
}
