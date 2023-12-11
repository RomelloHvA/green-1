package app.repositories;

import app.models.ActionPlan;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository("ACTIONPLAN.INMEMORY")
public class ActionPlanRepositoryMock implements ActionPlanRepository {

    private List<ActionPlan> actionPlans;

    public ActionPlanRepositoryMock() {
        actionPlans = new ArrayList<>();
        int numberOfActionPlans = 17;

        for (int i = 1; i <= numberOfActionPlans; i++) {
            actionPlans.addAll(ActionPlan.createSamplePlan(i));
        }
    }

    @Override
    public List findAll() {
        return actionPlans;
    }

    @Override
    public ActionPlan findById(int id) {
        return actionPlans.stream().filter(actionPlan -> actionPlan.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<ActionPlan> findBySdgId(int id) {
        return actionPlans.stream().filter(actionPlan -> actionPlan.getSdgId() == id).collect(Collectors.toList());
    }
}
