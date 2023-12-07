package app.rest;

import app.exceptions.ResourceNotFoundException;
import app.models.Image;
import app.repositories.ImageRepository;
import app.repositories.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RestController for finding images and updating image per page
 * @author Jiaming Yan
 */
@RestController
@RequestMapping("page/image")
public class ImageController {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PageRepository pageRepository;

    @GetMapping(path = "")
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = this.imageRepository.findAll();
        return ResponseEntity.ok(images);
    }

    @GetMapping(path = "/{pageId}/all")
    public ResponseEntity<Image> getImageByPageId(@PathVariable(value = "pageId") Long pageId) throws ResourceNotFoundException {
        if (!pageRepository.existsById(Math.toIntExact(pageId))) {
            throw new ResourceNotFoundException("Not found Page with id = " + pageId);
        }
        Image pageImage = imageRepository.findByFkPageImage_PageId(pageId);
        return ResponseEntity.ok(pageImage);
    }
}
