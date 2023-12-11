package app.rest;

import app.models.Quiz;
import app.repositories.QuizRepository;
import app.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Quiz> getQuizByLive(@RequestParam Long sectorId) {
        return quizService.findLiveQuizForSector(sectorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
    @PostMapping("/{id}")
    public ResponseEntity<Quiz>  createQuiz(@RequestBody Quiz quiz) {
        Quiz newQuiz = quizService.createQuiz(quiz);
        return ResponseEntity.status(HttpStatus.CREATED).body(newQuiz);
    }

    // Update a quiz
    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody Quiz quiz) {
        return quizService.update(id, quiz)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a quiz
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }
}
