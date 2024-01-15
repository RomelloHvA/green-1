package app.rest;

import app.models.Quiz;
import app.models.Sector;
import app.repositories.SectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for QuizController
 * @Author Marco de Boer
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuizControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Quiz quiz;

    @Value("${server.servlet.context-path}")
    private String servletContextPath;

    @BeforeEach
    void setup() {
        if (servletContextPath == null) {
            servletContextPath = "/";
        }
        quiz = new Quiz((long) 1, "The new quiz", false, true, false, null);
    }

    @Test
    public void allQuizzesCanBeRetrieved() {

        ResponseEntity<Quiz[]> response =
                this.restTemplate.getForEntity("/quiz/all", Quiz[].class);

        // check status code, location header and response body of post request
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be 200 OK");

        Quiz[] quizzes = response.getBody();
        assertThat(quizzes.length, is(greaterThan(0)));
    }

    @Test
    public void aNewQuizCanBePosted() {
        // post a new quiz instance at REST end-point
        quiz.setId((long) 0);

        ResponseEntity<Quiz> response =
                this.restTemplate.postForEntity("/quiz/" + 0, quiz, Quiz.class);

        // check status code
        assertEquals(HttpStatus.CREATED, response.getStatusCode(),
                "Status code should be 201 CREATED");

        Quiz savedQuiz = response.getBody();
        assert savedQuiz != null;
        assertThat(savedQuiz.getId(), is(greaterThan(0L)));
        compareQuizzes(savedQuiz, quiz);
        // retrieve the quiz that was posted before
        ResponseEntity<Quiz> response2 =
                this.restTemplate.getForEntity("/quiz/" + savedQuiz.getId(), Quiz.class);

        // check status code and response body of get-by-id request
        assertEquals(HttpStatus.OK, response2.getStatusCode(),
                "Status code should be 200 OK");

        Quiz retrievedQuiz = response2.getBody();
        assert retrievedQuiz != null;
        compareQuizzes(retrievedQuiz, savedQuiz);
    }

    @Test
    public void getQuizByValidId() {
        String url = "/quiz/" + quiz.getId();
        ResponseEntity<Quiz> response =
                this.restTemplate.getForEntity(url, Quiz.class);

        // check status code and response body of get-by-id request
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "Status code should be 200 OK");

        Quiz retrievedQuiz = response.getBody();
        assert retrievedQuiz != null;
        assertEquals(retrievedQuiz.getName(),"quiz 1",
                "Name of retrieved quiz should be equal to name of posted quiz");
        assertEquals(retrievedQuiz.getIsPublished(),false,
                "isPublished of retrieved quiz should be equal to isPublished of posted quiz");
        assertEquals(retrievedQuiz.getIsConcept(),true,
                "isConcept of retrieved quiz should be equal to isConcept of posted quiz");
        assertEquals(retrievedQuiz.getIsLive(),false,
                "isLive of retrieved quiz should be equal to isLive of posted quiz");
    }

    @Test
    public void getQuizByInvalidId() {
        String url = "/quiz/" + 9999;
        ResponseEntity<Quiz> response =
                this.restTemplate.getForEntity(url, Quiz.class);

        // check status code and response body of get-by-id request
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                "Status code should be 404 NOT FOUND");
    }

    @Test
    public void updateQuizByValidId() {
        quiz.setId((long) 0);
        ResponseEntity<Quiz> response1 =
                this.restTemplate.postForEntity("/quiz/" + 0, quiz, Quiz.class);

        Quiz postedQuiz = response1.getBody();
        postedQuiz.setName("updated quiz");
        // post a new quiz instance at REST end-point
        String url = "/quiz/" + postedQuiz.getId();
        HttpEntity<Quiz> requestEntity = new HttpEntity<>(postedQuiz);

        ResponseEntity<Quiz> response = this.restTemplate.exchange(
                url, HttpMethod.PUT, requestEntity, Quiz.class);

        // check status code
        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "Status code should be 200 OK");

        Quiz savedQuiz = response.getBody();
        assert savedQuiz != null;
        assertThat(savedQuiz.getId(), is(greaterThan(0L)));
        compareQuizzes(savedQuiz, postedQuiz);
    }

    @Test
    public void updateQuizByInvalidId() {
        // post a new quiz instance at REST end-point
        quiz.setId((long) 99999);
        String url = "/quiz/" + quiz.getId();
        HttpEntity<Quiz> requestEntity = new HttpEntity<>(quiz);

        ResponseEntity<Quiz> response = this.restTemplate.exchange(
                url, HttpMethod.PUT, requestEntity, Quiz.class);

        // check status code
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                "Status code should be 404 NOT FOUND");
    }

    @Test
    public void deleteQuizByValidId() {
        quiz.setId((long) 0);
        ResponseEntity<Quiz> response1 =
                this.restTemplate.postForEntity("/quiz/" + 0, quiz, Quiz.class);

        // check status code
        assertEquals(HttpStatus.CREATED, response1.getStatusCode(),
                "Status code should be 201 CREATED");
        // post a new quiz instance at REST end-point
        String url = "/quiz/" + response1.getBody().getId();
        ResponseEntity<Void> response = this.restTemplate.exchange(
                url, HttpMethod.DELETE, null, Void.class);

        // check status code
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode(),
                "Status code should be 204 NO CONTENT");
    }

    @Test
    public void deleteQuizByInvalidId() {
        // post a new quiz instance at REST end-point
        quiz.setId((long) 99999);
        String url = "/quiz/" + quiz.getId();
        ResponseEntity<Void> response = this.restTemplate.exchange(
                url, HttpMethod.DELETE, null, Void.class);

        // check status code
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                "Status code should be 404 NOT FOUND");
    }

