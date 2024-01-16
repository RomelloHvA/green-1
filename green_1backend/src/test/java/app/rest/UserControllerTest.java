package app.rest;

import app.models.Quiz;
import app.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private User user;
    @Value("${server.servlet.context-path}")
    private String servletContextPath;

    @BeforeEach
    void setup() {
        if (servletContextPath == null) {
            servletContextPath = "/";
        }
        user = new User(0, "Alex95", "Alexisawesome95", false);
    }
    @Test
    public void allUsersCanBeRetrieved() {
        ResponseEntity<List<User>> response = restTemplate.exchange(
                "/users/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}
        );

        // check status code, location header and response body of post request
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be 200 OK");

        List<User> users = response.getBody();
        assertThat(users.size(), is(greaterThan(0)));
    }

}
