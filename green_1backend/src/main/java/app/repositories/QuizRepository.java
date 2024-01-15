package app.repositories;

import app.models.Quiz;
import app.models.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("QUIZ.JPA")
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findAllBySector(Sector sector);

    Optional<Quiz> findByIsLiveAndSectorId(boolean isLive, long sectorId);

}
