package app.rest;

import app.models.QuizResult;
import app.repositories.QuizResultRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quizresult")
public class QuizResultController {

    private final QuizResultRepositoryJPA quizResultRepositoryJPA;

    @Autowired
    public QuizResultController(QuizResultRepositoryJPA quizResultRepositoryJPA) {
        this.quizResultRepositoryJPA = quizResultRepositoryJPA;
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuizResult>> getAllQuizResults() {
        List<QuizResult> quizResults = quizResultRepositoryJPA.findAll();
        return ResponseEntity.ok(quizResults);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizResult> getQuizResultById(@PathVariable long id) {
        Optional<QuizResult> optionalQuizResult = quizResultRepositoryJPA.findById(id);

        if (optionalQuizResult.isPresent()) {
            QuizResult quizResult = optionalQuizResult.get();
            return ResponseEntity.ok(quizResult);
        } else {
            throw new ResourceNotFoundException("Result not found with ID: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable long id) {
        Optional<QuizResult> optionalQuizResult = quizResultRepositoryJPA.findById(id);

        if (optionalQuizResult.isPresent()) {
            QuizResult quizResult = optionalQuizResult.get();
            quizResultRepositoryJPA.delete(quizResult);
            return ResponseEntity.noContent().build(); // 204 successfully deleted
        } else {
            throw new ResourceNotFoundException("QuizResult not found with ID: " + id);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<QuizResult> saveQuizResult(@RequestBody QuizResult quizResult) {
        QuizResult savedQuizResult = quizResultRepositoryJPA.save(quizResult);
        return new ResponseEntity<>(savedQuizResult, HttpStatus.CREATED); // 201 successfully saved
    }









}
