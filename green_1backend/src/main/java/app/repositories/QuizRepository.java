package app.repositories;

import app.models.Quiz;
import app.models.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findAllBySector(Sector sector);

    Optional<Quiz> findByIsLiveAndSectorId(boolean isLive, long sectorId);

}
