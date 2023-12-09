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
import java.util.Objects;

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
     */
    @GetMapping(path = "/{pageId}/all")
    public ResponseEntity<Image> getImageByPageId(@PathVariable(value = "pageId") Long pageId) {
        Image image = this.imageRepository.findByFkPageImage_PageId(pageId);
        try {
            if (!pageRepository.existsById(Math.toIntExact(pageId))
                    || Objects.equals(image, null)) {
                return ResponseEntity.ok(new Image()); // Return an empty object
            } else {
                return ResponseEntity.ok(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        // Trying to remove the image of the page that is found
        if (Objects.equals(fileName, "none")) {
            Image existingImage = this.imageRepository.findByFkPageImage_PageId(pageId);
            existingImage.setFkPageImage(null);
            this.imageRepository.save(existingImage);
            return ResponseEntity.ok(existingImage);
        } else {
            Image image = this.imageRepository.findByFileName(fileName);
            try {
                // Trying to find out if there is already an existing image with the give pageId
                Image existingImage = this.imageRepository.findByFkPageImage_PageId(pageId);
                if (existingImage != null && !Objects.equals(existingImage.getImageId(), image.getImageId())) {
                    // If there is an image found with this Id, the pageId of that image will be set to null
                    existingImage.setFkPageImage(null);
                    this.imageRepository.save(existingImage);
                }
                // If there is an image with the given fileName
                if (image != null) {
                    Page page = this.pageRepository.findPageByPageIdIs(pageId);
                    image.setFkPageImage(page);
                    // this section checks if the file has a key named: "imageWidth"
                    if (body.containsKey("imageWidth")
                            && body.get("imageWidth") != null
                            && !body.get("imageWidth").isEmpty()) {
                        // If the body has imageWidth,  and it is not empty or null
                        Integer imageWidth = Integer.valueOf(body.get("imageWidth"));
                        image.setImageWidth(imageWidth);
                    }
                    // this section checks if the file has a key named: "imageHeight"
                    if (body.containsKey("imageHeight")
                            && body.get("imageHeight") != null
                            && !body.get("imageHeight").isEmpty()) {
                        // If the body has imageHeight, and it is not empty or null
                        Integer imageHeight = Integer.valueOf(body.get("imageHeight"));
                        image.setImageHeight(imageHeight);
                    }
                    // Finally, saving the image to the database and returning the ok response to the front end
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
}
