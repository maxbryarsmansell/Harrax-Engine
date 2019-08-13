package com.max.harrax.graphics.renderables;

import com.max.harrax.graphics.Colour;
import com.max.harrax.maths.Vec2;
import com.max.harrax.maths.Vec4;

public class Sprite extends Renderable{
	
	public Sprite(float x, float y, float width, float height, Colour colour) {
		super(new Vec2(x - width / 2, y - height / 2), new Vec2(width, height), colour);
	}
	
	public Sprite(float x, float y, float width, float height) {
		super(new Vec2(x - width / 2, y - height / 2), new Vec2(width, height), Colour.WHITE);
	}
	
	public void setPosition(float x, float y) {
		this.position.x = x - size.x / 2;
		this.position.y = y - size.y / 2;
	}
	
	@Override
	public void setPosition(Vec2 position) {
		this.position.x = position.x - size.x / 2;
		this.position.y = position.y - size.y / 2;
	}
}
