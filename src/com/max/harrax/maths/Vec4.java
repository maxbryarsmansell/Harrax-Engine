package com.max.harrax.maths;

import java.nio.FloatBuffer;
import java.text.DecimalFormat;

public class Vec4 implements Cloneable{
	
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
	
	public Vec4(float x, float y, float z, float w) {			
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vec4(Vec4 other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
		this.w = other.w;
	}

	public Vec4 Add(Vec4 other) {		
		x += other.x;
		y += other.y;
		z += other.z;
		w += other.w;
		return this;
	}
	
	public Vec4 Subtract(Vec4 other) {			
		x -= other.x;
		y -= other.y;
		z -= other.z;
		w -= other.w;
		return this;
	}
	
	public Vec4 Multiply(Vec4 other) {			
		x *= other.x;
		y *= other.y;
		z *= other.z;
		w *= other.w;
		return this;
	}
	
	public Vec4 Divide(Vec4 other) {		
		x /= other.x;
		y /= other.y;
		z /= other.z;
		w /= other.w;
		return this;	
	}
	
	public Vec4 Add(float value) {			
		x += value;
		y += value;
		z += value;
		w += value;
		return this;
	}
	
	public Vec4 Subtract(float value) {			
		x -= value;
		y -= value;
		z -= value;
		w -= value;
		return this;
	}
	
	public Vec4 Multiply(float value) {			
		x *= value;
		y *= value;
		z *= value;
		w *= value;
		return this;
	}
	
	public Vec4 Divide(float value) { 		
		x /= value;
		y /= value;
		z /= value;
		w /= value;
		return this;
	}
	
	/*
	 * Multiply this vector by a given matrix.
	 */
	
	public Vec4 Multiply(Mat4 other) {		
		Vec4 temp = new Vec4(x, y, z, w);
		this.x = temp.x * other.elements[0] + temp.y * other.elements[4] + temp.z * other.elements[8] + temp.w * other.elements[12];
		this.y = temp.x * other.elements[1] + temp.y * other.elements[5] + temp.z * other.elements[9] + temp.w * other.elements[13];
		this.z = temp.x * other.elements[2] + temp.y * other.elements[6] + temp.z * other.elements[10] + temp.w * other.elements[14]; 
		this.w = temp.x * other.elements[3] + temp.y * other.elements[7] + temp.z * other.elements[11] + temp.w * other.elements[15]; 
		return this;
	}
	
	public float Distance(Vec4 other) { 				
		float a = x - other.x;					
		float b = y - other.y;						
		float c = z - other.z;							
		float d = w - other.w;								
		return (float) Math.sqrt(a * a + b * b + c * c + d * d);		
	}
	
	public float Magnitude() {								
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);		
	}
	
	public Vec4 Normalize() {
		float length = Magnitude();
		x /= length;
		y /= length;
		z /= length;
		w /= length;
		return this;		
	}
	
	public Vec4 Negate() {					
		x = -x;
		y = -y;
		z = -z;
		w = -w;
		return this;
	}
	
	public String toString() {												
		DecimalFormat df = new DecimalFormat("0.00");
		return ("(" + df.format(x) + ", " + df.format(y) + ", " + df.format(z) + ", " + df.format(w) +")\n");
	}
	
	/*
	 * Return a float buffer which represents the vector
	 */
	
	public void toBuffer(FloatBuffer buffer) {
        buffer.put(x).put(y).put(z).put(w);
        buffer.flip();
    }
	
	public Vec2 toVec2() {
        return new Vec2(x, y);
    }
	
	
	
}
