package app.repositories;

import app.models.DataLoader;
import app.models.Quiz;
import app.models.Sector;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class tests the QuizRepository.
 * @author Marco de Boer
 */

@Import(DataLoader.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
        classes = { Repository.class, Entity.class }))
public class QuizRepositoryTest {
    @Qualifier("QUIZ.JPA")
    @Autowired
    private QuizRepository quizRepository;

    @BeforeAll
    public static void classLevelSetup() {
        System.out.println("initialise static values and expensive data structures");
    }

    @Autowired
    CommandLineRunner dataLoader;

    private List<Quiz> someQuizzes;

    @BeforeEach
    public void setup() throws Exception {
        // use the available data loader to initialize the repository
        this.dataLoader.run(null);
        this.someQuizzes = this.quizRepository.findAll();
    }

    @Test
    public void repoFindAllReturnsAll() {
        assertTrue(this.someQuizzes.size() > 0);
    }

    @Test
    public void repoFindByIDReturnsProperQuiz(){
        for(int i = 0; i < this.someQuizzes.size(); i++){
            Quiz quiz = quizRepository.findById(this.someQuizzes.get(i).getId()).orElse(null);
            assertEquals(this.someQuizzes.get(i).getName(), quiz.getName(),
                    "The name of the quiz is not the same as the name of the quiz in the repository");
            assertEquals(this.someQuizzes.get(i).getSector(), quiz.getSector(),
                    "The sector of the quiz is not the same as the sector of the quiz in the repository");
            assertEquals(this.someQuizzes.get(i).getIsLive(), quiz.getIsLive(),
                    "The isLive of the quiz is not the same as the isLive of the quiz in the repository");
            assertEquals(this.someQuizzes.get(i).getIsPublished(), quiz.getIsPublished(),
                    "The isPublished of the quiz is not the same as the isPublished of the quiz in the repository");
        }
    }

    @Test
    public void repoFindByWrongIDReturnsNull(){
        Quiz quiz = quizRepository.findById((long) 0).orElse(null);
        assertEquals(null, quiz,
                "The quiz should be null because the ID does not exist in the repository");
    }

    @Test
    public void repoSaveAddsOrUpdatesAQuiz(){
        Quiz quiz = new Quiz((long) 0, "quiz 3", false, true, false, null);
        Quiz savedQuiz = quizRepository.save(quiz);
        Quiz quiz2 = quizRepository.findById(savedQuiz.getId()).orElse(null);
        assertTrue(savedQuiz.getId() > 0, "The ID of the quiz should be generated and > 0");
        assertEquals(quiz.getName(), quiz2.getName(),
                "The quiz should be the same as the quiz in the repository");
        assertEquals(quiz.getIsLive(), quiz2.getIsLive(),
                "The isLive of the quiz should be the same as the isLive of the quiz in the repository");
        assertEquals(quiz.getIsPublished(), quiz2.getIsPublished(),
                "The isPublished of the quiz should be the same as the isPublished of the quiz in the repository");
        assertEquals(quiz.getSector(), quiz2.getSector(),
                "The sector of the quiz should be the same as the sector of the quiz in the repository");
    }

    @Autowired
    private SectorRepository sectorRepository;

    @Test
    public void repoFindAllQuizzesBySector(){
        Sector sector = sectorRepository.findById((long) 1).orElse(null);
        List<Quiz> quizzes = quizRepository.findAllBySector(sector);
        assertEquals(2, quizzes.size(),
                "The number of quizzes in the sector should be 2");
    }

    @Test
    public void repoFindAllQuizzesByWrongSector(){
        Sector sector = sectorRepository.findById((long) 0).orElse(null);
        List<Quiz> quizzes = quizRepository.findAllBySector(sector);
        assertEquals(0, quizzes.size(),
                "The number of quizzes in the sector should be 0");
    }

    @Test
    public void repoFindAllQuizzesByIsLive(){
        Sector sector = sectorRepository.findById((long) 1).orElse(null);
        Quiz quiz = quizRepository.findByIsLiveAndSectorId(true, sector.getId()).orElse(null);
        assertEquals("quiz 2", quiz.getName(),
                "The name of the quiz should be quiz 2");
    }

    @Test
    public void repoFindAllQuizzesByWrongIsLive(){
        Quiz quiz = quizRepository.findByIsLiveAndSectorId(false, 0).orElse(null);
        assertEquals(null, quiz,
                "The quiz should be null because there is no quiz with isLive = false");
    }



}
