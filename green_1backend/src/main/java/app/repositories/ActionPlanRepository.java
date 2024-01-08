package app.repositories;

import app.models.ActionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ActionPlanRepository extends JpaRepository<ActionPlan, Long> {

    List<ActionPlan> findAllBySectorId(Long sectorId);

    @Query("SELECT a FROM ActionPlan a JOIN a.sdgArray sdg WHERE a.sector.id = :sectorId AND sdg IN :sdgIds GROUP BY a HAVING COUNT(DISTINCT sdg) = :countSdgs")
    List<ActionPlan> findBySectorAndSdgs(@Param("sectorId") Long sectorId, @Param("sdgIds") List<Long> sdgIds, @Param("countSdgs") long countSdgs);


    @Query("SELECT a FROM ActionPlan a JOIN a.sdgArray sdg WHERE a.sector.id = :sectorId AND sdg IN :sdgIds GROUP BY a")
    List<ActionPlan> findBySectorAndSdgsWithoutLengthFiler(@Param("sectorId") Long sectorId, @Param("sdgIds") List<Long> sdgIds);

}
