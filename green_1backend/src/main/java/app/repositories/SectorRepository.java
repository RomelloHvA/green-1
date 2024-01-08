package app.repositories;

import app.models.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

    @Query("SELECT s FROM Sector s JOIN Quiz q ON q.sector.id = s.id WHERE q.isLive = TRUE")
    List<Sector> findAllWithLiveQuiz();

}
