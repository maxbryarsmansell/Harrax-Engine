package com.max.harrax.game.ecs.components;

import com.max.harrax.maths.Vec2;

import java.util.List;

public class TransformComponent implements Component {

    private Vec2 position;
    private Vec2 scale;

    public TransformComponent(Vec2 position, Vec2 scale) {
        this.position = position;
        this.scale = scale;
    }

    public TransformComponent(float px, float py, float sx, float sy) {
        this(new Vec2(px, py), new Vec2(sx, sy));
    }

    @Override
    public List<ComponentType> getRequiredComponents() {
        return null;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.TransformComponent;
    }

    public String toString() {
        return String.format("Transform: { Position: %s, Scale: %s }", position, scale);
    }

    public Vec2 getPosition() {
        return position;
    }

    public void setPosition(Vec2 position) {
        this.position = position;
    }

    public Vec2 getSize() {
        return scale;
    }

    public void setSize(Vec2 size) {
        this.scale = size;
    }
}
