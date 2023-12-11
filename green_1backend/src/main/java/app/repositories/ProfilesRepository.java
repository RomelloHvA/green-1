package app.repositories;

import app.models.Profile;

import java.util.List;

public interface ProfilesRepository<E> {
    List<E> findAll();

    E findById(int id);
    E save(Profile profile);
}
