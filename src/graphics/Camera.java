package graphics;

import game.Game;
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
	
	public Camera() {
		this.position = new Vec2(0, 0);
		this.origin = new Vec2(0, 0);
		this.zoom = 1f;
		this.rotation = 0f;
		this.projectionMatrix = Mat4.Orthographic(-0.5f * Game.VIRTUAL_WIDTH, 0.5f * Game.VIRTUAL_WIDTH, -0.5f * Game.VIRTUAL_HEIGHT, 0.5f * Game.VIRTUAL_HEIGHT, 1, -1);
	}
	
	/*
	 * Camera constructor that creates a camera with a given size
	 */
	
	public Camera(float width, float height) {
		this.position = new Vec2(0, 0);
		this.origin = new Vec2(0, 0);
		this.zoom = 1f;
		this.rotation = 0f;
		this.projectionMatrix = Mat4.Orthographic(0, width,  0, height, 1, -1);
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
