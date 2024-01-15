package app.repositories;

import app.models.MultipleChoiceQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("MULTIPLECHOICEQUESTION.JPA")
public interface MultipleChoiceQuestionRepository  extends JpaRepository<MultipleChoiceQuestion, Long> {
}
