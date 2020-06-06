package com.max.harrax.game.ecs.systems;

import com.max.harrax.game.ecs.EntityManager;
import com.max.harrax.game.ecs.components.ComponentType;
import com.max.harrax.game.ecs.Entity;

import java.util.List;

public abstract class System {

    protected List<Entity> entities;

    public void onAddedToManager(EntityManager manager) {
        entities = manager.getEntities(getRequiredComponents());
    }

    public abstract void onUpdate(float delta);

    public abstract void dispose();

    protected abstract List<ComponentType> getRequiredComponents();
}
