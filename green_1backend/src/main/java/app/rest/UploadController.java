package app.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @PostMapping()
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            // Check if the file's content type is an image
            if (file.getContentType().startsWith("image/")) {
                // Generate a unique filename
                String filename = UUID.randomUUID().toString() + getFileExtension(file.getOriginalFilename());

                // Define the path to save the image
                String directoryPath = "/uploaded/images/";
                Path path = Paths.get(directoryPath + filename);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                // Save the image information in the database (omitted for brevity)

                // Create a relative path or URL to the saved image
                String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/images/")
                        .path(filename)
                        .toUriString();

                // Return the image URL in the response
                return ResponseEntity.ok().body(imageUrl);
            } else {
                return ResponseEntity.badRequest().body("File must be an image");
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Could not upload image");
        }
    }

    // Helper method to extract file extension
    private String getFileExtension(String filename) {
        if (filename != null && filename.contains(".")) {
            return filename.substring(filename.lastIndexOf("."));
        } else {
            return ""; // Default extension or throw an error if necessary
        }
    }

}

