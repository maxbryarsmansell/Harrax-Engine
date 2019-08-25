package com.max.harrax.graphics;

import com.max.harrax.maths.Mat4;
import com.max.harrax.maths.Vec3;

public class PerspectiveCamera extends Camera{

	private Vec3 target;
	
	public PerspectiveCamera(float fov, float aspect) {
		super(Mat4.perspective(fov, aspect, 0.1f, 1000f), Mat4.IDENTITY);
	}
	
	@Override
	protected void init() {
		target = new Vec3();
	}
	

	public void lookAt(Vec3 target) {
		this.target = target;
		recalculateViewProjectionMatrix();
	}

	@Override
	protected void recalculateViewProjectionMatrix() {
		Vec3 up = new Vec3(0, 1, 0);
		Mat4 transform = Mat4.lookAt(position, target, up);
		
		
		viewMatrix = transform;
		viewProjectionMatrix = projectionMatrix.mult(viewMatrix);
	}

}
