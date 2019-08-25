package com.max.harrax.game.entities.components;

import java.util.List;

import com.max.harrax.game.entities.Entity;

public interface Component {
	
	/*
	 * All components should have a function for printing their contents - debugging
	 * purpose.
	 */

	public abstract void printContents();
	
	/*
	 * All components should have access to the entity they are attached to
	 */

	public abstract void onEntityAdd(Entity e);
	
	/*
	 * All components should have access to the entity they are attached to when its deleted
	 */

	public abstract void onEntityRemoval(Entity e);
	
	/*
	 * All components should return a list of the other components they require.
	 */

	public abstract List<Class<?>> getRequireComponents();

}
