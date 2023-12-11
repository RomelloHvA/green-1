package app.repositories;

import app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepositoryJPA extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
