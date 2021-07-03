package com.max.harrax.game.ecs.components;

import java.util.List;

public class TransformComponent implements Component {


    @Override
    public List<ComponentType> getRequiredComponents() {
        return null;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.TransformComponent;
    }

}
