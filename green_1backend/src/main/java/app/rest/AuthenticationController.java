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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    UsersRepositoryJPA usersRepository;
    @Autowired
    MvcConfig mvcConfig;

    @PostMapping(path = "/signup", produces = "application/json")
    public ResponseEntity<?> signUp(@RequestBody Map<String, String> userData) {
        // Start by getting all the required data from the body
        int sectorId = Integer.parseInt(userData.get("sector_id"));
        String firstName = userData.get("first_name");
        String lastName = userData.get("last_name");
        String email = userData.get("email");
        int securityClearance = Integer.parseInt(userData.get("security_clearance"));
        String userName = userData.get("username");
        String passWord = userData.get("password");
        String postalCode = userData.get("postal_code");
        LocalDate dateOfBirth = null;
        if (userData.containsKey("date_of_birth") && !(userData.get("date_of_birth") == null)) {
            // Sometimes the user will not provide their date of birth because it is not mandatory
            dateOfBirth = LocalDate.parse(userData.get("date_of_birth"));
        }

        // Creating a new User Object with the given data
        User user = new User(
                sectorId,
                firstName,
                lastName,
                email,
                securityClearance,
                passWord,
                userName,
                null,
                null,
                dateOfBirth,
                postalCode,
                null,
                false
        );
        // Attributes that are set to null will be instantiated by the user via their profile page
        System.out.println(user);
        try {
            User existingUser = this.usersRepository.findByUsername(userName);
            if (existingUser != null) {
                throw new PreConditionFailedException("Sign up failed, there is already an existing user with this username!");
            }
            // Saving the user to the repository
            this.usersRepository.save(user);
            User loggedInUser = this.usersRepository.findByUsername(userName);
            return ResponseEntity.status(HttpStatus.CREATED).body(loggedInUser);
        } catch (PreConditionFailedException exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @JsonView({ViewClasses.Summary.class})
    @PostMapping(path = "/login", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody ObjectNode userData) {
        try {
            String userName = userData.get("username").asText();
            String passWord = userData.get("password").asText();

            User user = this.usersRepository.findByUsername(userName);

            if (user == null) {
                throw new NullPointerException("This user does not exist!" + userName);
            } else if (userName.trim().isEmpty()) {
                throw new IllegalArgumentException("The username must not be empty!");
            } else if (!Objects.equals(userName, user.getUsername())) {
                throw new IllegalArgumentException("The username is incorrect!");
            } else if (Objects.equals(passWord, user.getPassword())) {
                JWToken jwToken = new JWToken(user.getUsername(), user.getUser_id(), user.getIsAdmin());
                String tokenString = jwToken.encode(this.mvcConfig.getIssuer(),
                        this.mvcConfig.getPassphrase(),
                        this.mvcConfig.getTokenDurationOfValidity());
//                return ResponseEntity.ok("Login successful!");
                return ResponseEntity.accepted()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                        .body(user);
            } else if (passWord.trim().isEmpty()) {
                throw new IllegalArgumentException("The password must not be empty!");
            } else {
                throw new IllegalArgumentException("The password is incorrect");
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(illegalArgumentException.getMessage());
        } catch (NullPointerException nullPointerException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This user does not exist!");
        }
    }
}
