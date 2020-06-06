package com.max.harrax.game.ecs;

import com.max.harrax.game.ecs.components.Component;
import com.max.harrax.game.ecs.components.ComponentType;
import com.max.harrax.game.ecs.systems.System;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityManager {

    private List<Entity> entities;
    private List<System> systems;

    public EntityManager() {
        this.entities = new ArrayList<Entity>();
        this.systems = new ArrayList<System>();
    }

    public void onUpdate(float delta) {
        for (System s : systems)
            s.onUpdate(delta);
    }

    public List<Entity> getEntities(List<ComponentType> types) {
        List<Entity> ret = new ArrayList<Entity>();

        for (Entity e : entities) {
            boolean shouldAdd = true;
            for (Component c : e.getComponents()) {
                if (!types.contains(c.getComponentType())) {
                    shouldAdd = false;
                    break;
                }
            }

            if (shouldAdd)
                ret.add(e);
        }

        return ret;
    }

    public void addSystem(System system) {
        if (!systems.contains(system)) {
            systems.add(system);
            system.onAddedToManager(this);
        }
    }

    public Entity createEntity(String name, Component... components) {

        Entity e = new Entity(name);

        for (Component c : components) {
            e.addComponent(c);
        }

        entities.add(e);

        return e;
    }

    public void dispose() {
        for (System s : systems)
            s.dispose();

        for (Entity e : entities)
            e.dispose();
    }
}
