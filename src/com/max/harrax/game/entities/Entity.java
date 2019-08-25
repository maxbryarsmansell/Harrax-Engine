package com.max.harrax.game.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.max.harrax.game.entities.components.Component;

public class Entity {

	// Counts the amount of entities created.
	private static int currentEntityID = 0;

	/*
	 * Variables for: Entity id, Entity name, The components that the entity has,
	 * whether the entity should be removed
	 */
	private int id;
	private String name;
	Map<Class<?>, Component> components;
	private boolean shouldRemove;

	/*
	 * Simple constructor that takes a given name
	 */

	public Entity(String name) {
		this.id = currentEntityID++;
		this.name = name;
		this.components = new HashMap<Class<?>, Component>();
		this.shouldRemove = false;
	}

	/*
	 * Method for adding a component to the entity
	 */

	public void addComponent(Component component) {
		if (!components.containsKey(component.getClass())) {
			component.onEntityAdd(this);
			components.put(component.getClass(), component);
		}
	}

	/*
	 * Method for removing a component from the entity
	 */

	public <T> void removeComponent(Class<T> type) {
		if (components.containsKey(type)) {
			components.get(type).onEntityRemoval(this);
			components.remove(type);
		}
	}

	/*
	 * Method for getting a given component from the entity
	 */

	public <T extends Component> T getComponent(Class<T> type) {
		if (components.containsKey(type)) {
			return type.cast(components.get(type));
		}
		return null;
	}

	/*
	 * Method for checking whether the entity has a given component
	 */

	public <T> boolean hasComponent(Class<T> type) {
		return components.containsKey(type);
	}

	/*
	 * Method for checking whether the entity has given components
	 */

	public boolean hasComponents(List<Class<?>> type) {
		for (Class<?> t : type) {
			if (!components.containsKey(t)) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Print all of the components that the entity has
	 */

	public void printComponents() {
		System.out.format("The entity \"%s\" (ID %d) has the following components.\n{\n", name, id);
		for (Component c : components.values()) {
			System.out.print("	" + c.getClass().getSimpleName() + " => ");
			c.printContents();
		}
		System.out.println("}");
	}

	/*
	 * Set the entity ready for removal.
	 */

	public void setShouldRemove() {
		for (Component c : components.values()) {
			c.onEntityRemoval(this);
		}
		this.shouldRemove = true;
	}

	/*
	 * Getters and setters
	 */

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isShouldRemove() {
		return shouldRemove;
	}

}
