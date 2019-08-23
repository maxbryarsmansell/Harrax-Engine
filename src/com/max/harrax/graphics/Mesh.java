package com.max.harrax.graphics;

import static com.max.harrax.graphics.buffer.ShaderDataType.Float3;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryUtil;

import com.max.harrax.Disposable;
import com.max.harrax.graphics.buffer.BufferElement;
import com.max.harrax.graphics.buffer.BufferLayout;
import com.max.harrax.graphics.buffer.IndexBuffer;
import com.max.harrax.graphics.buffer.VertexArray;
import com.max.harrax.graphics.buffer.VertexBuffer;

public class Mesh implements Disposable {

	private VertexArray vertexArray;
	private VertexBuffer vertexBuffer;
	private IndexBuffer indexBuffer;

	private static final BufferLayout bufferLayout = new BufferLayout(
			new BufferElement(Float3, "a_Position"),
			new BufferElement(Float3, "a_Normal"));

	public Mesh(int[] indices, float[] vertices) {
		FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
		verticesBuffer.put(vertices).flip();

		vertexBuffer = new VertexBuffer(verticesBuffer);

		MemoryUtil.memFree(verticesBuffer);

		IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
		indicesBuffer.put(indices).flip();

		indexBuffer = new IndexBuffer(indicesBuffer);

		MemoryUtil.memFree(indicesBuffer);

		vertexArray = new VertexArray();

		vertexBuffer.setLayout(bufferLayout);

		vertexArray.addVertexBuffer(vertexBuffer);
		vertexArray.setIndexBuffer(indexBuffer);
	}

	@Override
	public void dispose() {
		vertexArray.dispose();
		vertexBuffer.dispose();
		indexBuffer.dispose();
	}
	
	public VertexArray getVertexArray() {
		return vertexArray;
	}

	public static Mesh quadMesh() {
		float[] vertices = new float[] {
				-1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 
				-1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 
				1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 
				1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};

		int[] indices = new int[] { 0, 1, 3, 3, 1, 2 };

		return new Mesh(indices, vertices);
	}
	
	public static Mesh cubeMesh() {
		float[] vertices = new float[] {
				-1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 
				-1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 
				1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 
				1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f,
				-1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 
				-1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 
				1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 
				1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 1.0f
				};

		int[] indices = new int[] {
				// front
				0, 1, 3, 3, 1, 2,
				// top
				4, 0, 7, 7, 0, 3,
				// bottom
				1, 5, 2, 2, 5, 6,
				// right
				3, 2, 7, 7, 2, 6,
				// left
				4, 5, 0, 0, 5, 1,
				// back
				4, 5, 7, 7, 5, 6
				};

		return new Mesh(indices, vertices);
	}

}
