package app.rest;

import app.exceptions.ResourceNotFoundException;
import app.models.Image;
import app.models.Page;
import app.repositories.ImageRepository;
import app.repositories.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    /**
     * A method to retrieve all image objects
     * @return all image objects in an array
     */
    @GetMapping(path = "")
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = this.imageRepository.findAll();
        return ResponseEntity.ok(images);
    }

    /**
     * A method for getting an image from a certain page
     * @param pageId = id of the page
     * @return the Image that is found with the pageId
     * @throws ResourceNotFoundException When there is no image found
     */
    @GetMapping(path = "/{pageId}/all")
    public ResponseEntity<Image> getImageByPageId(@PathVariable(value = "pageId") Long pageId) throws ResourceNotFoundException {
        if (!pageRepository.existsById(Math.toIntExact(pageId))) {
            throw new ResourceNotFoundException("Not found Page with id = " + pageId);
        }
        Image pageImage = imageRepository.findByFkPageImage_PageId(pageId);
        return ResponseEntity.ok(pageImage);
    }

    /**
     * A method for updating an image in the backend
     * @param pageId = id of the page
     * @param body = body of the request
     * @return updated image
     */
    @PostMapping("/{pageId}/update")
    public ResponseEntity<Image> updateImage(
            @PathVariable(value = "pageId") Long pageId,
            @RequestBody Map<String, String> body
            ) {
        String fileName = body.get("fileName");
        Image image = null;
        try {
            Image existingImage = this.imageRepository.findByFkPageImage_PageId(pageId);
            if (existingImage != null) {
                existingImage.setFkPageImage(null);
                this.imageRepository.save(existingImage);
            }

            image = this.imageRepository.findByFileName(fileName);

            if (image != null) {
                 Page page = this.pageRepository.findPageByPageIdIs(pageId);
                image.setFkPageImage(page);
                this.imageRepository.save(image);
                return ResponseEntity.ok(image);
            } else {
                throw new ResourceNotFoundException("No image found with pageId = " + pageId);
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(image);
        }
    }
}
