package app.repositories;

import app.models.DataLoader;
import app.models.MultipleChoiceQuestion;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the MultipleChoiceQuestionRepository.
 * @author Marco de Boer
 */

@Import(DataLoader.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
        classes = { Repository.class, Entity.class }))
public class MultipleChoiceQuestionRepositoryTest {

    @Qualifier("MULTIPLECHOICEQUESTION.JPA")
    @Autowired
    private MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;

    @BeforeAll
    public static void classLevelSetup() {
        System.out.println("initialise static values and expensive data structures");
    }

    @Autowired
    CommandLineRunner dataLoader;

    private List<MultipleChoiceQuestion> someMultipleChoiceQuestions;

    @BeforeEach
    public void setup() throws Exception {
        // use the available data loader to initialize the repository
        this.dataLoader.run(null);
        this.someMultipleChoiceQuestions = this.multipleChoiceQuestionRepository.findAll();
    }

    @Test
    public void repoFindAllReturnsAll() {
        assertTrue(this.someMultipleChoiceQuestions.size() > 0);
    }

    @Test
    public void repoFindByIdReturnsProperMultipleChoiceOption() {
        for (int i = 0; i < this.someMultipleChoiceQuestions.size(); i++) {
            MultipleChoiceQuestion multipleChoiceQuestion = multipleChoiceQuestionRepository.findById(this.someMultipleChoiceQuestions.get(i).getId()).orElse(null);
            assert multipleChoiceQuestion != null;
            compareMultipleChoiceQuestions(this.someMultipleChoiceQuestions.get(i), multipleChoiceQuestion);}

        MultipleChoiceQuestion multipleChoiceQuestion = multipleChoiceQuestionRepository.findById((long) 9999999).orElse(null);
        assertNull(multipleChoiceQuestion);
    }

    @Test
    public void repoSaveAddsOrUpdatesAMultipleChoiceOption() {
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion((long) 0, "question 3", "imgpath",
                null, 1, 1);
        MultipleChoiceQuestion savedMultipleChoiceQuestion = multipleChoiceQuestionRepository.save(multipleChoiceQuestion);

        assertTrue(savedMultipleChoiceQuestion.getId() > 0, "MultipleChoiceOption ID should be generated and > 0");
        compareMultipleChoiceQuestions(multipleChoiceQuestion, savedMultipleChoiceQuestion);

        // update the multipleChoiceOption
        savedMultipleChoiceQuestion.setQuestion("question 4");
        savedMultipleChoiceQuestion.setAnswerLimit(2);
        MultipleChoiceQuestion updatedMultipleChoiceQuestion = multipleChoiceQuestionRepository.save(savedMultipleChoiceQuestion);

        compareMultipleChoiceQuestions(savedMultipleChoiceQuestion, updatedMultipleChoiceQuestion);
        // find updated multipleChoiceOption
        MultipleChoiceQuestion newMultipleChoiceQuestion = multipleChoiceQuestionRepository.findById(updatedMultipleChoiceQuestion.getId()).orElse(null);
        compareMultipleChoiceQuestions(updatedMultipleChoiceQuestion, newMultipleChoiceQuestion);
    }

    private void compareMultipleChoiceQuestions(MultipleChoiceQuestion question1, MultipleChoiceQuestion question2){
        assertEquals(question1.getQuestion(), question2.getQuestion(),
                "MultipleChoiceQuestion question should be the same");
        assertEquals(question1.getAnswerLimit(), question2.getAnswerLimit(),
                "MultipleChoiceQuestion answerLimit should be the same");
        assertEquals(question1.getIndex(), question2.getIndex(),
                "MultipleChoiceQuestion index should be the same");
        assertEquals(question1.getOptions(), question2.getOptions(),
                "MultipleChoiceQuestion options should be the same");
    }
}
