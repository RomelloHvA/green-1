package app.rest;

import app.models.Goal;
import app.models.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;

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

    /**
     * Testing if a goal can be saved
     * @author Santosh Kakkar
     */
    @Test
    public void newGoalCanBeSaved() {
        Goal newGoal = new Goal(4, 2, "Life under water");
        ResponseEntity<Goal> response =
                this.restTemplate.postForEntity("/goals", newGoal, Goal.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be 200 OK");
        Goal savedGoal = response.getBody();
        assertNotNull(savedGoal, "Saved goal is null");
        ResponseEntity<Goal> responseGetOne =
                this.restTemplate.getForEntity("/goals/4", Goal.class);
        Goal goalSaved = responseGetOne.getBody();
        assertNotNull(goalSaved, "The goal is not correctly saved");
    }

     /** Testing if a single Goal can be retrieved by ID
     * @author Santosh Kakkar
     */
    @Test
    public void singleGoalCanBeRetrievedById() {
        ResponseEntity<Goal> response =
                this.restTemplate.getForEntity("/goals/3", Goal.class);

        // check status code, location header and response body of post request
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be 200 OK");

        Goal goal = response.getBody();
        assertNotNull(goal);
        assertEquals(3, goal.getId());
    }

    /**
     * Testing if a single Goal can be deleted by ID
     * @author Santosh Kakker
     */
    @Test
    public void singleGoalCanBeDeletedById() {
        ResponseEntity<Goal> response =
                this.restTemplate.exchange("/goals/2", HttpMethod.DELETE, null, Goal.class);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be 200 OK");

        Goal deletedGoal = response.getBody();
        assertNotNull(deletedGoal);
        assertEquals(2, deletedGoal.getId());

        // Check if the goal is deleated
        ResponseEntity<Goal> checkDeletedGoal =
                this.restTemplate.getForEntity("/goals/2", Goal.class);
        assertEquals(HttpStatus.OK, checkDeletedGoal.getStatusCode(), "Status code should be OK");
    }
}
