package com.max.harrax.graphics.buffer;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;

import com.max.harrax.Disposable;

public class VertexBuffer implements Disposable {

	private final int id;
	private BufferLayout layout;

	public VertexBuffer(FloatBuffer vertices) {
		
		id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
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

	@Override
	public void dispose() {
		glDeleteBuffers(id);
	}

}
