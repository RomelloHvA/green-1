package app.repositories;

import app.models.PageContent;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageContentRepository extends JpaRepository<PageContent, Integer> {
    List<PageContent> findAllByFkPage_PageId(Long pageId);
}

