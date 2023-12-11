package app.rest;

import app.exceptions.ResourceNotFoundException;
import app.models.PageContent;
import app.repositories.PageContentRepository;
import app.repositories.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * RestController for finding content and updating content per page
 * @author Romello ten Broeke
 */

@RestController
@RequestMapping("page/content")
public class PageContentController {

    @Autowired
    private PageContentRepository pageContentRepository;
    @Autowired
    private PageRepository pageRepository;


// Gets all the content for one page per the Page ID
    @GetMapping(path = "/{pageId}/all")
    public ResponseEntity<List<PageContent>> getAllContentByPageId(@PathVariable(value = "pageId") Long pageId) throws ResourceNotFoundException {
        if (!pageRepository.existsById(Math.toIntExact(pageId))) {
            throw new ResourceNotFoundException("Not found Page with id = " + pageId);
        }
        List<PageContent> pageContents = pageContentRepository.findAllByFkPage_PageId(pageId);
        return new ResponseEntity<>(pageContents, HttpStatus.OK);
    }

    /**
     *
     * @param contentId id of the content to be changed
     * @param contentConcept is the parameter that says if only the concept needs to be changed.
     * @param pageContent is all the content that needs to be saved.
     * @return if the data is saved succesfully or not.
     * @author Romello ten Broeke
     */
    @PostMapping("/{contentId}/{isConcept}")
    public ResponseEntity<PageContent> updateContent(
            @PathVariable(value = "contentId") Long contentId,
            @PathVariable(value = "isConcept", required = false) String contentConcept,
            @RequestBody PageContent pageContent
    ) throws ResourceNotFoundException {
        PageContent existingPageContent = pageContentRepository.findById(Math.toIntExact(contentId)).orElse(null);

        if (existingPageContent != null) {
            // Check if contentConcept parameter is provided in the URL
            if (Objects.equals(contentConcept, "true")) {
                existingPageContent.setContentConcept(pageContent.getContentConcept());
            } else if (Objects.equals(contentConcept, "false")) {
                // Update other fields based on the request body
                existingPageContent.setContentTitle(pageContent.getContentTitle());
                existingPageContent.setContentDutch(pageContent.getContentDutch());
                existingPageContent.setContentEnglish(pageContent.getContentEnglish());
                existingPageContent.setContentConcept(pageContent.getContentConcept());
            }

            PageContent updatedPageContent = pageContentRepository.save(existingPageContent);
            return new ResponseEntity<>(updatedPageContent, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Not found content with id = " + contentId);
        }
    }


}
