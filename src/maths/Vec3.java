package maths;

import java.nio.FloatBuffer;
import java.text.DecimalFormat;

public class Vec3 {
	
	public float x;						
	public float y;					
	public float z;						
	
	public Vec3() {						
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
	}
	
	public Vec3(float x, float y, float z) {			
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec3(Vec3 other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}

	public Vec3 Add(Vec3 other) {		
		x += other.x;
		y += other.y;
		z += other.z;
		return this;
	}
	
	public Vec3 Subtract(Vec3 other) {			
		x -= other.x;
		y -= other.y;
		z -= other.z;
		return this;
	}
	
	public Vec3 Multiply(Vec3 other) {			
		x *= other.x;
		y *= other.y;
		z *= other.z;
		return this;
	}
	
	public Vec3 Divide(Vec3 other) {		
		x /= other.x;
		y /= other.y;
		z /= other.z;
		return this;	
	}
	
	public Vec3 Add(float value) {			
		x += value;
		y += value;
		z += value;
		return this;
	}
	
	public Vec3 Subtract(float value) {			
		x -= value;
		y -= value;
		z -= value;
		return this;
	}
	
	public Vec3 Multiply(float value) {			
		x *= value;
		y *= value;
		z *= value;
		return this;
	}
	
	public Vec3 Divide(float value) { 		
		x /= value;
		y /= value;
		z /= value;
		return this;
	}
	
	public float Distance(Vec3 other) { 				
		float a = x - other.x;							
		float b = y - other.y;							
		float c = z - other.z;								
		return (float) Math.sqrt(a * a + b * b + c * c);		
	}
	
	public float Magnitude() {								
		return (float) Math.sqrt(x * x + y * y + z * z);		
	}
	
	public Vec3 Normalize() {
		float length = Magnitude();
		x /= length;
		y /= length;
		z /= length;
		return this;		
	}
	
	public Vec3 Negate() {					
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	
	public String toString() {													
		DecimalFormat df = new DecimalFormat("0.00");
		return ("(" + df.format(x) + ", " + df.format(y) + ", " + df.format(z) + ")");
	}
	
	/*
	 * Return a float buffer which represents the vector
	 */
	
	public void toBuffer(FloatBuffer buffer) {
        buffer.put(x).put(y).put(z);
        buffer.flip();
    }
	
	
	
}
