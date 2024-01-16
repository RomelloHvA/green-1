package app.rest;

import app.models.QuizResult;
import app.models.User;
import app.repositories.QuizResultRepositoryJPA;
import app.repositories.UsersRepositoryJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuizResultControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private QuizResultRepositoryJPA quizResultRepository;

    @Autowired
    private UsersRepositoryJPA usersRepository;

    private List<QuizResult> quizResults;
    private User user;
    private User savedUser;

    @BeforeEach
    void setup() {
        user = new User(1);
        savedUser = usersRepository.save(user);

        Set<Long> sdgArray1 = new HashSet<>();
        sdgArray1.add(1L);
        sdgArray1.add(2L);
        sdgArray1.add(4L);

        Set<Long> sdgArray2 = new HashSet<>();
        sdgArray2.add(8L);
        sdgArray2.add(6L);
        sdgArray2.add(7L);

        quizResults = List.of(
                new QuizResult(sdgArray1, savedUser, LocalDate.now()),
                new QuizResult(sdgArray2, savedUser, LocalDate.now())
        );

        quizResultRepository.saveAll(quizResults);
    }

    /**
     * Test for retrieving of quiz results
     * @author Santosh Kakkar
     */

    @Test
    public void allQuizResultsCanBeRetrieved() {
        ResponseEntity<QuizResult[]> response =
                this.restTemplate.getForEntity("/quizresult/all", QuizResult[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be 200 OK");
        assertEquals(quizResults.size(), Objects.requireNonNull(response.getBody()).length, "Size should be " + quizResults.size());
    }


    @Test
    public void quizResultCanBeAdded() {
        Set<Long> sdgArray = new HashSet<>();
        sdgArray.add(1L);
        sdgArray.add(2L);
        sdgArray.add(4L);


        QuizResult quizResult = new QuizResult(sdgArray, savedUser, LocalDate.now());

        ResponseEntity<String> response =
                this.restTemplate.postForEntity("/quizresult/save/" + savedUser.getUser_id(), quizResult, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be 200 OK");
        assertEquals("QuizResult Saved", response.getBody(), "Body should be QuizResult Saved");
    }
}
