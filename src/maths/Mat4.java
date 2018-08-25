package maths;

import java.nio.FloatBuffer;
import java.text.DecimalFormat;

public class Mat4 {

	/*
	 * Elements are represented by a 1D float array of size 16. The default values
	 * in the array are zero (the zero matrix).
	 */

	public float[] elements = new float[16];

	/*
	 * The default constructor. The default matrix is the identity matrix.
	 */

	public Mat4() {
		this.elements[0 + 0 * 4] = 1.0f;
		this.elements[1 + 1 * 4] = 1.0f;
		this.elements[2 + 2 * 4] = 1.0f;
		this.elements[3 + 3 * 4] = 1.0f;
	}

	/*
	 * Constructor to initialise the matrix with given elements.
	 */

	public Mat4(float[] elements) {
		System.arraycopy(elements, 0, this.elements, 0, 16);
	}
	
	/*
	 * Copy constructor.
	 */
	
	public Mat4(Mat4 other) {
		this.elements = other.elements.clone();
	}

	/*
	 * Add scalar to matrix.
	 */

	public Mat4 Add(float value) {
		for (int i = 0; i < this.elements.length; i++) {
			this.elements[i] += value;
		}
		return this;
	}

	/*
	 * Subtract scalar from matrix.
	 */

	public Mat4 Subtract(float value) {
		for (int i = 0; i < this.elements.length; i++) {
			this.elements[i] -= value;
		}
		return this;
	}

	/*
	 * Multiply matrix by scalar.
	 */

	public Mat4 Multiply(float value) {
		for (int i = 0; i < this.elements.length; i++) {
			this.elements[i] *= value;
		}
		return this;
	}

	/*
	 * Add given vector to the matrix.
	 */

	public Mat4 Add(Vec4 other) {
		for (int i = 0; i < this.elements.length; i += 4) {
			this.elements[i + 0] += other.x;
			this.elements[i + 1] += other.y;
			this.elements[i + 2] += other.z;
			this.elements[i + 3] += other.w;
		}
		return this;
	}

	/*
	 * Subtract given vector from the matrix.
	 */

	public Mat4 Subtract(Vec4 other) {
		for (int i = 0; i < this.elements.length; i += 4) {
			this.elements[i + 0] -= other.x;
			this.elements[i + 1] -= other.y;
			this.elements[i + 2] -= other.z;
			this.elements[i + 3] -= other.w;
		}
		return this;
	}

	/*
	 * Add another matrix to the matrix.
	 */

	public Mat4 Add(Mat4 other) {
		for (int i = 0; i < this.elements.length; i++) {
			elements[i] += other.elements[i];
		}
		return this;
	}

	/*
	 * Subtract another matrix from the matrix.
	 */

	public Mat4 Subtract(Mat4 other) {
		for (int i = 0; i < this.elements.length; i++) {
			elements[i] -= other.elements[i];
		}
		return this;
	}

	/*
	 * Multiply another matrix with the matrix.
	 */

	public Mat4 Multiply(Mat4 other) {
		float[] data = new float[16];
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				float sum = 0.0f;
				for (int e = 0; e < 4; e++) {
					sum += elements[e + row * 4] * other.elements[col + e * 4];
				}
				data[col + row * 4] = sum;
			}
		}
		System.arraycopy(data, 0, elements, 0, 16);
		return this;
	}

	/*
	 * Return a new scale matrix with a given scale factor.
	 */

	public static Mat4 Scale(float scale) {
		Mat4 result = new Mat4();
		result.elements[0 + 0 * 4] = scale;
		result.elements[1 + 1 * 4] = scale;
		result.elements[2 + 2 * 4] = scale;
		return result;

	}

	/*
	 * Return a new translation matrix with a given translation.
	 */

	public static Mat4 Translation(Vec3 translation) {
		Mat4 result = new Mat4();
		result.elements[0 + 3 * 4] = translation.x;
		result.elements[1 + 3 * 4] = translation.y;
		result.elements[2 + 3 * 4] = translation.z;
		return result;

	}
	
	/*
	 * Return a new translation matrix with a given translation.
	 */

	public static Mat4 Translation(Vec2 translation) {
		Mat4 result = new Mat4();
		result.elements[0 + 3 * 4] = translation.x;
		result.elements[1 + 3 * 4] = translation.y;
		result.elements[2 + 3 * 4] = 0.0f;
		return result;

	}

	/*
	 * Return a new x-axis rotation matrix with a given angle of rotation.
	 */

	public static Mat4 xAxisRotation(float angle) {
		Mat4 result = new Mat4();
		result.elements[1 + 1 * 4] = (float) Math.cos(angle);
		result.elements[2 + 1 * 4] = (float) Math.sin(angle);
		result.elements[1 + 2 * 4] = (float) -Math.sin(angle);
		result.elements[2 + 2 * 4] = (float) Math.cos(angle);
		return result;

	}

	/*
	 * Return a new y-axis rotation matrix with a given angle of rotation.
	 */

	public static Mat4 yAxisRotation(float angle) {
		Mat4 result = new Mat4();
		result.elements[0 + 0 * 4] = (float) Math.cos(angle);
		result.elements[2 + 0 * 4] = (float) Math.sin(angle);
		result.elements[0 + 2 * 4] = (float) -Math.sin(angle);
		result.elements[2 + 2 * 4] = (float) Math.cos(angle);
		return result;

	}

	/*
	 * Return a new z-axis rotation matrix with a given angle of rotation.
	 */

	public static Mat4 zAxisRotation(float angle) {
		Mat4 result = new Mat4();
		result.elements[0 + 0 * 4] = (float) Math.cos(angle);
		result.elements[1 + 0 * 4] = (float) Math.sin(angle);
		result.elements[0 + 1 * 4] = (float) -Math.sin(angle);
		result.elements[1 + 1 * 4] = (float) Math.cos(angle);
		return result;

	}

	/*
	 * Return a new orthographic projection matrix with given bounds.
	 */

	public static Mat4 Orthographic(float left, float right, float bottom, float top, float near, float far) {
		Mat4 result = new Mat4();
		result.elements[0 + 0 * 4] = 2.0f / (right - left);
		result.elements[1 + 1 * 4] = 2.0f / (top - bottom);
		result.elements[2 + 2 * 4] = -2.0f / (far - near);
		result.elements[0 + 3 * 4] = -(right + left) / (right - left);
		result.elements[1 + 3 * 4] = -(top + bottom) / (top - bottom);
		result.elements[2 + 3 * 4] = -(far + near) / (far - near);
		return result;
	}

	/*
	 * Return a string version of the matrix for printing.
	 */

	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		String result = "";
		for (int row = 0; row < 4; row++) {
			result += "(";
			for (int col = 0; col < 4; col++) {
				result += df.format(elements[row + col * 4]);
				if (col < 3) {
					result += ", ";
				}
			}
			result += ")\n";
		}
		return result;
	}
	
	/*
	 * Function for converting data into buffer
	 */

	public void toBuffer(FloatBuffer buffer) {
		buffer.put(elements[0]).put(elements[1]).put(elements[2]).put(elements[3]);
		buffer.put(elements[4]).put(elements[5]).put(elements[6]).put(elements[7]);
		buffer.put(elements[8]).put(elements[9]).put(elements[10]).put(elements[11]);
		buffer.put(elements[12]).put(elements[13]).put(elements[14]).put(elements[15]);
		buffer.flip();
	}

}
