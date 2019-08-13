package com.max.harrax.graphics;

import java.util.Random;

import com.max.harrax.maths.Vec3;
import com.max.harrax.maths.Vec4;
import com.max.harrax.utils.Property;

public class Colour {

	public static Colour CLEAR_COLOUR = new Colour(1f, 1f, 1f, 1f);
	public static boolean inverted = Boolean.parseBoolean(Property.loadProperty("invertColours", "graphics"));
	
	public static Colour BLACK = new Colour(0f, 0f, 0f, 1f);
	public static Colour WHITE = new Colour(1f, 1f, 1f, 1f);
	public static Colour RED = new Colour(1f, 0f, 0f, 1f);
	public static Colour GREEN = new Colour(0f, 1f, 0f, 1f);
	public static Colour BLUE = new Colour(0f, 0f, 1f, 1f);
	public static Colour EMPTY_COLOUR = new Colour(0f, 0f, 0f, 0f);
	
	
	/*
	 * Attributes to represent the channels of colour in RBGA (Red, Green, Blue,
	 * Alpha)
	 */

	private float red, green, blue, alpha;

	/*
	 * Constructor which creates a new colour with given channel values
	 */

	public Colour(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	/*
	 * Constructor which creates a new colour with a given intensity
	 */

	public Colour(float intensity) {
		this(intensity, intensity, intensity, 1.0f);
	}
	
	/*
	 * Constructor which creates a new colour with only RGB (1.0f default alpha)
	 */
	
	public Colour(float red, float green, float blue) {
		this(red, green, blue, 1.0f);
	}
	
	/*
	 * Constructor which creates a new random colour
	 */
	
	public Colour() {
		Random r = new Random();
		this.red = r.nextFloat();
		this.green = r.nextFloat();
		this.blue = r.nextFloat();
		this.alpha = 1.0f;
	}

	/*
	 * Returns the red component
	 */

	public float getRed() {
		if (inverted) {
			return 1 - red;
		}
		return red;
	}

	/*
	 * Sets the red component
	 */

	public void setRed(float red) {
		if (red < 0f) {
			red = 0f;
		}
		if (red > 1f) {
			red = 1f;
		}
		this.red = red;
	}

	/*
	 * Returns the green component
	 */

	public float getGreen() {
		if (inverted) {
			return 1 - green;
		}
		return green;
	}

	/*
	 * Sets the green component
	 */

	public void setGreen(float green) {
		if (green < 0f) {
			green = 0f;
		}
		if (green > 1f) {
			green = 1f;
		}
		this.green = green;
	}

	/*
	 * Returns the blue component
	 */

	public float getBlue() {
		if (inverted) {
			return 1 - blue;
		}
		return blue;
	}

	/*
	 * Sets the blue component
	 */

	public void setBlue(float blue) {
		if (blue < 0f) {
			blue = 0f;
		}
		if (blue > 1f) {
			blue = 1f;
		}
		this.blue = blue;
	}

	/*
	 * Returns the alpha component
	 */

	public float getAlpha() {
		return alpha;
	}

	/*
	 * Sets the alpha component
	 */

	public void setAlpha(float alpha) {
		if (alpha < 0f) {
			alpha = 0f;
		}
		if (alpha > 1f) {
			alpha = 1f;
		}
		this.alpha = alpha;
	}

	/*
	 * Returns the colour as a vec3
	 */

	public Vec3 toVec3() {
		return new Vec3(red, green, blue);
	}

	/*
	 * Returns the colour as a vec4
	 */

	public Vec4 toVec4() {
		return new Vec4(red, green, blue, alpha);
	}

}
