package graphics.renderables;

import java.util.ArrayList;

import graphics.Renderer;
import maths.Mat4;
import maths.Vec3;

public class Group extends Renderable {
	
	/*
	 * Attributes for
	 * children renderables
	 * transformation of all the children
	 */

	private ArrayList<Renderable> children;
	private Mat4 transformationMatrix;
	
	/*
	 * Constructor for the group
	 */

	public Group(Renderable renderable) {
		super(renderable);
		this.children = new ArrayList<Renderable>();
		this.transformationMatrix = Mat4.Translation(new Vec3(position.x, position.y, 0));
	}
	
	public Group(Mat4 transform) {
		super(null, null, null);
		this.children = new ArrayList<Renderable>();
		this.transformationMatrix = transform;
	}
	
	/*
	 * Function which adds a renderable to the children list
	 */

	public void addChild(Renderable renderable) {
		children.add(renderable);
	}
	
	/*
	 * Overridden submit method
	 */

	@Override
	public void submit(Renderer renderer) {
		
		if (position != null) {
			super.submit(renderer);
		}
		
		renderer.push_transformation(transformationMatrix);
		for (Renderable r : children) {
			r.submit(renderer);
		}
		renderer.pop_transformation();
	}

}
