package com.max.harrax.graphics.renderables;

import java.util.ArrayList;

import com.max.harrax.graphics.Colour;
import com.max.harrax.graphics.Renderer;
import com.max.harrax.graphics.layers.Layer;
import com.max.harrax.maths.Mat4;
import com.max.harrax.maths.Vec2;
import com.max.harrax.maths.Vec3;
import com.max.harrax.maths.Vec4;

public class Group extends Renderable {

	/*
	 * Attributes for children renderables transformation of all the children
	 */

	private ArrayList<Renderable> children;
	private Mat4 transformationMatrix;

	/*
	 * Constructor for the group
	 */

	public Group(Mat4 transform) {
		super(null, null, null);
		this.children = new ArrayList<Renderable>();
		this.transformationMatrix = transform;
	}

	@Override
	public void setPosition(Vec2 position) {
		this.transformationMatrix = Mat4.Translation(position);
	}

	@Override
	public void onAddedToLayer(Layer layer) {
		for (Renderable r : children) {
			r.onAddedToLayer(layer);
		}
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
		if (visible) {
			renderer.push_transformation(transformationMatrix);
			for (Renderable r : children) {
				r.submit(renderer);
			}
			renderer.pop_transformation();
		}
	}

}
