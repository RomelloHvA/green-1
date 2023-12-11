package app.repositories;

import app.models.MultipleChoiceOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultipleChoiceOptionRepository extends JpaRepository<MultipleChoiceOption, Long> {
}
