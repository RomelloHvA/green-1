package app.rest;

import app.models.ActionPlan;
import app.models.User;
import app.repositories.ActionPlanRepository;
import app.repositories.UsersRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UsersRepositoryJPA usersRepository;

    @Autowired
    ActionPlanRepository actionPlanRepository;

    @GetMapping(path = "/all", produces = "application/json")
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    /**
     * this api is for getting the given ids for users
     *
     * @param id the id given in the url
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        Optional<User> user = usersRepository.findById(id);
        if (user.isPresent()) {
            User foundUser = user.get();
            return ResponseEntity.ok(foundUser);
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
    }

    @GetMapping("/{id}/actionplans")
    public ResponseEntity<List<ActionPlan>> getAllUsersWithActionPlans(@PathVariable long id) {
        Optional<User> user = usersRepository.findById(id);
        if (user != null) {
            return ResponseEntity.ok(user.get().getActionplans());
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
    }

    @DeleteMapping("/{id}/actionplans/{actionPlanId}")
    public ResponseEntity<Object> deleteActionPlanFromUser(@PathVariable long id, @PathVariable long actionPlanId) {
        Optional<User> user = usersRepository.findById(id);
        if (user != null) {
            if (user.get().getActionplans().contains(this.actionPlanRepository.findById(actionPlanId).orElse(null))) {
                user.get().getActionplans().remove(this.actionPlanRepository.findById(actionPlanId).orElse(null));
                this.usersRepository.save(user.get());
                return ResponseEntity.ok(user.get().getActionplans());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Action plan with id: " + actionPlanId + " not found.");
            }
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
    }

    /**
     * Delete an user with the specified ID.
     *
     * @param id id of the user to be deleted.
     * @return A ResponseEntity containing the deleted User if the deletion is successful.
     * @throws ResourceNotFoundException if the user with the specified ID is not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        User user = usersRepository.findById(id).orElse(null);
        if (user != null) {
            usersRepository.delete(user);
            return ResponseEntity.ok(user);
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
    }

    /**
     * Update an user with the specified ID by replacing it with the provided user object.
     *
     * @param id   The id of the user that is going to be updated.
     * @param user the user with updated information.
     * @return A ResponseEntity containing the updated user if the update is successful.
     * @throws PreConditionFailedException if the provided ID in the path does not match the ID in the request body.
     * @throws ResourceNotFoundException   if the offer with the specified ID is not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user) {
        if (id != user.getUser_id()) {
            throw new PreConditionFailedException("ID in the path does not match ID in the request body");
        }
        if (usersRepository.findById(id) != null) {
            User existingUser = usersRepository.findById(id).orElse(null);
            if (existingUser == null) {
                throw new ResourceNotFoundException("Cannot find a user with id=" + id);
            } else {
                existingUser.setUsername(user.getUsername());
                existingUser.setEmail(user.getEmail());
                existingUser.setIsAdmin(user.getIsAdmin());
                user = usersRepository.save(existingUser);
                return ResponseEntity.ok(user);
            }
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable long id, @RequestBody Map<String, String> user) {
        try {
            // Try to find a user with the given id
            Optional<User> existingUser = this.usersRepository.findById(id);
            if (existingUser.isPresent()) {
                // Validating if certain attributes are present
                User userToUpdate = existingUser.get();
                if (user.containsKey("first_name")) {
                    userToUpdate.setFirst_name(user.get("first_name"));
                }
                if (user.containsKey("last_name")) {
                    userToUpdate.setLast_name(user.get("last_name"));
                }
                if (user.containsKey("occupation")) {
                    userToUpdate.setOccupation(user.get("occupation"));
                }
                if (user.containsKey("username")) {
                    userToUpdate.setUsername(user.get("username"));
                }
                if (user.containsKey("date_of_birth") && user.get("date_of_birth") != null) {
                    userToUpdate.setDate_of_birth(LocalDate.parse(user.get("date_of_birth")));
                }
                if (user.containsKey("bio")) {
                    userToUpdate.setBio(user.get("bio"));
                }
                if (user.containsKey("img_path")) {
                    userToUpdate.setImg_path(user.get("img_path"));
                }
                // Saving the user
                return ResponseEntity.ok(this.usersRepository.save(userToUpdate));
            } else {
                throw new ResourceNotFoundException("No user is found!");
            }
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/name", produces = "application/json")
    public ResponseEntity<?> findByUsername(@RequestBody String username) {
        if (username.trim().isEmpty()) throw new ResourceNotFoundException("Please provide an username!");
        try {
            User user = this.usersRepository.findByUsername(username);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                throw new ResourceNotFoundException("No user found with username: " + username);
            }
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }
}
