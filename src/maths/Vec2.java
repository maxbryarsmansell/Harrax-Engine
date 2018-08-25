package maths;

import java.nio.FloatBuffer;
import java.text.DecimalFormat;

public class Vec2 {
	
	/*
	 * x and y components, represented as floats.
	 */
	
	public float x;						
	public float y;
	
	/*
	 * Default constructor.
	 * Values initialised to zero.
	 */
	
	public Vec2() {						
		this.x = 0.0f;
		this.y = 0.0f;
	}
	
	/*
	 * Constructor to initialise with given values.
	 */
	
	public Vec2(float x, float y) {			
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Copy constructor.
	 */
	
	public Vec2(Vec2 other) {
		this.x = other.x;
		this.y = other.y;
	}
	
	/*
	 * Add another vector to this. 
	 */

	public Vec2 Add(Vec2 other) {		
		x += other.x;
		y += other.y;
		return this;
	}
	
	/*
	 * Subtract another vector from this. 
	 */
	
	public Vec2 Subtract(Vec2 other) {			
		x -= other.x;
		y -= other.y;
		return this;
	}
	
	/*
	 * Multiply this vector by another one. 
	 */
	
	public Vec2 Multiply(Vec2 other) {			
		x *= other.x;
		y *= other.y;
		return this;
	}
	
	/*
	 * Divide this vector by another one. 
	 */
	
	public Vec2 Divide(Vec2 other) {		
		x /= other.x;
		y /= other.y;
		return this;	
	}
	
	/*
	 * Add a scalar to this.
	 */
	
	public Vec2 Add(float value) {			
		x += value;
		y += value;
		return this;
	}
	
	/*
	 * Subtract a scalar from this.
	 */

	
	public Vec2 Subtract(float value) {			
		x -= value;
		y -= value;
		return this;
	}
	
	/*
	 * Multiply this by a scalar.
	 */

	
	public Vec2 Multiply(float value) {			
		x *= value;
		y *= value;
		return this;
	}
	
	/*
	 * Divide this by a scalar.
	 */
	
	public Vec2 Divide(float value) { 		
		x /= value;
		y /= value;
		return this;
	}
	
	/*
	 * Calculate the distance between this and another vector.
	 */
	
	public float Distance(Vec2 other) { 				
		float a = x - other.x;						
		float b = y - other.y;							
		return (float) Math.sqrt(a * a + b * b);		
	}
	
	/*
	 * Calculate the magnitude of this vector.
	 */
	
	public float Magnitude() {							
		return (float) Math.sqrt(x * x + y * y);		
	}
	
	/*
	 * Normalize this vector.
	 */
	
	public Vec2 Normalize() {
		float length = Magnitude();
		x /= length;
		y /= length;
		return this;		
	}
	
	/*
	 * Negate this vector.
	 */
	
	public Vec2 Negate() {					
		x = -x;
		y = -y;
		return this;
	}
	
	/*
	 * Return a string version of the vector for printing.
	 */
	
	public String toString() {													
		DecimalFormat df = new DecimalFormat("0.00");
		return ("(" + df.format(x) + ", " + df.format(y) +")");
	}
	
	/*
	 * Return a float buffer which represents the vector
	 */
	
	public void toBuffer(FloatBuffer buffer) {
        buffer.put(x).put(y);
        buffer.flip();
    }
	
	public Vec4 toVec4() {
		return new Vec4(x, y, 1, 1);
	}
	
	
	
}
