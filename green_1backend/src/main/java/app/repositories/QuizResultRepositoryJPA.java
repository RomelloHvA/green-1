package app.repositories;

import app.models.QuizResult;
import app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizResultRepositoryJPA extends JpaRepository<QuizResult, Long> {

    List<QuizResult> findQuizResultByUser(User user);
}