//    @Test
//    public void getLiveQuizBySector(){
//        quiz.setIsPublished(true);
//        quiz.setIsConcept(false);
//        quiz.setSector(new Sector((long) 1, "sector 1", "description 1"));
//
//        ResponseEntity<Quiz> response1 =
//                this.restTemplate.postForEntity("/quiz/" + 0, quiz, Quiz.class);
//
//        assertEquals(HttpStatus.CREATED, response1.getStatusCode(),
//                "Status code should be 201 CREATED");
//
//        URI uri = UriComponentsBuilder.fromUriString("/quiz/live")
//                .queryParam("sectorId", response1.getBody().getSector().getId())
//                .build()
//                .toUri();
//
//        ResponseEntity<Quiz> response =
//                this.restTemplate.getForEntity(uri, Quiz.class);
//
//        // check status code and response body of get-by-id request
//        assertEquals(HttpStatus.OK, response.getStatusCode(),
//                "Status code should be 200 OK");
//
//        Quiz retrievedQuiz = response.getBody();
//        assert retrievedQuiz != null;
//        assertEquals(retrievedQuiz.getIsLive(), true,
//                "isLive of retrieved quiz should be true");
//    }

    private void compareQuizzes(Quiz quiz1, Quiz quiz2) {
        assertEquals(quiz1.getName(), quiz2.getName(),
                "Name of saved quiz should be equal to name of posted quiz");
        assertEquals(quiz1.getIsPublished(), quiz2.getIsPublished(),
                "isPublished of saved quiz should be equal to isPublished of posted quiz");
        assertEquals(quiz1.getIsConcept(), quiz2.getIsConcept(),
                "isConcept of saved quiz should be equal to isConcept of posted quiz");
        assertEquals(quiz1.getIsLive(), quiz2.getIsLive(),
                "isLive of saved quiz should be equal to isLive of posted quiz");
        assertEquals(quiz1.getSector(), quiz2.getSector(),
                "Sector of saved quiz should be equal to sector of posted quiz");
    }
}
