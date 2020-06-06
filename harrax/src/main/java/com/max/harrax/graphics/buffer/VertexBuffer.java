package com.max.harrax.graphics.buffer;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL15.*;

public class VertexBuffer {
    private final int id;
    private BufferLayout layout;
    private int bufferSize;

    /*
     * public VertexBuffer(FloatBuffer vertices) { id = glGenBuffers(); glBindBuffer(GL_ARRAY_BUFFER, id);
     * glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW); glBindBuffer(GL_ARRAY_BUFFER, 0); }
     */

    public VertexBuffer(BufferLayout layout, int maxVertices) {
        id = glGenBuffers();
        this.layout = layout;
        this.bufferSize = layout.getStride() * maxVertices;

        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, bufferSize, GL_DYNAMIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public BufferLayout getLayout() {
        return layout;
    }

    public ByteBuffer mapBuffer(ByteBuffer oldBuffer) {
        glBindBuffer(GL_ARRAY_BUFFER, id);
        ByteBuffer newBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, bufferSize, oldBuffer);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        return newBuffer;
    }

    public void unmapBuffer() {
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glUnmapBuffer(GL_ARRAY_BUFFER);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id);
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void dispose() {
        glDeleteBuffers(id);
    }
}
