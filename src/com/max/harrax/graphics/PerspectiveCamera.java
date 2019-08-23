package com.max.harrax.graphics;

import com.max.harrax.maths.Mat4;

public class PerspectiveCamera extends Camera{

	public PerspectiveCamera(float fov, float aspect) {
		super(Mat4.perspective(fov, aspect, 0.1f, 100f), Mat4.IDENTITY);
	}

	@Override
	protected void recalculateViewProjectionMatrix() {
		Mat4 transform = Mat4.translation(position);
		
		viewMatrix = transform.inverse();
		viewProjectionMatrix = projectionMatrix.mult(viewMatrix);
	}

}
