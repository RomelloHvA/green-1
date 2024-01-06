package app.rest;

import app.models.QuizResult;
import app.models.User;
import app.repositories.QuizResultRepositoryJPA;
import app.repositories.UsersRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/quizresult")
public class QuizResultController {

    private final QuizResultRepositoryJPA quizResultRepositoryJPA;
    private final UsersRepositoryJPA usersRepositoryJPA;

    @Autowired
    public QuizResultController(QuizResultRepositoryJPA quizResultRepositoryJPA, UsersRepositoryJPA usersRepositoryJPA) {
        this.quizResultRepositoryJPA = quizResultRepositoryJPA;
        this.usersRepositoryJPA = usersRepositoryJPA;
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

    @PostMapping("/save/{userId}")
    public ResponseEntity<QuizResult> saveQuizResult(@RequestBody QuizResult quizResult, @PathVariable long userId) {
        quizResult.setUser(this.usersRepositoryJPA.findById(userId).orElse(null));

        this.quizResultRepositoryJPA.save(quizResult);
        return ResponseEntity.ok(quizResult); // 201 successfully saved
    }









}
