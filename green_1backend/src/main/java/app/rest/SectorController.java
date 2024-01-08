package app.rest;

import app.models.Sector;
import app.repositories.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sector")
public class SectorController {

    private final SectorRepository sectorRepository;
    @Autowired

    public SectorController(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    @GetMapping("/all")
    public List<Sector> getAllSectors() {
        return sectorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSectorById(@PathVariable Long id) {
        Sector sector = sectorRepository.findById(id).orElse(null);
        if (sector == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sector with id " + id + " not found");
        }
        return ResponseEntity.ok(sector);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> updateSector(@PathVariable Long id, @RequestBody Sector sector) {
        //New sector
        if (id == 0L) {
            Sector newSector = sectorRepository.save(sector);
            return ResponseEntity.status(HttpStatus.CREATED).body(newSector);
        }
        if (!id.equals(sector.getId())) {
            // Id in the URL and body are not the same
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Id in URL and body are not the same");
        } else {
            // Update existing sector
            Sector savedSector = sectorRepository.save(sector);
            return ResponseEntity.ok(savedSector);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSector(@PathVariable Long id) {
        Sector sectorToDelete = sectorRepository.findById(id).orElse(null);
        // Not found
        if (sectorToDelete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sector with id " + id + " not found");
        } else {
            try {
                sectorRepository.deleteById(id);
                return ResponseEntity.ok("Sector with id " + id + " deleted" + sectorToDelete);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Sector with id " + id + " can not be deleted because " + e.getLocalizedMessage());
            }
        }
    }
}
