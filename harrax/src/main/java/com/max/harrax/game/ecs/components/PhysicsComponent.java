package com.max.harrax.game.ecs.components;

import com.max.harrax.maths.Vec2;

import java.util.Arrays;
import java.util.List;

import static com.max.harrax.game.ecs.components.ComponentType.*;

public class PhysicsComponent implements Component {

    private Vec2 velocity;

    public Vec2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vec2 velocity) {
        this.velocity = velocity;
    }

    public PhysicsComponent(Vec2 velocity) {
        this.velocity = velocity;
    }

    @Override
    public List<ComponentType> getRequiredComponents() {
        return Arrays.asList(TransformComponent);
    }

    @Override
    public ComponentType getComponentType() {
        return PhysicsComponent;
    }

}
