package com.max.harrax.graphics;

import static org.lwjgl.opengl.GL40.*;

import java.util.ArrayList;

import com.max.harrax.maths.Mat4;

public class VertexArray {

	private final int id;
	
	private ArrayList<VertexBuffer> vertexBuffers;
	private IndexBuffer indexBuffer;

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
			glVertexAttribPointer(
					index,
					element.getComponentCount(),
					element.getType().openGLType(),
					element.isNormalized(),
					layout.getStride(),
					element.getOffset()
					);
			index++;
		}
		
		vertexBuffers.add(vertexBuffer);
	}
	
	public void setIndexBuffer(IndexBuffer indexBuffer) {
		glBindVertexArray(id);
		indexBuffer.bind();
		
		this.indexBuffer = indexBuffer;
	}
	
	public IndexBuffer getIndexBuffer() {
		return indexBuffer;
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
