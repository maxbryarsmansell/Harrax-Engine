package com.max.harrax.graphics;

import java.util.Random;

import com.max.harrax.maths.Vec3;
import com.max.harrax.maths.Vec4;
import com.max.harrax.utils.Property;

public class Colour {
	
	public static Colour BLACK = new Colour(0, 0, 0, 255);
	public static Colour WHITE = new Colour(255, 255, 255, 255);
	public static Colour RED = new Colour(255, 0, 0, 255);
	public static Colour GREEN = new Colour(0, 255, 0, 255);
	public static Colour BLUE = new Colour(0, 0, 255, 255);
	public static Colour TRANSPARENT = new Colour(0, 0, 0, 0);
	
	private int colour;
	
	static Random r = new Random();

	public Colour(int red, int green, int blue, int alpha) {
		colour = (red << 24) | (green << 16) | (blue << 12) | (alpha << 8);
	}

	public Colour(int red, int green, int blue) {
		this(red, green, blue, 255);
	}
	
	public Colour() {
		this(r.nextInt(255), r.nextInt(255), r.nextInt(255), 255);
	}

	public float getRed() {
		return (colour >> 24) & 0xFFFFFFFF;
	}

	public float getGreen() {
		return (colour >> 16) & 0xFFFFFFFF;
	}

	public float getBlue() {
		return (colour >> 12) & 0xFFFFFFFF;
	}

	public float getAlpha() {
		return (colour >> 8) & 0xFFFFFFFF;
	}

	public Vec3 toVec3() {
		float red = getRed() / 255f;
		float green = getGreen() / 255f;
		float blue = getBlue() / 255f;
		return new Vec3(red, green, blue);
	}

	public Vec4 toVec4() {
		float red = getRed() / 255f;
		float green = getGreen() / 255f;
		float blue = getBlue() / 255f;
		float alpha = getAlpha() / 255f;
		return new Vec4(red, green, blue, alpha);
	}

}
