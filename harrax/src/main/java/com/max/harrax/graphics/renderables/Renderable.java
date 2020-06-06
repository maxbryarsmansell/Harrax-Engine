package com.max.harrax.graphics.renderables;

import com.max.harrax.graphics.Colour;
import com.max.harrax.graphics.buffer.VertexArray;
import com.max.harrax.maths.Mat4;

public class Renderable {

    private VertexArray vertexArray;
    private Mat4 transform;

    public Renderable(float x1, float y1, float x2, float y2, Colour colour) {
        this.vertexArray = new VertexArray();
    }
}
