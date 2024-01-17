package app.rest;

import app.models.Page;
import app.repositories.PageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for PageController
 * @author Romello ten Broeke
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PageControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    PageRepository pageRepository;

    private List<Page> pages;

    @BeforeEach
    void setup() {
        pageRepository.deleteAll();
        pages = Arrays.asList(
                new Page(1L, "Title1"),
                new Page(2L, "Title2"));

        pageRepository.saveAll(pages);
    }

    @Test
    // FIRST
    public void allPagesCanBeRetrieved() {
        // act
        ResponseEntity<Page[]> response =
                this.restTemplate.getForEntity("/page/all", Page[].class);
        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be 200 OK");
        // assert
        assertEquals(pages.size(), Objects.requireNonNull(response.getBody()).length, "Size should be" + pages.size());
    }

    @Test
    // FIRST
    public void pageCanBeAdded() {
        // arrange
        Page page = new Page(3L, "Title3");
        // act
        ResponseEntity<String> response =
                this.restTemplate.postForEntity("/page/add?pageTitle=" + page.getPageTitle(), null, String.class);
        // assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Status code should be 201 CREATED");
        // assert
        assertEquals("Page Created: " + page.getPageTitle(), response.getBody(), "Body should be Page Created: " + page.getPageTitle());
    }
}
