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

import java.util.Objects;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    UsersRepositoryJPA usersRepository;
    @Autowired
    MvcConfig mvcConfig;

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
