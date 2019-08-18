package com.max.harrax.maths;

import java.nio.FloatBuffer;
import java.text.DecimalFormat;

public class Mat4 {

	
	public static final Mat4 ZERO = new Mat4();
	public static final Mat4 IDENTITY = new Mat4(1.0f);
	
	/*
	 * Elements are represented by a float array in column major form.
	 */
	
	public final float[] elements = new float[16];

	public Mat4(final float diagonal) {
		this.elements[0 + 0 * 4] = diagonal;
		this.elements[1 + 1 * 4] = diagonal;
		this.elements[2 + 2 * 4] = diagonal;
		this.elements[3 + 3 * 4] = diagonal;
	}
	
	public Mat4() {
		this(1.0f);
	}

	public Mat4(final float[] elements) {
		System.arraycopy(elements, 0, this.elements, 0, 16);
	}
	
	public Mat4(final Mat4 other) {
		System.arraycopy(other.elements, 0, this.elements, 0, 16);
	}

	public Mat4 add(final float scalar) {
		float[] newElements = new float[16];
		for (int i = 0; i < this.elements.length; i++) {
			newElements[i] = this.elements[i] + scalar;
		}
		return new Mat4(newElements);
	}

	public Mat4 sub(final float scalar) {
		float[] newElements = new float[16];
		for (int i = 0; i < this.elements.length; i++) {
			newElements[i] = this.elements[i] - scalar;
		}
		return new Mat4(newElements);
	}

	public Mat4 mult(final float scalar) {
		float[] newElements = new float[16];
		for (int i = 0; i < this.elements.length; i++) {
			newElements[i] = this.elements[i] * scalar;
		}
		return new Mat4(newElements);
	}
	
	public Vec4 mult(final Vec4 other) {
		return other.mult(this);
	}

	public Mat4 add(final Mat4 other) {
		float[] newElements = new float[16];
		for (int i = 0; i < this.elements.length; i++) {
			newElements[i] = this.elements[i] + other.elements[i];
		}
		return new Mat4(newElements);
	}


	public Mat4 sub(final Mat4 other) {
		float[] newElements = new float[16];
		for (int i = 0; i < this.elements.length; i++) {
			newElements[i] = this.elements[i] - other.elements[i];
		}
		return new Mat4(newElements);
	}

