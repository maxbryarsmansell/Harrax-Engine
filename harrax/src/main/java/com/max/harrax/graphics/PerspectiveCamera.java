package com.max.harrax.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class PerspectiveCamera extends Camera {

    private Vector3f target;

    public PerspectiveCamera(float fov, float aspect) {
        super(new Matrix4f().perspective(fov, aspect, 0.1f, 1000f), new Matrix4f());
        
        target = new Vector3f(0f, 0f, 0f);
    }

    public void lookAt(Vector3f target) {
        this.target = target;
        recalculateViewProjectionMatrix();
    }

    @Override
    protected void recalculateViewProjectionMatrix() {

        viewMatrix = new Matrix4f().lookAt(position, target, new Vector3f(0f, 1f, 0f));
        viewProjectionMatrix = projectionMatrix.mul(viewMatrix);

    }

}
