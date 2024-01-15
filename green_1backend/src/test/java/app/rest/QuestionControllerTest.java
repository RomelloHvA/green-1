package app.rest;

import app.models.Question;
import app.models.Quiz;
import app.models.YesNoQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for QuestionController
 * @Author Marco de Boer
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuestionControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Question question;

    @Value("${server.servlet.context-path}")
    private String servletContextPath;

    private Quiz quiz = new Quiz((long) 1, "The new quiz", false, true, false, null);


    @BeforeEach
    void setup() {
        if (servletContextPath == null) {
            servletContextPath = "/";
        }

        question = new YesNoQuestion((long)1, "The new question", "imgpath", quiz, 1, 1 );
    }

    @Test
    public void allQuestionsCanBeRetrieved() {
        ResponseEntity<Question[]> response =
                this.restTemplate.getForEntity("/question", Question[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "Status code should be 200 OK");

        Question[] questions = response.getBody();
        assertThat(questions.length, is(greaterThan(0)));
    }

    @Test
    public void aNewYesNoQuestionCanBePosted() {
        // post a new question instance at REST end-point
        YesNoQuestion question = new YesNoQuestion((long)0, "The new question", "imgpath", new Quiz(), 1, 1 );

        ResponseEntity<Question> response =
                this.restTemplate.postForEntity("/question/yesno/" + 1, question, Question.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode(),
                "Status code should be 201 CREATED");
    }

    @Test
    public void getAQuestionById() {
        ResponseEntity<Question> response1 =
                this.restTemplate.postForEntity("/question/yesno/" + 1, question, Question.class);

        Question postedQuestion = response1.getBody();

        ResponseEntity<Question> response =
                this.restTemplate.getForEntity("/question/" + postedQuestion.getId(), Question.class);

        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "Status code should be 200 OK");

        Question retrievedQuestion = response.getBody();
        assert retrievedQuestion != null;
        compareQuestion(question, retrievedQuestion);
    }

    @Test
    public void getAQuestionByWrongId() {
        ResponseEntity<Question> response =
                this.restTemplate.getForEntity("/question/" + 0, Question.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                "Status code should be 404 NOT FOUND");
    }

    @Test
    public void deleteAQuestionById() {
        ResponseEntity<Question> response1 =
                this.restTemplate.postForEntity("/question/yesno/" + 1, question, Question.class);

        Question postedQuestion = response1.getBody();
        assert postedQuestion != null;

        String url = "/question/" + postedQuestion.getId();
        ResponseEntity<Void> response = this.restTemplate.exchange(
                url, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode(),
                "Status code should be 204 NO CONTENT");
    }

    @Test
    public void deleteAQuestionByWrongId() {
        String url = "/question/" + 9999;
        ResponseEntity<Void> response = this.restTemplate.exchange(
                url, HttpMethod.DELETE, null, Void.class);

        // check status code
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                "Status code should be 404 NOT FOUND");
    }

    private void compareQuestion(Question question1, Question question2) {
        assertEquals(question1.getQuestion(), question2.getQuestion(),
                "question name should be the same");
        assertEquals(question1.getImgPath(), question2.getImgPath(),
                "question imgPath should be the same");
        assertEquals(question1.getIndex(), question2.getIndex(),
                "question index should be the same");
    }
}
