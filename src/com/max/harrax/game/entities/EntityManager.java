package com.max.harrax.game.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.max.harrax.game.entities.components.Component;
import com.max.harrax.game.entities.systems.System;

public class EntityManager {

	/*
	 * Attributes for Entities being managed by the entity manager Systems being
	 * updated by the entity manager
	 */

	private ArrayList<Entity> entities;
	private ArrayList<System> systems;

	/*
	 * Entity manager constructor
	 */

	public EntityManager() {
		this.entities = new ArrayList<Entity>();
		this.systems = new ArrayList<System>();
	}

	/*
	 * Entity managers update method
	 */

	public void update(float delta) {
		checkEntityRemoval();
		for (System s : systems) {
			List<Entity> toSubmit = new ArrayList<Entity>();
			for (Entity e : entities) {
				if (e.hasComponents(s.getComponentsRequired()) && !e.isShouldRemove()) {
					toSubmit.add(e);
				}
			}
			s.update(toSubmit, delta);
		}
	}

	/*
	 * Entity managers render method
	 */

	public void render() {
		for (System s : systems) {
			List<Entity> toSubmit = new ArrayList<Entity>();
			for (Entity e : entities) {
				if (e.hasComponents(s.getComponentsRequired()) && !e.isShouldRemove()) {
					toSubmit.add(e);
				}
			}
			s.render(toSubmit);
		}
	}

	/*
	 * Check for any entities that need to be removed
	 */

	private void checkEntityRemoval() {
		Iterator<Entity> iter = entities.iterator();
		while (iter.hasNext()) {
			Entity e = iter.next();
			if (e.isShouldRemove()) {
				iter.remove();
				continue;
			}
		}
	}

	/*
	 * Method for adding systems to the entity manager
	 */

	public void addSystem(System system) {
		if (!systems.contains(system)) {
			systems.add(system);
		}
	}

	/*
	 * Method for creating entities directly with the manager
	 */

	public Entity createEntity(String name, List<Component> components) {
		Entity e = new Entity(name);

		List<Class<?>> componentTypes = new ArrayList<Class<?>>();
		for (Component t : components) {
			componentTypes.add(t.getClass());
		}

		for (Component c : components) {

			if (!componentTypes.containsAll(c.getRequireComponents())) {
				java.lang.System.out.format("The entity \"%s\" (ID %d) requires the following components: \n",
						e.getName(), e.getId());
				for (Class<?> o : c.getRequireComponents()) {
					java.lang.System.out.println(o.getSimpleName());
				}
				java.lang.System.out.format("For %s to be added. \n", c.getClass().getSimpleName());
				throw new RuntimeException();
			}

			e.addComponent(c);
		}
		entities.add(e);
		return e;
	}

}
