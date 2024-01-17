package app.repositories;

import app.models.Goal;
import app.models.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository("PROFILES.INMEMORY")
public class ProfilesRepositoryMock implements ProfilesRepository {

    private List<Profile> profiles;
    static Random randomizer = new Random();

    private GoalsRepository<Goal> goalsRepo;

    public ProfilesRepositoryMock(GoalsRepository<Goal> goalsRepo) {
        this.goalsRepo = goalsRepo;
        profiles = new ArrayList<>();
        int numberOfUsers = 2;
        Profile profile = new Profile(1, "John", "Smith");
        profiles.add(profile);
        Profile profile2 = new Profile(2, "Jan", "Bob");
        profiles.add(profile2);

//        for (int i = 0; i < numberOfUsers; i++) {
//            Profile profile = Profile.createSampleProfile(i, null);
//            profiles.add(profile);
//            associateGoalToProfile(profile);
//        }
    }

//    private void associateGoalToProfile(Profile profile) {
//        List<Goal> goals = new ArrayList<>();
//        profile.setGoals(goals);
//        for (int i = 0; i < 3; i++) {
//            Goal goal = Goal.createSampleGoal(i+1, profile.getUser_id());
//            goals.add(goal);
//            profile.setGoals(goals);
//        }
//    }

    @Override
    public List findAll() {
        return profiles;
    }

    @Override
    public Profile findById(int id) {
        for (Profile profile : profiles) {
            if (id == profile.getUser_id()) {
                return profile;
            }
        }
        return null;
    }

    @Override
    public Profile save(Profile profile) {
        Profile existingProfile = findById(profile.getUser_id());
        int index = profiles.indexOf(existingProfile);
        profiles.set(index, profile);
        return profile;
    }
}
