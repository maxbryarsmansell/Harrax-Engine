package com.max.harrax.graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Map;

import org.lwjgl.BufferUtils;

import com.max.harrax.graphics.text.Font;
import com.max.harrax.graphics.text.Glyph;
import com.max.harrax.maths.Mat4;

public class Renderer {

	private static Mat4 viewProjectionMatrix;

	public static void beginScene(Mat4 viewProjectionMatrix) {
		Renderer.viewProjectionMatrix = viewProjectionMatrix;
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

	public static void submit(Shader shader, String text, Mat4 transform) {
		int[] indices = new int[] {0, 1, 2, 3, 4, 5};
		
		BufferLayout layout = new BufferLayout(
				new BufferElement(ShaderDataType.Float2, "a_Position"),
				new BufferElement(ShaderDataType.Float2, "a_Texcoord"),
				new BufferElement(ShaderDataType.Float4, "a_Colour")
				);
		
		Font font = Font.DEBUG_FONT;
		
		int fontWidth = font.getFontWidth();
		int fontHeight = font.getFontHeight();
		Map<Character, Glyph> glyphs = font.getGlyphs();
		
		Texture fontTexture = font.getTexture();

		float drawX = 0;
		float drawY = 0;

		for (int i = 0; i < text.length(); i++) {
			char character = text.charAt(i);

			if (character == '\n') {
				drawY -= fontHeight;
				drawX = 0;
				continue;
			}

			if (character == '\r') {
				continue;
			}

			Glyph glyph = glyphs.get(character);

			float x0 = drawX;
			float x1 = drawX + glyph.width;
			float y0 = drawY;
			float y1 = drawY + glyph.height;

			float u0 = (float) glyph.x / fontWidth;
			float u1 = (float) (glyph.x + glyph.width) / (float) fontWidth;
			float v0 = (float) glyph.y / fontTexture.getHeight();
			float v1 = (float) (glyph.y + glyph.height) / (float) fontHeight;
			
			float r = 1.0f;
			float g = 0.0f;
			float b = 0.0f;
			float a = 1.0f;
			
			//System.out.println(x0 + ", " + x1);

			FloatBuffer vertices = BufferUtils.createFloatBuffer(6 * 8);
			
			// Put the vertex data into the float buffer
			// There are 6 vertices per character
			vertices.put(x0).put(y0).put(u0).put(v0).put(r).put(g).put(b).put(a);
			vertices.put(x0).put(y1).put(u0).put(v1).put(r).put(g).put(b).put(a);
			vertices.put(x1).put(y1).put(u1).put(v1).put(r).put(g).put(b).put(a);

			vertices.put(x0).put(y0).put(u0).put(v0).put(r).put(g).put(b).put(a);
			vertices.put(x1).put(y1).put(u1).put(v1).put(r).put(g).put(b).put(a);
			vertices.put(x1).put(y0).put(u1).put(v0).put(r).put(g).put(b).put(a);

			vertices.flip();
			
			VertexArray vArray = new VertexArray();
			
			VertexBuffer vBuffer = new VertexBuffer(vertices);
			vBuffer.setLayout(layout);
			
			vArray.addVertexBuffer(vBuffer);

			IntBuffer ind = BufferUtils.createIntBuffer(indices.length);
			ind.put(indices);
			ind.flip();
			
			IndexBuffer iBuffer = new IndexBuffer(ind);
			vArray.setIndexBuffer(iBuffer);
			
			shader.bind();

			shader.setUniformMatrix4fv("u_ViewProjection", viewProjectionMatrix);
			shader.setUniformMatrix4fv("u_Transform", transform);
			
			glActiveTexture(GL_TEXTURE0);
			fontTexture.bind();
			
			vArray.bind();

			glDrawElements(GL_TRIANGLES, vArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT, 0);

			vArray.unbind();
			
			drawX += glyph.width;
		}
		
		shader.unbind();
	}

	public static void endScene() {

	}

	public static void clear() {
		glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

}
