package com.max.harrax.graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public abstract class Camera {

    protected Vector3f position;

    protected Matrix4f projectionMatrix;
    protected Matrix4f viewMatrix;
    protected Matrix4f viewProjectionMatrix;

    protected Camera(Matrix4f projectionMatrix, Matrix4f viewMatrix) {
        this.position = new Vector3f();
        this.projectionMatrix = new Matrix4f(projectionMatrix);
        this.viewMatrix = new Matrix4f(viewMatrix);
        this.viewProjectionMatrix = new Matrix4f();

        projectionMatrix.mul(viewMatrix, this.viewProjectionMatrix);
    }

    protected abstract void recalculateViewProjectionMatrix();

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position.set(position);
        recalculateViewProjectionMatrix();
    }

    public void setPosition(Vector2f position) {
        this.position.set(position, 0f);
        recalculateViewProjectionMatrix();
    }

    public Matrix4f getViewProjectionMatrix() {
        return viewProjectionMatrix;
    }
}
