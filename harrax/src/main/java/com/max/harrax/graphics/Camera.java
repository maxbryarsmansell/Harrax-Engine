package com.max.harrax.graphics;

import com.max.harrax.maths.Mat4;
import com.max.harrax.maths.Vec3;

public abstract class Camera {
    protected Vec3 position;

    protected Mat4 projectionMatrix;
    protected Mat4 viewMatrix;
    protected Mat4 viewProjectionMatrix;

    public Camera(Mat4 projectionMatrix, Mat4 viewMatrix) {
        this.position = new Vec3();
        this.projectionMatrix = projectionMatrix;
        this.viewMatrix = viewMatrix;
        init();
        recalculateViewProjectionMatrix();
    }

    protected abstract void init();

    protected abstract void recalculateViewProjectionMatrix();

    public Vec3 getPosition() {
        return position;
    }

    public void setPosition(Vec3 position) {
        this.position = position;
        recalculateViewProjectionMatrix();
    }

    public Mat4 getViewProjectionMatrix() {
        return viewProjectionMatrix;
    }
}
