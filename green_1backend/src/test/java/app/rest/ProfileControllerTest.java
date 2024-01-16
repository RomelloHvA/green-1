package app.rest;

import app.models.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for ProfileController
 * @Author Justin Chan
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProfileControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private Profile profile;

    @BeforeEach
    void setup() {
//        profile = new Profile(1, "Bastiaan", "Groote");
    }

    @Test
    public void profileCanBeRetrieved() {
        // Arrange
        ResponseEntity<Profile> response =
                this.restTemplate.getForEntity(
                "/profiles/1",
                Profile.class
        );

        // check status code, location header and response body of get request
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be 200 OK");

        // Arrange
        Profile profile = response.getBody();
        // Assert
        assert profile != null;
        // Assert
        assertEquals(1, profile.getUser_id());
    }

    @Test
    public void aProfileCanBeSaved() {
        // Arrange
        ResponseEntity<Profile> response1 =
                this.restTemplate.getForEntity(
                        "/profiles/1",
                        Profile.class
                );

        // check status code, location header and response body of post request
        // Assert
        assertEquals(HttpStatus.OK, response1.getStatusCode(), "Status code should be 200 OK");

        // Arrange
        Profile profile = response1.getBody();
        assert profile != null;
        // Assert
        assertEquals(1, profile.getUser_id());

        // Act
        profile.setFirst_name("Bastiaan");
        // post the updated profile instance

        ResponseEntity<Profile> response2 =
                this.restTemplate.exchange(
                        "/profiles/1",
                        HttpMethod.PUT,
                        new HttpEntity<>(profile),
                        Profile.class
                );

        // Assert
        assertEquals(HttpStatus.OK, response2.getStatusCode(), "Status code should be 200 OK");

        // retrieve the updated profile
        Profile updatedProfile = response2.getBody();
        assert updatedProfile != null;
        // Assert
        assertEquals(1, updatedProfile.getUser_id());
        assertEquals("Bastiaan", updatedProfile.getFirst_name());
    }

}
