package com.max.harrax.graphicsOLD;

import com.max.harrax.Application;
import com.max.harrax.maths.Mat4;
import com.max.harrax.maths.Vec2;
import com.max.harrax.maths.Vec4;

public class Camera {

	/*
	 * Attributes for Projection transformation Position of the camera Origin of the
	 * camera Rotation of the camera Zoom amount of the camera
	 */

	private Mat4 projectionMatrix;
	private Vec2 position;
	private Vec2 origin;
	private float rotation;
	private float zoom;
	private float frustumWidth, frustumHeight;

	/*
	 * Camera constructor that creates a camera with a given size
	 */

	public Camera(Vec2 size) {
		this.position = new Vec2(0, 0);
		this.origin = new Vec2(0, 0);
		this.zoom = 1f;
		this.rotation = 0f;
		this.frustumWidth = size.x;
		this.frustumHeight = size.y;
		this.projectionMatrix = Mat4.Orthographic(-size.x / 2f, size.x / 2f, -size.y / 2f, size.y / 2f, 1, -1);
	}

	/*
	 * Camera constructor that creates a camera with a given size
	 */

	public Camera(float width, float height) {
		this(new Vec2(width, height));
	}

	public Vec2 ScreenToWorldCoordinates(float mouseX, float mouseY) {
		int WIDTH = 1, HEIGHT = 1;
		
		Vec2 normalizedCoords = new Vec2((2 * mouseX) / WIDTH - 1, (-2 * mouseY) / HEIGHT + 1);

		Vec4 clipCoords = normalizedCoords.toVec4();

		Mat4 inverseProjection = new Mat4(projectionMatrix).Invert();
		Vec4 eyeCoords = clipCoords.Multiply(inverseProjection);

		Mat4 inverseView = new Mat4(getViewMatrix()).Invert();
		Vec4 worldCoords = eyeCoords.Multiply(inverseView);

		return worldCoords.toVec2();
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

	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
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

	public float getFrustumWidth() {
		return frustumWidth;
	}

	public float getFrustumHeight() {
		return frustumHeight;
	}

	public Mat4 getProjectionMatrix() {
		return new Mat4(projectionMatrix);
	}

	public Mat4 getViewMatrix() {
		return Mat4.Translation(new Vec2(position).Negate()).Multiply(Mat4.Scale(zoom))
				.Multiply(Mat4.zAxisRotation(rotation)).Multiply(Mat4.Translation(origin));
	}

}
