package app.services;

import app.models.Quiz;
import app.repositories.QuizRepository;
import app.rest.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Quiz findById(Long id) {
        return quizRepository.findById(id).orElse(null);
    }

    public Optional<Quiz> findLiveQuizForSector(Long id) {
        return quizRepository.findByIsLiveAndSectorId(true, id);
    }

    @Transactional
    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Transactional
    public Optional<Quiz> update(Long id, Quiz quiz) {
        if (quiz.getIsLive()){
            List<Quiz> quizzes = quizRepository.findAllBySector(quiz.getSector());
            for (Quiz q : quizzes) {
                if (q.getIsLive()){
                    q.setIsLive(false);
                    quizRepository.save(q);
                }
            }
        }

        return quizRepository.findById(id)
                .map(q -> {
                    q.setIsLive(quiz.getIsLive());
                    q.setSector(quiz.getSector());
                    q.getQuizQuestions().clear();
                    if (quiz.getQuizQuestions() != null) {
                        q.getQuizQuestions().addAll(quiz.getQuizQuestions());
                    }
                    q.setName(quiz.getName());
                    q.setIsConcept(quiz.getIsConcept());
                    q.setIsPublished(quiz.getIsPublished());
                    q.setIsLive(quiz.getIsLive());
                    return q;
                })
                .map(quizRepository::save);
    }

    public void deleteQuiz(Long id) {
        if (!quizRepository.existsById(id)) {
            throw new ResourceNotFoundException("Quiz not found with id " + id);
        }
        quizRepository.deleteById(id);
    }

}
