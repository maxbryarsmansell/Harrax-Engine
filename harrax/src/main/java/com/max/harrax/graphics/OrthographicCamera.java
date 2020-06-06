package com.max.harrax.graphics;

import com.max.harrax.maths.Mat4;

public class OrthographicCamera extends Camera {

    private float rotation;
    private float scale;

    public OrthographicCamera(float left, float right, float bottom, float top) {
        super(Mat4.orthographic(left, right, bottom, top, -1.0f, 1.0f), Mat4.IDENTITY);
    }

    @Override
    protected void init() {
        rotation = 0.0f;
        scale = 1.0f;
    }

    @Override
    protected void recalculateViewProjectionMatrix() {
        Mat4 transform = Mat4.translation(position).mult(Mat4.zAxisRotation(rotation).mult(Mat4.scale(scale)));

        viewMatrix = transform.inverse();
        viewProjectionMatrix = projectionMatrix.mult(viewMatrix);
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        recalculateViewProjectionMatrix();
    }

    public void setScale(float scale) {
        this.scale = scale;
        recalculateViewProjectionMatrix();
    }

    public float getScale() {
        return scale;
    }

}

