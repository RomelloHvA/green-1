package app.rest;

import app.models.Quiz;
import app.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizRepository quizRepository;

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizRepository quizRepository, QuizService quizService) {
        this.quizRepository = quizRepository;
        this.quizService = quizService;
    }

    @GetMapping("/live")
    public ResponseEntity<Quiz> getQuizById(@RequestParam long sectorID) {
        return ResponseEntity.ok(quizService.getQuizByIsLive(sectorID));
    }

    // Get all quizzes
    @GetMapping("/all")
    public List<Quiz> getAllQuizzes() {
        return quizService.getQuizzes();
    }

    // Get a single quiz by ID
    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        return quizRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new quiz
    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizRepository.save(quiz);
    }

    // Update a quiz
    @PostMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody Quiz quiz) {
        return ResponseEntity.status(HttpStatus.CREATED).body(quizService.addQuiz(quiz));
    }

    // Delete a quiz
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id) {
        return quizRepository.findById(id)
                .map(quiz -> {
                    quizRepository.delete(quiz);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
