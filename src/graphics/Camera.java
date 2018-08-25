package graphics;

import maths.Mat4;
import maths.Vec2;

public class Camera {

	/*
	 * Attributes for
	 * Projection transformation
	 * Position of the camera
	 * Origin of the camera
	 * Rotation of the camera
	 * Zoom amount of the camera
	 */
	
	private Mat4 projectionMatrix;
	private Vec2 position;
	private Vec2 origin;
	private float rotation;
	private float zoom;
	
	/*
	 * Camera constructor that creates a camera with a given size
	 */
	
	public Camera(Vec2 size) {
		this.position = new Vec2(0, 0);
		this.origin = new Vec2(0, 0);
		this.zoom = 1f;
		this.rotation = 0f;
		this.projectionMatrix = Mat4.Orthographic(-size.x / 2f, size.x / 2f, -size.y / 2f, size.y / 2f, 1, -1);
	}
	
	/*
	 * Camera constructor that creates a camera with a given size and projection
	 */
	
	public Camera(Vec2 size, Mat4 projection) {
		this.position = new Vec2(0, 0);
		this.origin = new Vec2(0, 0);
		this.zoom = 1f;
		this.rotation = 0f;
		this.projectionMatrix = projection;
	}
	
	/*
	 * Camera constructor that creates a camera with a given size
	 */
	
	public Camera(float width, float height) {
		this(new Vec2(width, height));
	}
	
	/*
	 * Getters and setters
	 */
	
	public Vec2 getOrigin() {
		return origin;
	}
	
	public void setOrigin(Vec2 origin) {
		this.origin = origin;
	}
	
	public Vec2 getPosition() {
		return position;
	}

	public void setPosition(Vec2 position) {
		this.position = position;
	}
	
	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public float getZoom() {
		return zoom;
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}

	public Mat4 getProjectionMatrix() {
		return new Mat4(projectionMatrix);
	}

	public Mat4 getViewMatrix() {
		return Mat4.Translation(new Vec2(position).Negate())
				.Multiply(Mat4.Scale(zoom))
				.Multiply(Mat4.zAxisRotation(rotation))
				.Multiply(Mat4.Translation(origin));
	}
	
}
