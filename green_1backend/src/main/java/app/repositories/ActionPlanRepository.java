package app.repositories;

import app.models.ActionPlan;

import java.util.List;

public interface ActionPlanRepository<E> {
    List<E> findAll();

    ActionPlan findById(int id);
    List<ActionPlan> findBySdgId(int id);
}
