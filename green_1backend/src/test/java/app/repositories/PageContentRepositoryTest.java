package app.repositories;

import app.models.Page;
import app.models.PageContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test class for PageContentRepository
 * @author Romello ten Broeke
 */
@DataJpaTest
@Transactional
public class PageContentRepositoryTest {

    @Autowired
    private PageContentRepository pageContentRepository;

    @Autowired
    private PageRepository pageRepository;

    @BeforeEach
    public void setup() {
        Page page1 = new Page(1L, "Title1");
        Page page2 = new Page(2L, "Title2");
        pageRepository.save(page1);
        pageRepository.save(page2);
        PageContent pageContent1 = new PageContent(1, "Title1", "Dutch1", "English1", page1, "Concept1");
        PageContent pageContent2 = new PageContent(2, "Title2", "Dutch2", "English2", page2, "Concept2");
        pageContentRepository.save(pageContent1);
        pageContentRepository.save(pageContent2);
    }
    @Test
    public void repoFindAllReturnsAll() {
        assertEquals(2, pageContentRepository.findAll().size(), "Size should be 2");
    }

    @Test
    public void repoCantFindByIdReturnsNull() {
        assertNull(pageContentRepository.findById(3).orElse(null), "Should be null");
    }


    @Test
    public void repoFindAllByFkPageIdReturnsEmpty() {
        List<PageContent> result = pageContentRepository.findAllByFkPage_PageId(7L);
        assertEquals(0, result.size(), "Size should be 0");
    }
}
