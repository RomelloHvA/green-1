package app.rest;

import app.models.QuizResult;
import app.models.User;
import app.repositories.QuizResultRepositoryJPA;
import app.repositories.UsersRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/quizresult")
public class QuizResultController {

    private final UsersRepositoryJPA usersRepositoryJPA;
    private final QuizResultRepositoryJPA quizResultRepositoryJPA;

    @Autowired
    public QuizResultController( QuizResultRepositoryJPA quizResultRepositoryJPA, UsersRepositoryJPA usersRepositoryJPA, QuizResultRepositoryJPA abstractQuizResultEntityRepositoryJpa) {
        this.quizResultRepositoryJPA = quizResultRepositoryJPA;
        this.usersRepositoryJPA = usersRepositoryJPA;
    }

    //Get all quiz results
    @GetMapping("/all")
    public ResponseEntity<List<QuizResult>> getAllQuizResults() {
        List<QuizResult> quizResults = quizResultRepositoryJPA.findAll();
        return ResponseEntity.ok(quizResults); // 200 found successfully
    }

    //Get a result by id
    @GetMapping("/{id}")
    public ResponseEntity<QuizResult> getQuizResultById(@PathVariable long id) {
        Optional<QuizResult> optionalQuizResult = Optional.ofNullable(quizResultRepositoryJPA.findById(id).orElse(null));

        if (optionalQuizResult.isPresent()) {
            QuizResult quizResult = optionalQuizResult.get();
            return ResponseEntity.ok(quizResult); // 200 found successfully
        } else {
            throw new ResourceNotFoundException("Result not found with ID: " + id);
        }
    }

    //Delete the results
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable long id) {
        Optional<QuizResult> optionalQuizResult = Optional.ofNullable(quizResultRepositoryJPA.findById(id).orElse(null));

        if (optionalQuizResult.isPresent()) {
            QuizResult quizResult = optionalQuizResult.get();
            quizResultRepositoryJPA.deleteById(quizResult.getResultId());
            return ResponseEntity.noContent().build(); // 204 successfully deleted
        } else {
            throw new ResourceNotFoundException("QuizResult not found with ID: " + id);
        }
    }

    //save the results
    @PostMapping("/save/{userId}")
    public ResponseEntity<QuizResult> saveQuizResult(@RequestBody QuizResult quizResult, @PathVariable long userId) {
        quizResult.setUser(this.usersRepositoryJPA.findById(userId).orElse(null));

        this.quizResultRepositoryJPA.save(quizResult);
        return ResponseEntity.ok(quizResult); // 200 successfully saved
    }

    // Gets results for a specific user
    @GetMapping("/getById/{userId}")
    public ResponseEntity<List<QuizResult>> getResultsForUser(
             @PathVariable long userId ) {
        User user = this.usersRepositoryJPA.findById(userId).orElse(null);
        List<QuizResult>quizResultsFromUser = this.quizResultRepositoryJPA.findQuizResultByUser(user);

        return ResponseEntity.ok(quizResultsFromUser); // 200 found successfully
    }

}
