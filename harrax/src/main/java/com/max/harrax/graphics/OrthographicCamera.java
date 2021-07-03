package com.max.harrax.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class OrthographicCamera extends Camera {

    private float scale;

    public OrthographicCamera(float left, float right, float bottom, float top) {
        super(new Matrix4f().ortho(left, right, bottom, top, -1.0f, 1.0f), new Matrix4f());

        this.scale = 1f;
    }

    public void set(float left, float right, float bottom, float top) {
        projectionMatrix.set(new Matrix4f().ortho(left, right, bottom, top, -1.0f, 1.0f));

        recalculateViewProjectionMatrix();
    }

    @Override
    protected void recalculateViewProjectionMatrix() {
        viewMatrix.set(new Matrix4f().translation(new Vector3f(position).negate()).mul(new Matrix4f().scale(scale)));
        projectionMatrix.mul(viewMatrix, viewProjectionMatrix);
    }

    // public void setRotation(float rotation) {
    // this.rotation = rotation;
    // recalculateViewProjectionMatrix();
    // }

    // public float getRotation() {
    // return rotation;
    // }

    public void setScale(float scale) {
        this.scale = scale;
        recalculateViewProjectionMatrix();
    }

    public float getScale() {
        return scale;
    }

}
