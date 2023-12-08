package app.repositories;

import app.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image findByFkPageImage_PageId(Long pageId);

    Image findByFileName(String fileName);
}
