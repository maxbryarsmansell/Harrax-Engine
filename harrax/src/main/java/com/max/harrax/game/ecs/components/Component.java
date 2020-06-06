package com.max.harrax.game.ecs.components;

import java.util.List;

public interface Component {

    public abstract List<ComponentType> getRequiredComponents();
    public abstract ComponentType getComponentType();
}
