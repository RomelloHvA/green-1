package app.repositories;

import app.models.ActionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionPlanRepository extends JpaRepository<ActionPlan, Long> {

    List<ActionPlan> findAllBySectorId(Long sectorId);

}
