package app.repositories;

import app.models.DataLoader;
import app.models.MultipleChoiceOption;
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

/**
 * This class tests the MultipleChoiceOptionRepository.
 * @Author: Marco de Boer
 */

@Import(DataLoader.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
        classes = { Repository.class, Entity.class }))
public class MultipleChoiceOptionRepositoryTest {

    @Qualifier("multiplechoiceoption.JPA")
    @Autowired
    private MultipleChoiceOptionRepository multipleChoiceOptionRepo;

    @BeforeAll
    public static void classLevelSetup() {
        System.out.println("initialise static values and expensive data structures");
    }

    @Autowired
    CommandLineRunner dataLoader;

    private List<MultipleChoiceOption> someMultipleChoiceOptions;

    @BeforeEach
    public void setup() throws Exception {
        // use the available data loader to initialize the repository
        this.dataLoader.run(null);
        this.someMultipleChoiceOptions = this.multipleChoiceOptionRepo.findAll();
    }

    @Test
    public void repoFindAllReturnsAll() {
        assertTrue(this.someMultipleChoiceOptions.size() > 0);
    }

    @Test
    public void repoFindByIdReturnsProperMultipleChoiceOption() {
        for (int i = 0; i < this.someMultipleChoiceOptions.size(); i++) {
            MultipleChoiceOption multipleChoiceOption = multipleChoiceOptionRepo.findById(this.someMultipleChoiceOptions.get(i).getId()).orElse(null);
            assert multipleChoiceOption != null;
            assertEquals(this.someMultipleChoiceOptions.get(i).getOption(), multipleChoiceOption.getOption(),
                    "MultipleChoiceOption option should be the same");
            assertEquals(this.someMultipleChoiceOptions.get(i).getSDG(), multipleChoiceOption.getSDG(),
                    "MultipleChoiceOption SDG should be the same");
        }

        MultipleChoiceOption multipleChoiceOption = multipleChoiceOptionRepo.findById((long) 9999999).orElse(null);
        assertNull(multipleChoiceOption);
    }

    @Test
    public void repoSaveAddsOrUpdatesAMultipleChoiceOption() {
        MultipleChoiceOption multipleChoiceOption = new MultipleChoiceOption((long) 0, "option 2");
        MultipleChoiceOption savedMultipleChoiceOption = multipleChoiceOptionRepo.save(multipleChoiceOption);

        assertTrue(savedMultipleChoiceOption.getId() > 0, "MultipleChoiceOption ID should be generated and > 0");
        assertEquals(multipleChoiceOption.getOption(), savedMultipleChoiceOption.getOption(),
                "MultipleChoiceOption option should be the same");
        assertEquals(multipleChoiceOption.getSDG(), savedMultipleChoiceOption.getSDG(),
                "MultipleChoiceOption SDG should be the same");

        // update the multipleChoiceOption
        savedMultipleChoiceOption.setOption("option 3");
        savedMultipleChoiceOption.setSDG(2);
        MultipleChoiceOption updatedMultipleChoiceOption = multipleChoiceOptionRepo.save(savedMultipleChoiceOption);

        assertEquals(savedMultipleChoiceOption.getId(), updatedMultipleChoiceOption.getId(),
                "MultipleChoiceOption ID should be the same");
        assertEquals(savedMultipleChoiceOption.getOption(), updatedMultipleChoiceOption.getOption(),
                "MultipleChoiceOption option should be the same");
        assertEquals(savedMultipleChoiceOption.getSDG(), updatedMultipleChoiceOption.getSDG(),
                "MultipleChoiceOption SDG should be the same");

        // find updated multipleChoiceOption
        MultipleChoiceOption newMultipleChoiceOption = multipleChoiceOptionRepo.findById(updatedMultipleChoiceOption.getId()).orElse(null);
        assertEquals(newMultipleChoiceOption.getId(), updatedMultipleChoiceOption.getId(),
                "MultipleChoiceOption ID should be the same");
        assertEquals(newMultipleChoiceOption.getOption(), updatedMultipleChoiceOption.getOption(),
                "MultipleChoiceOption option should be the same");
        assertEquals(newMultipleChoiceOption.getSDG(), updatedMultipleChoiceOption.getSDG(),
                "MultipleChoiceOption SDG should be the same");
    }
}
