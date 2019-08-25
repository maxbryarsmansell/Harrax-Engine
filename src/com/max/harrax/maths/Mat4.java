package com.max.harrax.maths;

import java.nio.FloatBuffer;
import java.text.DecimalFormat;

import org.lwjgl.system.MemoryUtil;

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
	
	public Mat4(Vec4 col1, Vec4 col2, Vec4 col3, Vec4 col4) {
		this.elements[0] = col1.x; this.elements[4] = col2.x; this.elements[8] = col3.x; this.elements[12] = col4.x;
		this.elements[1] = col1.y; this.elements[5] = col2.y; this.elements[9] = col3.y; this.elements[13] = col4.y;
		this.elements[2] = col1.z; this.elements[6] = col2.z; this.elements[10] = col3.z; this.elements[14] = col4.z;
		this.elements[3] = col1.w; this.elements[7] = col2.w; this.elements[11] = col3.w; this.elements[15] = col4.w;
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
	
	public Vec4 mult(final Vec4 other) {
		return other.mult(this);
	}

	public Mat4 mult(final Mat4 right) {
		float[] newElements = new float[16];
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				float sum = 0.0f;
				for (int e = 0; e < 4; e++) {
					sum += elements[row + e * 4] * right.elements[e + col * 4];
				}
				newElements[row + col * 4] = sum;
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
		float[] inv = new float[16];

	    inv[0] = this.elements[5]  * this.elements[10] * this.elements[15] - 
	             this.elements[5]  * this.elements[11] * this.elements[14] - 
	             this.elements[9]  * this.elements[6]  * this.elements[15] + 
	             this.elements[9]  * this.elements[7]  * this.elements[14] +
	             this.elements[13] * this.elements[6]  * this.elements[11] - 
	             this.elements[13] * this.elements[7]  * this.elements[10];

	    inv[4] = -this.elements[4]  * this.elements[10] * this.elements[15] + 
	              this.elements[4]  * this.elements[11] * this.elements[14] + 
	              this.elements[8]  * this.elements[6]  * this.elements[15] - 
	              this.elements[8]  * this.elements[7]  * this.elements[14] - 
	              this.elements[12] * this.elements[6]  * this.elements[11] + 
	              this.elements[12] * this.elements[7]  * this.elements[10];

	    inv[8] = this.elements[4]  * this.elements[9] * this.elements[15] - 
	             this.elements[4]  * this.elements[11] * this.elements[13] - 
	             this.elements[8]  * this.elements[5] * this.elements[15] + 
	             this.elements[8]  * this.elements[7] * this.elements[13] + 
	             this.elements[12] * this.elements[5] * this.elements[11] - 
	             this.elements[12] * this.elements[7] * this.elements[9];

	    inv[12] = -this.elements[4]  * this.elements[9] * this.elements[14] + 
	               this.elements[4]  * this.elements[10] * this.elements[13] +
	               this.elements[8]  * this.elements[5] * this.elements[14] - 
	               this.elements[8]  * this.elements[6] * this.elements[13] - 
	               this.elements[12] * this.elements[5] * this.elements[10] + 
	               this.elements[12] * this.elements[6] * this.elements[9];

	    inv[1] = -this.elements[1]  * this.elements[10] * this.elements[15] + 
	              this.elements[1]  * this.elements[11] * this.elements[14] + 
	              this.elements[9]  * this.elements[2] * this.elements[15] - 
	              this.elements[9]  * this.elements[3] * this.elements[14] - 
	              this.elements[13] * this.elements[2] * this.elements[11] + 
	              this.elements[13] * this.elements[3] * this.elements[10];

	    inv[5] = this.elements[0]  * this.elements[10] * this.elements[15] - 
	             this.elements[0]  * this.elements[11] * this.elements[14] - 
	             this.elements[8]  * this.elements[2] * this.elements[15] + 
	             this.elements[8]  * this.elements[3] * this.elements[14] + 
	             this.elements[12] * this.elements[2] * this.elements[11] - 
	             this.elements[12] * this.elements[3] * this.elements[10];

	    inv[9] = -this.elements[0]  * this.elements[9] * this.elements[15] + 
	              this.elements[0]  * this.elements[11] * this.elements[13] + 
	              this.elements[8]  * this.elements[1] * this.elements[15] - 
	              this.elements[8]  * this.elements[3] * this.elements[13] - 
	              this.elements[12] * this.elements[1] * this.elements[11] + 
	              this.elements[12] * this.elements[3] * this.elements[9];

	    inv[13] = this.elements[0]  * this.elements[9] * this.elements[14] - 
	              this.elements[0]  * this.elements[10] * this.elements[13] - 
	              this.elements[8]  * this.elements[1] * this.elements[14] + 
	              this.elements[8]  * this.elements[2] * this.elements[13] + 
	              this.elements[12] * this.elements[1] * this.elements[10] - 
	              this.elements[12] * this.elements[2] * this.elements[9];

	    inv[2] = this.elements[1]  * this.elements[6] * this.elements[15] - 
	             this.elements[1]  * this.elements[7] * this.elements[14] - 
	             this.elements[5]  * this.elements[2] * this.elements[15] + 
	             this.elements[5]  * this.elements[3] * this.elements[14] + 
	             this.elements[13] * this.elements[2] * this.elements[7] - 
	             this.elements[13] * this.elements[3] * this.elements[6];

	    inv[6] = -this.elements[0]  * this.elements[6] * this.elements[15] + 
	              this.elements[0]  * this.elements[7] * this.elements[14] + 
	              this.elements[4]  * this.elements[2] * this.elements[15] - 
	              this.elements[4]  * this.elements[3] * this.elements[14] - 
	              this.elements[12] * this.elements[2] * this.elements[7] + 
	              this.elements[12] * this.elements[3] * this.elements[6];

	    inv[10] = this.elements[0]  * this.elements[5] * this.elements[15] - 
	              this.elements[0]  * this.elements[7] * this.elements[13] - 
	              this.elements[4]  * this.elements[1] * this.elements[15] + 
	              this.elements[4]  * this.elements[3] * this.elements[13] + 
	              this.elements[12] * this.elements[1] * this.elements[7] - 
	              this.elements[12] * this.elements[3] * this.elements[5];

	    inv[14] = -this.elements[0]  * this.elements[5] * this.elements[14] + 
	               this.elements[0]  * this.elements[6] * this.elements[13] + 
	               this.elements[4]  * this.elements[1] * this.elements[14] - 
	               this.elements[4]  * this.elements[2] * this.elements[13] - 
	               this.elements[12] * this.elements[1] * this.elements[6] + 
	               this.elements[12] * this.elements[2] * this.elements[5];

	    inv[3] = -this.elements[1] * this.elements[6] * this.elements[11] + 
	              this.elements[1] * this.elements[7] * this.elements[10] + 
	              this.elements[5] * this.elements[2] * this.elements[11] - 
	              this.elements[5] * this.elements[3] * this.elements[10] - 
	              this.elements[9] * this.elements[2] * this.elements[7] + 
	              this.elements[9] * this.elements[3] * this.elements[6];

	    inv[7] = this.elements[0] * this.elements[6] * this.elements[11] - 
	             this.elements[0] * this.elements[7] * this.elements[10] - 
	             this.elements[4] * this.elements[2] * this.elements[11] + 
	             this.elements[4] * this.elements[3] * this.elements[10] + 
	             this.elements[8] * this.elements[2] * this.elements[7] - 
	             this.elements[8] * this.elements[3] * this.elements[6];

	    inv[11] = -this.elements[0] * this.elements[5] * this.elements[11] + 
	               this.elements[0] * this.elements[7] * this.elements[9] + 
	               this.elements[4] * this.elements[1] * this.elements[11] - 
	               this.elements[4] * this.elements[3] * this.elements[9] - 
	               this.elements[8] * this.elements[1] * this.elements[7] + 
	               this.elements[8] * this.elements[3] * this.elements[5];

	    inv[15] = this.elements[0] * this.elements[5] * this.elements[10] - 
	              this.elements[0] * this.elements[6] * this.elements[9] - 
	              this.elements[4] * this.elements[1] * this.elements[10] + 
	              this.elements[4] * this.elements[2] * this.elements[9] + 
	              this.elements[8] * this.elements[1] * this.elements[6] - 
	              this.elements[8] * this.elements[2] * this.elements[5];

	    float deterinant = this.elements[0] * inv[0] + this.elements[1] * inv[4] + this.elements[2] * inv[8] + this.elements[3] * inv[12];

	    for (int i = 0; i < 16; i++)
	    	inv[i] = inv[i] * (1f / deterinant);
		
		return new Mat4(inv);
	}

	public static Mat4 scale(float scale) {
		Mat4 result = new Mat4(1.0f);
		result.elements[0 + 0 * 4] = scale;
		result.elements[1 + 1 * 4] = scale;
		result.elements[2 + 2 * 4] = scale;
		return result;

	}
	
	public static Mat4 translation(Vec3 translation) {
		Mat4 result = new Mat4(1.0f);
		result.elements[0 + 3 * 4] = translation.x;
		result.elements[1 + 3 * 4] = translation.y;
		result.elements[2 + 3 * 4] = translation.z;
		return result;
	}

	public static Mat4 xAxisRotation(float angle) {
		Mat4 result = new Mat4(1.0f);
		result.elements[1 + 1 * 4] = (float) Math.cos(angle);
		result.elements[2 + 1 * 4] = (float) Math.sin(angle);
		result.elements[1 + 2 * 4] = (float) -Math.sin(angle);
		result.elements[2 + 2 * 4] = (float) Math.cos(angle);
		return result;
	}

	public static Mat4 yAxisRotation(float angle) {
		Mat4 result = new Mat4(1.0f);
		result.elements[0 + 0 * 4] = (float) Math.cos(angle);
		result.elements[2 + 0 * 4] = (float) Math.sin(angle);
		result.elements[0 + 2 * 4] = (float) -Math.sin(angle);
		result.elements[2 + 2 * 4] = (float) Math.cos(angle);
		return result;
	}

	public static Mat4 zAxisRotation(float angle) {
		Mat4 result = new Mat4(1.0f);
		result.elements[0 + 0 * 4] = (float) Math.cos(angle);
		result.elements[1 + 0 * 4] = (float) Math.sin(angle);
		result.elements[0 + 1 * 4] = (float) -Math.sin(angle);
		result.elements[1 + 1 * 4] = (float) Math.cos(angle);
		return result;
	}

	public static Mat4 orthographic(float left, float right, float bottom, float top, float near, float far) {
		Mat4 result = new Mat4(1.0f);
		result.elements[0 + 0 * 4] = 2.0f / (right - left);
		result.elements[1 + 1 * 4] = 2.0f / (top - bottom);
		result.elements[2 + 2 * 4] = -2.0f / (far - near);
		result.elements[0 + 3 * 4] = -(right + left) / (right - left);
		result.elements[1 + 3 * 4] = -(top + bottom) / (top - bottom);
		result.elements[2 + 3 * 4] = -(far + near) / (far - near);
		return result;
	}
	
	public static Mat4 perspective(float fov, float aspect, float near, float far) {
		Mat4 result = new Mat4(0.0f);
		float f = 1f / (float)Math.tan(0.5f * fov);
		result.elements[0 + 0 * 4] =  f / aspect;
		result.elements[1 + 1 * 4] =  f;
		result.elements[2 + 2 * 4] = (near + far) / (near - far);
		result.elements[2 + 3 * 4] = (2f * near * far) / (near - far);
		result.elements[3 + 2 * 4] = -1.0f;
		result.elements[3 + 3 * 4] = 0.0f;
		return result;
	}
	
	public static Mat4 lookAt(Vec3 from, Vec3 to, Vec3 wrldUp) {
		Vec3 zaxis = (from.sub(to)).norm();
		Vec3 xaxis = (wrldUp.cross(zaxis)).norm();
		Vec3 yaxis = (zaxis.cross(xaxis)).norm();
		
		Mat4 result = new Mat4(1.0f);
		result.elements[0 + 0 * 4] = xaxis.x;
		result.elements[1 + 0 * 4] = yaxis.x;
		result.elements[2 + 0 * 4] = zaxis.x;
		
		result.elements[0 + 1 * 4] = xaxis.y;
		result.elements[1 + 1 * 4] = yaxis.y;
		result.elements[2 + 1 * 4] = zaxis.y;
		
		result.elements[0 + 2 * 4] = xaxis.z;
		result.elements[1 + 2 * 4] = yaxis.z;
		result.elements[2 + 2 * 4] = zaxis.z;
		
		result.elements[0 + 3 * 4] = -(xaxis.dot(from));
		result.elements[1 + 3 * 4] = -(yaxis.dot(from));
		result.elements[2 + 3 * 4] = -(zaxis.dot(from));
		
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

	public void toBuffer(FloatBuffer buffer) {
		buffer.put(elements[0]).put(elements[1]).put(elements[2]).put(elements[3]);
		buffer.put(elements[4]).put(elements[5]).put(elements[6]).put(elements[7]);
		buffer.put(elements[8]).put(elements[9]).put(elements[10]).put(elements[11]);
		buffer.put(elements[12]).put(elements[13]).put(elements[14]).put(elements[15]);
		buffer.flip();
	}

}