	public Mat4 mult(final Mat4 other) {
		float[] newElements = new float[16];
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				float sum = 0.0f;
				for (int e = 0; e < 4; e++) {
					sum += elements[e + row * 4] * other.elements[col + e * 4];
				}
				newElements[col + row * 4] = sum;
			}
		}
		return new Mat4(newElements);
	}
	
	public Mat4 transpose() {
		float[] newElements = new float[16];
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				newElements[row + col * 4] = elements[col + row * 4];
			}
		}
		return new Mat4(newElements);
	}
	
	public Mat4 inverse() {
		float[] newElements = new float[16];

		newElements[0] = elements[5] * elements[10] * elements[15] -
			elements[5] * elements[11] * elements[14] -
			elements[9] * elements[6] * elements[15] +
			elements[9] * elements[7] * elements[14] +
			elements[13] * elements[6] * elements[11] -
			elements[13] * elements[7] * elements[10];

		newElements[4] = -elements[4] * elements[10] * elements[15] +
			elements[4] * elements[11] * elements[14] +
			elements[8] * elements[6] * elements[15] -
			elements[8] * elements[7] * elements[14] -
			elements[12] * elements[6] * elements[11] +
			elements[12] * elements[7] * elements[10];

		newElements[8] = elements[4] * elements[9] * elements[15] -
			elements[4] * elements[11] * elements[13] -
			elements[8] * elements[5] * elements[15] +
			elements[8] * elements[7] * elements[13] +
			elements[12] * elements[5] * elements[11] -
			elements[12] * elements[7] * elements[9];

		newElements[12] = -elements[4] * elements[9] * elements[14] +
			elements[4] * elements[10] * elements[13] +
			elements[8] * elements[5] * elements[14] -
			elements[8] * elements[6] * elements[13] -
			elements[12] * elements[5] * elements[10] +
			elements[12] * elements[6] * elements[9];

		newElements[1] = -elements[1] * elements[10] * elements[15] +
			elements[1] * elements[11] * elements[14] +
			elements[9] * elements[2] * elements[15] -
			elements[9] * elements[3] * elements[14] -
			elements[13] * elements[2] * elements[11] +
			elements[13] * elements[3] * elements[10];

		newElements[5] = elements[0] * elements[10] * elements[15] -
			elements[0] * elements[11] * elements[14] -
			elements[8] * elements[2] * elements[15] +
			elements[8] * elements[3] * elements[14] +
			elements[12] * elements[2] * elements[11] -
			elements[12] * elements[3] * elements[10];

		newElements[9] = -elements[0] * elements[9] * elements[15] +
			elements[0] * elements[11] * elements[13] +
			elements[8] * elements[1] * elements[15] -
			elements[8] * elements[3] * elements[13] -
			elements[12] * elements[1] * elements[11] +
			elements[12] * elements[3] * elements[9];

		newElements[13] = elements[0] * elements[9] * elements[14] -
			elements[0] * elements[10] * elements[13] -
			elements[8] * elements[1] * elements[14] +
			elements[8] * elements[2] * elements[13] +
			elements[12] * elements[1] * elements[10] -
			elements[12] * elements[2] * elements[9];

		newElements[2] = elements[1] * elements[6] * elements[15] -
			elements[1] * elements[7] * elements[14] -
			elements[5] * elements[2] * elements[15] +
			elements[5] * elements[3] * elements[14] +
			elements[13] * elements[2] * elements[7] -
			elements[13] * elements[3] * elements[6];

		newElements[6] = -elements[0] * elements[6] * elements[15] +
			elements[0] * elements[7] * elements[14] +
			elements[4] * elements[2] * elements[15] -
			elements[4] * elements[3] * elements[14] -
			elements[12] * elements[2] * elements[7] +
			elements[12] * elements[3] * elements[6];

		newElements[10] = elements[0] * elements[5] * elements[15] -
			elements[0] * elements[7] * elements[13] -
			elements[4] * elements[1] * elements[15] +
			elements[4] * elements[3] * elements[13] +
			elements[12] * elements[1] * elements[7] -
			elements[12] * elements[3] * elements[5];

		newElements[14] = -elements[0] * elements[5] * elements[14] +
			elements[0] * elements[6] * elements[13] +
			elements[4] * elements[1] * elements[14] -
			elements[4] * elements[2] * elements[13] -
			elements[12] * elements[1] * elements[6] +
			elements[12] * elements[2] * elements[5];

		newElements[3] = -elements[1] * elements[6] * elements[11] +
			elements[1] * elements[7] * elements[10] +
			elements[5] * elements[2] * elements[11] -
			elements[5] * elements[3] * elements[10] -
			elements[9] * elements[2] * elements[7] +
			elements[9] * elements[3] * elements[6];

		newElements[7] = elements[0] * elements[6] * elements[11] -
			elements[0] * elements[7] * elements[10] -
			elements[4] * elements[2] * elements[11] +
			elements[4] * elements[3] * elements[10] +
			elements[8] * elements[2] * elements[7] -
			elements[8] * elements[3] * elements[6];

		newElements[11] = -elements[0] * elements[5] * elements[11] +
			elements[0] * elements[7] * elements[9] +
			elements[4] * elements[1] * elements[11] -
			elements[4] * elements[3] * elements[9] -
			elements[8] * elements[1] * elements[7] +
			elements[8] * elements[3] * elements[5];

		newElements[15] = elements[0] * elements[5] * elements[10] -
			elements[0] * elements[6] * elements[9] -
			elements[4] * elements[1] * elements[10] +
			elements[4] * elements[2] * elements[9] +
			elements[8] * elements[1] * elements[6] -
			elements[8] * elements[2] * elements[5];

		float determinant = elements[0] * newElements[0] + elements[1] * newElements[4] + elements[2] * newElements[8] + elements[3] * newElements[12];
		determinant = 1.0f / determinant;

		for (int i = 0; i < 4 * 4; i++) {
			newElements[i] = newElements[i] * determinant;
		}
		
		return new Mat4(newElements);
	}

	public static Mat4 scale(float scale) {
		Mat4 result = new Mat4();
		result.elements[0 + 0 * 4] = scale;
		result.elements[1 + 1 * 4] = scale;
		result.elements[2 + 2 * 4] = scale;
		return result;

	}
	
	public static Mat4 translation(Vec3 translation) {
		Mat4 result = new Mat4();
		result.elements[0 + 3 * 4] = translation.x;
		result.elements[1 + 3 * 4] = translation.y;
		result.elements[2 + 3 * 4] = translation.z;
		return result;
	}

	public static Mat4 xAxisRotation(float angle) {
		Mat4 result = new Mat4();
		result.elements[1 + 1 * 4] = (float) Math.cos(angle);
		result.elements[2 + 1 * 4] = (float) Math.sin(angle);
		result.elements[1 + 2 * 4] = (float) -Math.sin(angle);
		result.elements[2 + 2 * 4] = (float) Math.cos(angle);
		return result;
	}

	public static Mat4 yAxisRotation(float angle) {
		Mat4 result = new Mat4();
		result.elements[0 + 0 * 4] = (float) Math.cos(angle);
		result.elements[2 + 0 * 4] = (float) Math.sin(angle);
		result.elements[0 + 2 * 4] = (float) -Math.sin(angle);
		result.elements[2 + 2 * 4] = (float) Math.cos(angle);
		return result;
	}

	public static Mat4 zAxisRotation(float angle) {
		Mat4 result = new Mat4();
		result.elements[0 + 0 * 4] = (float) Math.cos(angle);
		result.elements[1 + 0 * 4] = (float) Math.sin(angle);
		result.elements[0 + 1 * 4] = (float) -Math.sin(angle);
		result.elements[1 + 1 * 4] = (float) Math.cos(angle);
		return result;
	}

	public static Mat4 orthographic(float left, float right, float bottom, float top, float near, float far) {
		Mat4 result = new Mat4();
		result.elements[0 + 0 * 4] = 2.0f / (right - left);
		result.elements[1 + 1 * 4] = 2.0f / (top - bottom);
		result.elements[2 + 2 * 4] = -2.0f / (far - near);
		result.elements[0 + 3 * 4] = -(right + left) / (right - left);
		result.elements[1 + 3 * 4] = -(top + bottom) / (top - bottom);
		result.elements[2 + 3 * 4] = -(far + near) / (far - near);
		return result;
	}

	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		StringBuilder result = new StringBuilder();
		for (int row = 0; row < 4; row++) {
			result.append("(");
			for (int col = 0; col < 4; col++) {
				result.append(df.format(elements[row + col * 4]));
				if (col < 3) {
					result.append(", ");
				}
			}
			result.append(")\n");
		}
		return result.toString();
	}

	public void getBuffer(FloatBuffer buffer) {
		buffer.put(elements[0]).put(elements[1]).put(elements[2]).put(elements[3]);
		buffer.put(elements[4]).put(elements[5]).put(elements[6]).put(elements[7]);
		buffer.put(elements[8]).put(elements[9]).put(elements[10]).put(elements[11]);
		buffer.put(elements[12]).put(elements[13]).put(elements[14]).put(elements[15]);
		buffer.flip();
	}

}
