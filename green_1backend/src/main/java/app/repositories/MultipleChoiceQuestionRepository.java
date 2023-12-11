package app.repositories;

import app.models.MultipleChoiceQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultipleChoiceQuestionRepository  extends JpaRepository<MultipleChoiceQuestion, Long> {
}
