package com.max.harrax.game.entities.systems;

import java.util.List;

import com.max.harrax.game.entities.Entity;

public interface System {
	
	/*
	 * Systems can implement logic in the update loop
	 */

	public abstract void update(List<Entity> entities, float delta);
	
	/*
	 * Systems can implement logic in the render loop
	 */

	public abstract void render(List<Entity> entities);
	
	
	/*
	 * Systems have a list of certain components that they process
	 */
	
	public abstract List<Class<?>> getComponentsRequired();

}
