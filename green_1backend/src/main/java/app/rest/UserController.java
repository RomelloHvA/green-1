package app.rest;

import app.MvcConfig;
import app.models.User;
import app.models.ViewClasses;
import app.repositories.UsersRepositoryJPA;
import app.security.JWToken;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UsersRepositoryJPA usersRepository;

//    @Autowired
//    MvcConfig mvcConfig;

    @GetMapping(path = "/all", produces = "application/json")
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

//    @JsonView({ViewClasses.Summary.class})
//    @PostMapping(path = "/login", produces = "application/json")
//    public ResponseEntity<?> login(@RequestBody ObjectNode userData) {
//        try {
//            String userName = userData.get("username").asText();
//            String passWord = userData.get("password").asText();
//
//            User user = this.usersRepository.findByUsername(userName);
//
//            if (user == null) {
//                throw new NullPointerException("This user does not exist!" + userName);
//            } else if (userName.trim().isEmpty()) {
//                throw new IllegalArgumentException("The username must not be empty!");
//            } else if (!Objects.equals(userName, user.getUsername())) {
//                throw new IllegalArgumentException("The username is incorrect!");
//            } else if (Objects.equals(passWord, user.getPassword())) {
//                JWToken jwToken = new JWToken(user.getUsername(), user.getUser_id(), user.getIsAdmin());
//                String tokenString = jwToken.encode(this.mvcConfig.getIssuer(),
//                        this.mvcConfig.getPassphrase(),
//                        this.mvcConfig.getTokenDurationOfValidity());
////                return ResponseEntity.ok("Login successful!");
//                return ResponseEntity.accepted()
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
//                        .body(user);
//            } else if (passWord.trim().isEmpty()) {
//                throw new IllegalArgumentException("The password must not be empty!");
//            } else {
//                throw new IllegalArgumentException("The password is incorrect");
//            }
//        } catch (IllegalArgumentException illegalArgumentException) {
//            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(illegalArgumentException.getMessage());
//        } catch (NullPointerException nullPointerException) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This user does not exist!");
//        }
//    }

    /**
     * this api is for getting the given ids for users
     *
     * @param id the id given in the url
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getOfferById(@PathVariable long id) {
        Optional<User> user = usersRepository.findById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
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
    public ResponseEntity<User> deleteOffer(@PathVariable long id) {
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
    public ResponseEntity<User> updateOffer(@PathVariable long id, @RequestBody User user) {
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
            throw new ResourceNotFoundException("Offer not found with ID: " + id);
        }
    }
}
