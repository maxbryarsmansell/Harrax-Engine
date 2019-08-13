package com.max.harrax.maths;

public class Helper {
	
	/*
	 * Convert an angle in radians to degrees.
	 */
	
	public static float toDegrees(float angle) {
		return (float) (angle * (180 / Math.PI));
	}
	
	/*
	 * Convert an angle in degrees to radians.
	 */
	
	public static float toRadians(float angle) {
		return (float) (angle * (Math.PI / 180));
	}
	
	/*
	 * Linear interpolation between two float values
	 */
	
	public static float lerp(float goal, float current, float dt) {
		float difference = goal - current;
		if (difference > dt) {
			return current + dt;
		}
		if (difference < -dt) {
			return current - dt;
		}
		return goal;
	}
	

}
