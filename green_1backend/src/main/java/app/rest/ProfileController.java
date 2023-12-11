package app.rest;

import app.models.Profile;
import app.repositories.ProfilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    ProfilesRepository<Profile> profilesRepository;

    @GetMapping(path = "test", produces = "application/json")
    public List<Profile> getTestProfile() {
        return List.of(
                new Profile(1, "John", "Smith"),
                new Profile(2, "Jan", "Bob"));
    }

    @GetMapping(path = "", produces = "application/json")
    public List<Profile> getAllUsers() {
        return profilesRepository.findAll();
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public Profile getProfileById(@PathVariable int id) {
        return profilesRepository.findById(id);
    }

    @PutMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Profile> saveProfile(@PathVariable int id, @RequestBody Profile profile) {
        if (id != profile.getUser_id()) {
            throw new RuntimeException("IDs do not match");
        }
        Profile existingProfile = profilesRepository.findById(parseInt(String.valueOf(id)));
        if (existingProfile == null) {
            throw new RuntimeException("Profile not found");
        }
        existingProfile.setFirst_name(profile.getFirst_name());
        existingProfile.setLast_name(profile.getLast_name())  ;
        existingProfile.setDate_of_birth(profile.getDate_of_birth());
        existingProfile.setBio(profile.getBio());
        existingProfile.setOccupation(profile.getOccupation());
        existingProfile.setPhoto(profile.getPhoto());
        existingProfile.setGoals(profile.getGoals());
        return ResponseEntity.ok().body(profilesRepository.save(existingProfile));
    }
}

