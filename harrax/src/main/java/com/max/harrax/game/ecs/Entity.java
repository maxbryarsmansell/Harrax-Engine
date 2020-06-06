package com.max.harrax.game.ecs;

import com.max.harrax.game.ecs.components.Component;
import com.max.harrax.game.ecs.components.ComponentType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Entity {

    private static int currentEntityID = 0;

    private int id;
    private String name;
    private List<Component> components;

    public Entity(String name) {
        this.id = currentEntityID++;
        this.name = name;
        this.components = new ArrayList<>();
    }

    public List<Component> getComponents() {
        return components;
    }

    public boolean addComponent(Component component) {
        for (Component c : components) {
            if (c.getComponentType() == component.getComponentType()) return false;
        }

        components.add(component);

        return true;
    }

    public Component getComponent(ComponentType type) {
        for (Component c : components) {
            if (c.getComponentType() == type)
                return c;
        }

        return null;
    }

    public boolean removeComponent(ComponentType type) {
        Iterator<Component> it = components.iterator();

        while (it.hasNext()) {
            Component c = it.next();
            if (c.getComponentType() == type) {
                it.remove();
                return true;
            }
        }

        return false;
    }

    public String toString() {
        StringBuilder bString = new StringBuilder();

        bString.append("{\n");
        bString.append(String.format("  ID: %d,\n", id));
        bString.append(String.format("  Name: %s,\n", name));
        for (Component c : components) {
            bString.append(String.format("  %s,\n", c));
        }
        bString.append('}');

        return bString.toString();
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void dispose() {

    }
}
