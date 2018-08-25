package maths;

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
	

}
