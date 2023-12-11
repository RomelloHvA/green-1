package app.repositories;

import app.models.Goal;

import java.util.List;

public interface GoalsRepository<E> {
    List<E> findAll();

    Goal findById(int id);
    Goal save(Goal goal);

    E deleteById(int id);

//    E findIdById(int id);
}
