package app.rest;

import app.models.*;
import app.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // Get all quizzes
    @GetMapping
    public List<Question> getAllQuizzes() {
        return questionService.getQuestions();
    }

    // Get a single quiz by ID
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuizById(@PathVariable Long id) {
        Question question = questionService.getQuestionById(id);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(question);

    }

    @PostMapping("/yesno/{quizindex}")
    public ResponseEntity<Object> createYesNoQuestion(@PathVariable Long quizindex ,@RequestBody YesNoQuestion question) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.addYesNoQuestion(quizindex,question));
    }

    @PostMapping("/multiplechoice/{quizindex}")
    public ResponseEntity<Object> createMultipleChoiceQuestion(@PathVariable Long quizindex ,@RequestBody MultipleChoiceQuestion question) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.addMultipleChoiceQuestion(quizindex,question));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteQuestion(@PathVariable Long id) {
        boolean success = questionService.deleteQuestion(id);
        if (!success) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }


}
