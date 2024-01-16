package app.rest;

import app.models.Goal;
import app.repositories.GoalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goals")
public class GoalController {

    @Autowired
    GoalsRepository<Goal> goalsRepo;

    @GetMapping(path = "test", produces = "application/json")
    public List<Goal> getTestProfile() {
        return List.of(
                new Goal(1, 1, "No Poverty"),
                new Goal(2, 1, "Zero Hunger"),
                new Goal(3, 1, "Good Health and Well-being")
        );
    }

    @GetMapping(path = "", produces = "application/json")
    public List<Goal> getAllGoals() {
        return goalsRepo.findAll();
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Goal> getGoalById(@PathVariable int id) {
        return ResponseEntity.ok().body(goalsRepo.findById(id));
    }
    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity<Goal> saveGoal(@RequestBody Goal goal) {
        if (goal == null) return null;
        try {
            this.goalsRepo.save(goal);
            return ResponseEntity.ok(goal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Goal> deleteOneProfile(@PathVariable int id) {

        Goal goal = goalsRepo.deleteById(id);

        return ResponseEntity.ok().body(goal);
    }
}
