package app.repositories;

import app.models.Question;
import app.models.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizResultRepositoryJPA extends JpaRepository<QuizResult, Long> {
}
