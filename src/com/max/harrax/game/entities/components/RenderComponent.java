package com.max.harrax.game.entities.components;

import java.util.Arrays;
import java.util.List;

import com.max.harrax.game.entities.Entity;
import com.max.harrax.graphics.Colour;
import com.max.harrax.graphics.renderables.Sprite;

public class RenderComponent implements Component {

	/*
	 * Attributes for Entities sprite
	 */

	private Sprite sprite;

	/*
	 * Render component constructor
	 */

	public RenderComponent(Colour colour) {
		//this.sprite = new Sprite();
		//sprite.setVisibile(true);
		//sprite.setColour(colour);
	}

	/*
	 * Print contents of the render component
	 */

	@Override
	public void printContents() {
		System.out.format("{ Colour: \"%s\", Is visible: \"%s\" } \n", sprite.getColour().toVec4().toString(),
				sprite.isVisible());
	}
	
	@Override
	public void onEntityRemoval(Entity e) {
		sprite.shouldRemove();
	}
	
	@Override
	public void onEntityAdd(Entity e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<Class<?>> getRequireComponents() {
		return Arrays.asList(TransformComponent.class);
	}
	

	/*
	 * Getters and setters
	 */

	public Sprite getSprite() {
		return sprite;
	}


}
