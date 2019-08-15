package com.max.harrax.graphics;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL40.*;

import static org.lwjgl.system.MemoryUtil.memAddress;

import java.nio.FloatBuffer;

public class VertexBuffer {

	private final int id;
	private BufferLayout layout;

	public VertexBuffer(float[] vertices, long count) {
		id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public BufferLayout getLayout() {
		return layout;
	}

	public void setLayout(BufferLayout layout) {
		this.layout = layout;
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
