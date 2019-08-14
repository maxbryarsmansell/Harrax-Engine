package com.max.harrax.graphicsOLD.renderables;

import com.max.harrax.graphicsOLD.Colour;
import com.max.harrax.graphicsOLD.Renderer;
import com.max.harrax.layer.Layer;
import com.max.harrax.maths.Vec2;
import com.max.harrax.maths.Vec4;

public class Renderable {

	/*
	 * Attributes for position of the renderable size of the renderable colour of
	 * the renderable
	 */

	protected Vec2 position;
	protected Vec2 size;
	protected Colour colour;
	protected boolean visible;
	protected boolean shouldRemove;

	/*
	 * Constructor for the renderable.
	 */

	public Renderable(Vec2 position, Vec2 size, Colour colour) {
		this.position = position;
		this.size = size;
		this.colour = colour;
		this.visible = false;
		this.shouldRemove = false;
	}

	public void onAddedToLayer(Layer layer) {
		this.visible = true;
	}

	public void onRemovedFromLayer(Layer layer) {
		position = null;
		size = null;
		colour = null;
	}

	/*
	 * Function to submit renderable to the renderer.
	 */

	public void submit(Renderer renderer) {
		if (visible) {
			renderer.submitQuad(this);
		}
	}

	/*
	 * Getters and setters for attributes.
	 */

	public Vec2 getPosition() {
		return position;
	}

	public void setPosition(Vec2 position) {
		this.position = position;
	}

	public Vec2 getSize() {
		return size;
	}

	public void setSize(Vec2 size) {
		this.size = size;
	}

	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisibile(boolean visible) {
		this.visible = visible;
	}

	public void shouldRemove() {
		this.shouldRemove = true;
	}

	public boolean isShouldRemove() {
		return this.shouldRemove;
	}

}
