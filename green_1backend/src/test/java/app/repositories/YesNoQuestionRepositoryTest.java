package app.repositories;

import app.models.DataLoader;
import app.models.YesNoQuestion;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the YesNoQuestionRepository.
 *
 * @Author Justin Chan
 */
@Import(DataLoader.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
        classes = {Repository.class, Entity.class}))
public class YesNoQuestionRepositoryTest {
    @Autowired
    private YesNoQuestionRepository yesNoQuestionRepository;

    @BeforeAll
    public static void classLevelSetup() {
        System.out.println("initialise static values and expensive data structures");
    }

    @Autowired
    CommandLineRunner dataLoader;

    private List<YesNoQuestion> yesNoQuestions;

    @BeforeEach
    void setup() throws Exception {
        this.dataLoader.run(null);
        this.yesNoQuestions = this.yesNoQuestionRepository.findAll();
    }

    @Test
    public void repoFindAllReturnsAll() {
        assertTrue(this.yesNoQuestions.size() > 0);
    }

    @Test
    public void repoFindByIdReturnsProperYesNoQuestion() {
        for (int i = 0; i < this.yesNoQuestions.size(); i++) {
            YesNoQuestion yesNoQuestion = this.yesNoQuestions.get(i);
            assertNotNull(yesNoQuestion, "The YesNoQuestion " + yesNoQuestion + " should exist");
        }
        assertTrue(this.yesNoQuestions.get(0).getQuestion().equals("question 1"), "The question should be question 1");

        YesNoQuestion yesNoQuestion = this.yesNoQuestionRepository.findById((long) 999999999).orElse(null);
        assertNull(yesNoQuestion, "The YesNoQuestion should not exist");
    }

    @Test
    public void repoSaveAddsOrUpdatesProperYesNoQuestion() {
        // Saving a new YesNoQuestion
        YesNoQuestion yesNoQuestion = new YesNoQuestion((long) 0, "question 3", "imgPath", null, 1, 3);
        YesNoQuestion savedYesNoQuestion = this.yesNoQuestionRepository.save(yesNoQuestion);
        assertTrue(savedYesNoQuestion.getId() > 0, "YesNoQuestion ID should be greater than 0");

        // Updating an existing YesNoQuestion
        YesNoQuestion existingYesNoQuestion = this.yesNoQuestionRepository.findById((long) 1).orElse(null);
        assertNotNull(existingYesNoQuestion, "The YesNoQuestion should exist");
        existingYesNoQuestion.setQuestion("question 4");
        YesNoQuestion updatedYesNoQuestion = this.yesNoQuestionRepository.save(existingYesNoQuestion);
        assertTrue(updatedYesNoQuestion.getQuestion().equals("question 4"), "The question should be updated");
    }

    @Test
    public void repoDeleteYesNoQuestion() {
        YesNoQuestion yesNoQuestion = this.yesNoQuestionRepository.findById((long) 1).orElse(null);
        assertNotNull(yesNoQuestion, "The YesNoQuestion should exist");
        this.yesNoQuestionRepository.deleteById((long) 1);
        YesNoQuestion deletedYesNoQuestion = this.yesNoQuestionRepository.findById((long) 1).orElse(null);
        assertNull(deletedYesNoQuestion, "The YesNoQuestion should not exist anymore");
    }
}
