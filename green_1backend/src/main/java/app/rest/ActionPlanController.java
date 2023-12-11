package app.rest;

import app.models.ActionPlan;
import app.repositories.ActionPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actionplans")
public class ActionPlanController {

    @Autowired
    ActionPlanRepository<ActionPlan> actionPlanRepo;

    @GetMapping(path = "", produces = "application/json")
    public List<ActionPlan> getAllActionPlan() {
        return actionPlanRepo.findAll();
    }

    @GetMapping(path = "/sdg/{id}", produces = "application/json")
    public ResponseEntity<List<ActionPlan>> getActionPlanById(@PathVariable int id) {
        return ResponseEntity.ok().body(actionPlanRepo.findBySdgId(id));
    }
}
