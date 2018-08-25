package graphics.renderables;

import graphics.Colour;
import maths.Vec2;
import maths.Vec4;

public class Sprite extends Renderable{
	
	public Sprite(float x, float y, float width, float height, Colour colour) {
		super(new Vec4(x, y, 0, 1), new Vec2(width, height), colour);
	}
	
	public Sprite(float x, float y, float width, float height) {
		super(new Vec4(x, y, 0, 1), new Vec2(width, height), Colour.WHITE);
	}

}
