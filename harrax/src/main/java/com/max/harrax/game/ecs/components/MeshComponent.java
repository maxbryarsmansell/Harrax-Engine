package com.max.harrax.game.ecs.components;

import com.max.harrax.graphics.Colour;

import java.util.Arrays;
import java.util.List;

import static com.max.harrax.game.ecs.components.ComponentType.*;

public class MeshComponent implements Component {

    private Colour colour;

    public MeshComponent(Colour colour) {
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }

    @Override
    public List<ComponentType> getRequiredComponents() {
        return Arrays.asList(TransformComponent);
    }

    @Override
    public ComponentType getComponentType() {
        return MeshComponent;
    }

    public String toString() {
        return String.format("Mesh:");
    }
}
