package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface YesNoQuestionRepository extends JpaRepository<app.models.YesNoQuestion, Long> {
}
