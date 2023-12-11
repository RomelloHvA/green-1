package app.repositories;

import app.models.User;

import java.util.List;

public interface UsersRepository<E> {
    List<E> findAll();

    User findById(long id);
    User save(User user);
    User deleteById(long id);

    User findByUserName(String userName);
}
