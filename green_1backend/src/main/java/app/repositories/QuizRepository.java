package app.repositories;

import app.models.Quiz;
import app.models.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findAll();

    List<Quiz> findAllBySector(Sector sector);
    Quiz findById(int id);

    Quiz findByIsLiveAndSectorId(boolean isLive, long sectorId);

    Quiz save(Quiz quiz);

    void delete(Quiz quiz);
}
