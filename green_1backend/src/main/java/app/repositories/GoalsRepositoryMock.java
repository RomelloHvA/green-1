package app.repositories;

import app.models.Goal;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository("GOALS.INMEMORY")
public class GoalsRepositoryMock implements GoalsRepository {

    private List<Goal> goals;
    static Random randomizer = new Random();

    public GoalsRepositoryMock() {
        goals = new ArrayList<>();
        int numberOfGoals = 3;

        for (int i = 0; i < numberOfGoals ; i++) {
            goals.add(Goal.createSampleGoal((i+1), i));
        }
    }

    @Override
    public List findAll() {
        return goals;
    }

    @Override
    public Goal findById(int id) {
        return goals.stream().filter(goal -> goal.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Goal save(Goal goal) {
        goal.setId(goal.getId());
        goals.add(goal);
        return goal;
    }

    @Override
    public Goal deleteById(int id) {
        Goal toRemove = findById(id);
        goals.remove(toRemove);
        return toRemove;
    }
}
