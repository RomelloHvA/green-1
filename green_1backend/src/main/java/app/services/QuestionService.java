package app.services;

import app.models.*;
import app.repositories.QuizQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuizQuestionRepository quizQuestionRepository;

    private final QuizService quizService;




    @Autowired
    public QuestionService(QuizQuestionRepository quizQuestionRepository,
                           QuizService quizService) {
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizService = quizService;
    }

    public List<Question> getQuestions() {
        return quizQuestionRepository.findAll();
    }

    public Question getQuestionById(Long id) {
        return quizQuestionRepository.findById(id).orElse(null);
    }

    public Question addYesNoQuestion(Long quizindex ,YesNoQuestion question) {
        Quiz quiz = quizService.findById(quizindex);
        if(quiz == null){
            throw new RuntimeException("Quiz not found");
        }
        question.setQuiz(quiz);
        return quizQuestionRepository.save(question);
    }

    public Question addMultipleChoiceQuestion(Long quizindex , MultipleChoiceQuestion question) {
        Quiz quiz = quizService.findById(quizindex);
        if(quiz == null){
            throw new RuntimeException("Quiz not found");
        }
        question.setQuiz(quiz);
        for (MultipleChoiceOption option : question.getOptions()) {
            option.setMultipleChoiceQuestion(question);
        }
        return quizQuestionRepository.save(question);
    }

    public boolean deleteQuestion(Long id) {
        Question question =  quizQuestionRepository.findById(id).orElse(null);
        if (question == null) {
            return false;
        }
         quizQuestionRepository.deleteById(id);
        return true;
    }

}
