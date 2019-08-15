package com.max.harrax.graphics;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glBufferData;

import java.nio.IntBuffer;

public class IndexBuffer {
	
	private final int id;
	private int count;
	
	public IndexBuffer(int[] indices, int count) {
		this.count = count;
		
		id = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public int getCount() {
		return count;
	}

	public void bind() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
	}

	public void unbind() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public void dispose() {
		glDeleteBuffers(id);
	}


}
