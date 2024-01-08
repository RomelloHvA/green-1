package app.repositories;

import app.models.QuizResult;
import org.springframework.stereotype.Repository;

@Repository("QUIZRESULT.JPA")
public class AbstractQuizResultEntityRepositoryJpa extends AbstractEntityRepositoryJpa<QuizResult>{

    public AbstractQuizResultEntityRepositoryJpa() {
        super(QuizResult.class);
    }
}
