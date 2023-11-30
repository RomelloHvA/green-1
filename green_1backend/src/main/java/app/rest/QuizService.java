package app.rest;

import app.models.Quiz;
import app.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz getQuizById(Long id) {


        return quizRepository.findById(id).orElse(null);
    }

    public Quiz getQuizByIsLive(long id) {
        return quizRepository.findByIsLiveAndSectorId(true, id);
    }

    public Quiz addQuiz(Quiz quiz) {
        if (quiz.getIsLive()){
            List<Quiz> quizzes = quizRepository.findAllBySector(quiz.getSector());
            for (Quiz q : quizzes) {
                if (q.getIsLive()){
                    q.setIsLive(false);
                    quizRepository.save(q);
                }
            }
        }

        return quizRepository.save(quiz);
    }

    public boolean deleteQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id).orElse(null);
        if (quiz == null) {
            return false;
        }
        quizRepository.delete(quiz);
        return true;
    }

}
