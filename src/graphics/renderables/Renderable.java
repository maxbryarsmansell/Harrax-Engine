package graphics.renderables;

import graphics.Colour;
import graphics.Renderer;
import maths.Vec2;
import maths.Vec4;

public class Renderable {
	
	/*
	 * Attributes for
	 * position of the renderable
	 * size of the renderable
	 * colour of the renderable
	 */
	
	protected Vec4 position;
	protected Vec2 size;
	protected Colour colour;
	
	/*
	 * Constructor for the renderable.
	 */
	
	public Renderable(Vec4 position, Vec2 size, Colour colour) {
		this.position = position;
		this.size = size;
		this.colour = colour;
	}
	
	/*
	 * Constructor to initialise from existing renderable.
	 */
	
	public Renderable(Renderable renderable) {
		setPosition(renderable.getPosition());
		setSize(renderable.getSize());
		setColour(renderable.getColour());
	}
	
	/*
	 * Function to submit renderable to the renderer.
	 */
	
	public void submit(Renderer renderer) {
		renderer.submitQuad(this);
	}
	
	/*
	 * Getters and setters for attributes.
	 */

	public Vec4 getPosition() {
		return position;
	}
	
	public void setPosition(Vec4 position) {
		this.position = position;
	}
	
	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}
	
	public Vec2 getSize() {
		return size;
	}

	public void setSize(Vec2 size) {
		this.size = size;
	}

}
