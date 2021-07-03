package com.max.harrax.game.ecs.components;

import java.util.Arrays;
import java.util.List;

import static com.max.harrax.game.ecs.components.ComponentType.*;

public class PhysicsComponent implements Component {


    @Override
    public List<ComponentType> getRequiredComponents() {
        return Arrays.asList(TransformComponent);
    }

    @Override
    public ComponentType getComponentType() {
        return PhysicsComponent;
    }

}
