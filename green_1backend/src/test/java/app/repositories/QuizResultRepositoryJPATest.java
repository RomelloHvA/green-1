package app.repositories;

import app.models.QuizResult;
import app.models.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@Transactional
public class QuizResultRepositoryJPATest {

    @Autowired
    private UsersRepositoryJPA usersRepositoryJPA;
    @Autowired
    private QuizResultRepositoryJPA quizResultRepository;

    @BeforeEach
    public void setup() {
        System.out.println("setup");
        User user = new User(3);
        User saveduser = usersRepositoryJPA.save(user);
        System.out.println("user saved");

        Set<Long> sdgArray1 = new HashSet<>();
        sdgArray1.add(1L);
        sdgArray1.add(2L);
        sdgArray1.add(4L);
        LocalDate dateOfQuiz1 = LocalDate.now();

        Set<Long> sdgArray2 = new HashSet<>();
        sdgArray2.add(8L);
        sdgArray2.add(6L);
        sdgArray2.add(7L);
        LocalDate dateOfQuiz2 = LocalDate.now();

        QuizResult quizResult1 = new QuizResult(sdgArray1, saveduser, dateOfQuiz1);
        QuizResult quizResult2 = new QuizResult(sdgArray2, saveduser, dateOfQuiz2);
        quizResultRepository.save(quizResult1);
        quizResultRepository.save(quizResult2);


    }

    /**
     * Test for returning the quizResults
     * @author Santosh Kakkar
     */
    @Test
    public void findAndReturn() {
        assertEquals(2, quizResultRepository.findAll().size(), "Size should be 2");
    }

    /**
     * Initialization test
     * @author Santosh Kakkar
     */
    @Test
    public void testQuizResultInitialization() {
        Set<Long> sdgArray = new HashSet<>();
        sdgArray.add(1L);
        User user = new User();
        LocalDate dateOfQuiz = LocalDate.now();

        QuizResult quizResult = new QuizResult(sdgArray, user, dateOfQuiz);

        assertNotNull(quizResult);
        assertEquals(sdgArray, quizResult.getSdgArray());
        assertEquals(user, quizResult.getUser());
        assertEquals(dateOfQuiz, quizResult.getDateOfQuiz());
    }

    /**
     * Initialization test with default values
     * @author Santosh Kakkar
     */
    @Test
    public void testQuizResultInitializationWithDefaultValues() {
        QuizResult quizResult = new QuizResult();

        assertNotNull(quizResult);
        assertNull(quizResult.getSdgArray());
        assertNull(quizResult.getUser());
        assertNull(quizResult.getDateOfQuiz());
    }

}

