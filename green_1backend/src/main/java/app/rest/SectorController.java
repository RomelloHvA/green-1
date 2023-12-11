package app.rest;

import app.models.Sector;
import app.repositories.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Sector getSectorById(Long id) {
        return sectorRepository.findById(id).orElse(null);
    }

    @PostMapping("/{id}")
    public Sector updateSector(@PathVariable Long id, @RequestBody Sector sector) {
        return sectorRepository.save(sector);
    }

    @DeleteMapping("/{id}")
    public boolean deleteSector(@PathVariable Long id) {
        Sector sector = sectorRepository.findById(id).orElse(null);
        if (sector == null) {
            return false;
        }
        sectorRepository.delete(sector);
        return true;
    }
}
