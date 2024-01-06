package app.rest;

import app.models.ActionPlan;
import app.models.Sector;
import app.repositories.ActionPlanRepository;
import app.repositories.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/actionplans")
public class ActionPlanController {

    @Autowired
    ActionPlanRepository actionPlanRepository;

    @Autowired
    SectorRepository sectorRepo;


    @GetMapping("")
    public List<ActionPlan> findAll() {
        return actionPlanRepository.findAll();
    }

    @GetMapping("/testSector")
    public ResponseEntity<Object> testdata() {
        for (int i = 0; i < 5; i++) {
            Sector sector = new Sector();
            sector.setId((long) i);
            sector.setName("name: " + i);
            sector.setDescription("Description: " + i);
            sectorRepo.save(sector);
        }
        return ResponseEntity.ok("Sectors created");
    }

    // Find all ActionPlans by sector ID
    @GetMapping("/{sectorId}")
    public ResponseEntity<List<ActionPlan>> findAllBySectorId(@PathVariable Long sectorId) {
        // Check if the sector with the given ID exists
        if (!sectorRepo.existsById(sectorId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Sector exists, proceed with retrieving action plans
        List<ActionPlan> actionPlans = actionPlanRepository.findAllBySectorId(sectorId);
        return new ResponseEntity<>(actionPlans, HttpStatus.OK);
    }

    @DeleteMapping("/{sectorId}/{actionPlanId}")
    public ResponseEntity<Object> deleteActionPlan(@PathVariable Long sectorId, @PathVariable Long actionPlanId) {
        if (sectorRepo.existsById(sectorId) && actionPlanRepository.existsById(actionPlanId)){
            actionPlanRepository.deleteById(actionPlanId);
            return ResponseEntity.ok("action plan in sectorId: " + sectorId + " with id: " + actionPlanId + " deleted.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Action plan with id: " + actionPlanId + " or sector with id: " + sectorId + " not found.");
    }

    @PostMapping("/{sectorId}/{actionPlanId}")
    public ResponseEntity<Object> saveOrUpdateActionPlan(@PathVariable Long sectorId, @PathVariable Long actionPlanId,
            @RequestBody ActionPlan actionPlan) {

        if (!sectorRepo.existsById(sectorId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This sector does not exist");
        }

        if (!Objects.equals(actionPlan.getSector().getId(), sectorId)){
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Sector in Url does not match sector in action plan");
        }

        if (!Objects.equals(actionPlanId, actionPlan.getId())) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("id in Url does not match id in action plan");
        }
        actionPlanRepository.save(actionPlan);
        return ResponseEntity.ok(actionPlan);

    }

}
