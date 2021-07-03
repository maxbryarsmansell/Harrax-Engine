package com.max.harrax.graphics.buffer;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class VertexArray {

    private final int id;

    private ArrayList<VertexBuffer> vertexBuffers;

    public VertexArray() {
        vertexBuffers = new ArrayList<VertexBuffer>();
        id = glGenVertexArrays();
    }

    public void addVertexBuffer(VertexBuffer vertexBuffer) {

        assert vertexBuffer.getLayout() == null : "Vertex buffer has no layout!";

        glBindVertexArray(id);
        vertexBuffer.bind();

        int index = 0;
        BufferLayout layout = vertexBuffer.getLayout();

        for (BufferElement element : layout) {
            glEnableVertexAttribArray(index);
            glVertexAttribPointer(index, element.getComponentCount(), element.getType().openGLType(), element.isNormalized(), layout.getStride(), element.getOffset());
            index++;
        }

        vertexBuffer.unbind();
        glBindVertexArray(0);

        vertexBuffers.add(vertexBuffer);
    }

    public void bind() {
        glBindVertexArray(id);
    }

    public void unbind() {
        glBindVertexArray(0);
    }

    public void dispose() {
        glDeleteVertexArrays(id);
    }
}
