package com.max.harrax.game.ecs.systems;

import com.max.harrax.game.ecs.Entity;
import com.max.harrax.game.ecs.components.ComponentType;
import com.max.harrax.game.ecs.components.MeshComponent;
import com.max.harrax.game.ecs.components.PhysicsComponent;
import com.max.harrax.game.ecs.components.TransformComponent;

import java.util.Arrays;
import java.util.List;

public class PhysicsSystem extends System {

    @Override
    public void onUpdate(float delta) {
        for (Entity e : entities) {
            TransformComponent tc = (TransformComponent) e.getComponent(ComponentType.TransformComponent);
            PhysicsComponent pc = (PhysicsComponent) e.getComponent(ComponentType.PhysicsComponent);

            tc.getPosition().add(pc.getVelocity()).mult(delta);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    protected List<ComponentType> getRequiredComponents() {
        return Arrays.asList(ComponentType.TransformComponent, ComponentType.PhysicsComponent);
    }
}
