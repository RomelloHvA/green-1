package app.rest;

import app.models.Profile;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProfileControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private Profile profile;
    @Value("/")
    private String servletContextPath;

    @BeforeEach
    void setup() {
        if (servletContextPath == null) {
            servletContextPath = "/";
        }
        profile = new Profile(100, "Bastiaan", "Groote");
    }

    @Test
    public void profileCanBeRetrieved() {
        ResponseEntity<Profile> response = restTemplate.exchange(
                "/profiles/100",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Profile>() {}
        );

        // check status code, location header and response body of get request
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be 200 OK");
    }

}
