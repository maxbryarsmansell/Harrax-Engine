package com.max.harrax.graphics;

import static org.lwjgl.opengl.GL40.*;

import com.max.harrax.graphics.buffer.VertexArray;
import com.max.harrax.maths.Mat4;
import com.max.harrax.maths.Vec3;

public class Renderer {

	private static Mat4 viewProjectionMatrix;
	private static Vec3 cameraPosition;
	private static Light sceneLight;

	public static void init() {

		glEnable(GL_DEPTH_TEST);
	}

	public static void beginScene(Camera camera, Light light) {
		Renderer.viewProjectionMatrix = camera.getViewProjectionMatrix();
		Renderer.cameraPosition = camera.getPosition();
		Renderer.sceneLight = light;
	}

	private static void submit(Shader shader, VertexArray vertexArray, Mat4 transform) {
		shader.bind();

		shader.setUniformMatrix4fv("u_ViewProjection", viewProjectionMatrix);
		shader.setUniformMatrix4fv("u_Transform", transform);

		vertexArray.bind();

		//glDrawElements(GL_TRIANGLES, vertexArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT, 0);
		
		glDrawArrays(GL_TRIANGLES, 0, 36);

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
		
		// Light
		shader.setUniform3fv("u_Light.position", sceneLight.getPosition());
		shader.setUniform3fv("u_Light.ambient", sceneLight.getAmbient());
		shader.setUniform3fv("u_Light.diffuse", sceneLight.getDiffuse());
		shader.setUniform3fv("u_Light.specular", sceneLight.getSpecular());

		vertexArray.bind();

		//glDrawElements(GL_TRIANGLES, vertexArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT, 0);
		
		glDrawArrays(GL_TRIANGLES, 0, 36);

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
