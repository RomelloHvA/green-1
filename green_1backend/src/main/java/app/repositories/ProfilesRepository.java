package app.repositories;

import app.models.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProfilesRepository<E> {
    List<E> findAll();

    E findById(int id);
    E save(Profile profile);
}
