package com.max.harrax.maths;

import java.nio.FloatBuffer;
import java.text.DecimalFormat;

import org.lwjgl.BufferUtils;

public class Vec4 implements Cloneable {

	/*
	 * x, y, z and w components, represented as floats.
	 */

	public float x;
	public float y;
	public float z;
	public float w;

	public Vec4() {
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
		this.w = 0.0f;
	}

	public Vec4(final float x, final float y, final float z, final float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vec4(final Vec4 other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
		this.w = other.w;
	}

	public Vec4(final Vec3 other, final float w) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
		this.w = w;
	}

	public Vec4 add(final Vec4 other) {
		return new Vec4(x + other.x, y + other.y, z + other.z, w + other.w);
	}

	public Vec4 sub(final Vec4 other) {
		return new Vec4(x - other.x, y - other.y, z - other.z, w - other.w);
	}

	public Vec4 mult(final float scalar) {
		return new Vec4(x * scalar, y * scalar, z * scalar, w * scalar);
	}

	public Vec4 div(float scalar) {
		return new Vec4(x / scalar, y / scalar, z / scalar, w / scalar);
	}
	
	public Vec4 mult(final Mat4 other) {
		float nx = x * other.elements[0] + y * other.elements[4] + z * other.elements[8] + z * other.elements[12];
		float ny = x * other.elements[1] + y * other.elements[5] + z * other.elements[9] + z * other.elements[13];
		float nz = x * other.elements[2] + y * other.elements[6] + z * other.elements[10] + z * other.elements[14]; 
		float nw = x * other.elements[3] + y * other.elements[7] + z * other.elements[11] + z * other.elements[15]; 
		return new Vec4(nx, ny, nz, nw);
	}

	public float dot(final Vec4 other) {
		return (x * other.x) + (y * other.y) + (z * other.z) + (w * other.z);
	}

	public float dist(final Vec4 other) {
		float a = x - other.x;
		float b = y - other.y;
		float c = z - other.z;
		float d = w - other.w;
		return (float) Math.sqrt(a * a + b * b + c * c + d * d);
	}

	public float mag() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	public void normalize() {
		float length = mag();
		x /= length;
		y /= length;
		z /= length;
		w /= length;
	}

	public Vec4 norm() {
		float length = mag();
		return new Vec4(x / length, y / length, z / length, w / length);
	}

	public void negate() {
		x = -x;
		y = -y;
		z = -z;
		w = -w;
	}

	public boolean equals(final Vec4 other) {
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
		if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z)) {
			return false;
		}
		if (Float.floatToIntBits(w) != Float.floatToIntBits(other.w)) {
			return false;
		}

		return true;
	}

	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		return new StringBuilder().append("(").append(df.format(x)).append(", ").append(df.format(y)).append(", ")
				.append(df.format(z)).append(", ").append(df.format(w)).append(")").toString();
	}

	public FloatBuffer getBuffer() {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(3);

		buffer.put(x).put(y).put(z).put(w);
		buffer.flip();

		return buffer;
	}

}
