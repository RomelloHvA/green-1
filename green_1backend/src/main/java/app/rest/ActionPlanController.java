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
    ActionPlanRepository actionPlanRepo;

    // Find all ActionPlans by sector ID
    @GetMapping("/{sectorId}")
    public List<ActionPlan> findAllBySectorId(@PathVariable Long sectorId) {
        return actionPlanRepo.findAllBySectorId(sectorId);
    }

}
