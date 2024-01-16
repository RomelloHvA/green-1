package app.rest;

import app.models.Goal;
import app.models.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GoalControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private List<Goal> goals;

    @BeforeEach
    void setup() {
        this.goals = List.of(
                new Goal(1, 1, "No Poverty"),
                new Goal(2, 1, "Zero Hunger"),
                new Goal(3, 1, "Good Health and Well-being")
        );
    }

    /**
     * Testing if all Goals can be retrieved
     * @author Jiaming Yan
     */
    @Test
    public void allGoalsCanBeRetrieved() {

        ResponseEntity<Goal[]> response =
                this.restTemplate.getForEntity("/goals", Goal[].class);

        // check status code, location header and response body of post request
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be 200 OK");

        Goal[] goalsList = response.getBody();
        assertThat(goalsList.length, is(greaterThan(0)));
        for (int i = 0; i < goalsList.length; i++) {
            assertTrue(goalsList[i] instanceof Goal, "This is not a Goal object!");
        }
    }
}
