package com.max.harrax.game.entities.components;

import java.util.Arrays;
import java.util.List;

import com.max.harrax.game.entities.Entity;
import com.max.harrax.maths.Vec2;

public class TransformComponent implements Component {

	/*
	 * Attributes for Position of entity Size of entity
	 */

	private Vec2 position;
	private Vec2 size;

	/*
	 * Constructors that take different parameters
	 */

	public TransformComponent(Vec2 position, Vec2 size) {
		this.position = position;
		this.size = size;
	}

	public TransformComponent(float x, float y, float width, float height) {
		this(new Vec2(x, y), new Vec2(width, height));
	}

	public TransformComponent(float width, float height) {
		this(new Vec2(0.0f, 0.0f), new Vec2(width, height));
	}

	/*
	 * Print contents of the transform component
	 */

	@Override
	public void printContents() {
		System.out.format("{ Position: \"%s\", Size: \"%s\" } \n", position.toString(), size.toString());
	}

	@Override
	public void onEntityRemoval(Entity e) {
		position = null;
		size = null;
	}
	
	@Override
	public List<Class<?>> getRequireComponents() {
		return Arrays.asList();
	}
	
	@Override
	public void onEntityAdd(Entity e) {
	}

	/*
	 * Getters and setters for component attributes
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



}
