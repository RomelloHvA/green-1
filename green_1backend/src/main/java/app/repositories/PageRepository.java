package app.repositories;

import app.models.Page;
import org.springframework.data.repository.CrudRepository;

public interface PageRepository extends CrudRepository<Page, Integer> {

    Iterable<Page> findAll();
}
