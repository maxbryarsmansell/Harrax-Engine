package com.max.harrax.graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;

import com.max.harrax.graphics.buffer.VertexArray;
import com.max.harrax.maths.Mat4;
import com.max.harrax.maths.Vec3;

public class Renderer {

	private static Mat4 viewProjectionMatrix;
	private static Vec3 cameraPosition;

	public static void beginScene(Camera camera) {
		Renderer.viewProjectionMatrix = camera.getViewProjectionMatrix();
		Renderer.cameraPosition = camera.getPosition();
	}

	public static void submit(Shader shader, VertexArray vertexArray, Mat4 transform) {
		shader.bind();

		shader.setUniformMatrix4fv("u_ViewProjection", viewProjectionMatrix);
		shader.setUniformMatrix4fv("u_Transform", transform);

		vertexArray.bind();

		glDrawElements(GL_TRIANGLES, vertexArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT, 0);

		vertexArray.unbind();
		shader.unbind();
	}
	
	public static void submit(Shader shader, Material material, VertexArray vertexArray, Mat4 transform) {
		shader.bind();

		shader.setUniformMatrix4fv("u_ViewProjection", viewProjectionMatrix);
		shader.setUniformMatrix4fv("u_Transform", transform);
		shader.setUniform3fv("u_CameraPosition", cameraPosition);
		
		// Material
		shader.setUniform3fv("u_Material.ambient", material.getAmbient());
		shader.setUniform3fv("u_Material.diffuse", material.getDiffuse());
		shader.setUniform3fv("u_Material.specular", material.getSpecular());
		shader.setUniform1fv("u_Material.shininess", material.getShininess());

		vertexArray.bind();

		glDrawElements(GL_TRIANGLES, vertexArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT, 0);

		vertexArray.unbind();
		shader.unbind();
	}
	
	public static void submit(Shader shader, Mesh mesh, Mat4 transform) {
		submit(shader, mesh.getVertexArray(), transform);
	}
	
	public static void submit(Shader shader, Material material, Mesh mesh, Mat4 transform) {
		submit(shader, material, mesh.getVertexArray(), transform);
	}

	public static void endScene() {

	}

	public static void clear() {
		glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

}
