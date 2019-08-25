package com.max.harrax.maths;

import java.nio.FloatBuffer;
import java.text.DecimalFormat;

import org.lwjgl.BufferUtils;

public class Vec2 {

	/*
	 * x and y components, represented as floats.
	 */

	public float x;
	public float y;


	public Vec2() {
		this.x = 0.0f;
		this.y = 0.0f;
	}

	public Vec2(final float x, final float y) {
		this.x = x;
		this.y = y;
	}

	public Vec2(final Vec2 other) {
		this.x = other.x;
		this.y = other.y;
	}

	public Vec2 add(final Vec2 other) {
		return new Vec2(x + other.x, y + other.y);
	}

	public Vec2 sub(final Vec2 other) {
		return new Vec2(x + other.x, y + other.y);
	}

	public Vec2 mult(final float scalar) {
		return new Vec2(x * scalar, y * scalar);
	}

	public Vec2 div(float scalar) {
		return new Vec2(x / scalar, y / scalar);
	}
	
	public float dot(final Vec2 other) {
		return (x * other.x) + (y * other.y);
	}

	public float dist(final Vec2 other) {
		float a = x - other.x;
		float b = y - other.y;
		return (float) Math.sqrt(a * a + b * b);
	}

	public float mag() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public void normalize() {
		float length = mag();
		x /= length;
		y /= length;
	}

	public void negate() {
		x = -x;
		y = -y;
	}
	
	public Vec4 toVec4() {
		return new Vec4(x, y, 0, 1);
	}
	
	public boolean equals(final Vec2 other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x)) {
			return false;
		}
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y)) {
			return false;
		}
		
		return true;
	}

	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		return new StringBuilder().append("(").append(df.format(x)).append(", ").append(df.format(y)).append(")")
				.toString();
	}

	public void toBuffer(FloatBuffer buffer) {
		buffer.put(x).put(y);
		buffer.flip();
	}

}
