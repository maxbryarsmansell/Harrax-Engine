package com.max.harrax.graphicsOLD;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryStack;

import com.max.harrax.Main;
import com.max.harrax.maths.Mat4;
import com.max.harrax.maths.Vec2;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.GL_FLOAT;

public class Shader {
	
	/*
	 * Stores the handle of the shader.
	 */
	
	private final int id;

	/*
	 * Constructor for the shader
	 */

	public Shader(String vertexPath, String fragmentPath) {
		// Get the source code from the given files.
		CharSequence vertexSource = loadShaderSource(vertexPath);
		CharSequence fragmentSource = loadShaderSource(fragmentPath);

		//Stores the handle of the vertex and fragment shader
		int vertex, fragment, status;

		// Compile the source for the vertex shader
		vertex = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertex, vertexSource);
		glCompileShader(vertex);
		// Get the compilation status to check for errors
		status = glGetShaderi(vertex, GL_COMPILE_STATUS);
		if (status != 1) {
			throw new RuntimeException(glGetShaderInfoLog(vertex));
		}

		// Compile the source for the fragment shader
		fragment = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragment, fragmentSource);
		glCompileShader(fragment);
		// Get the compilation status to check for errors
		status = glGetShaderi(fragment, GL_COMPILE_STATUS);
		if (status != 1) {
			throw new RuntimeException(glGetShaderInfoLog(fragment));
		}

		// Attach the vertex and fragment shaders
		id = glCreateProgram();
		glAttachShader(id, vertex);
		glAttachShader(id, fragment);
		glLinkProgram(id);
		// Get the link status to check for errors
		status = glGetProgrami(id, GL_LINK_STATUS);
		if (status != 1) {
			throw new RuntimeException(glGetProgramInfoLog(id));
		}
		// Delete the vertex and fragment shaders as they are not needed
		glDeleteShader(vertex);
		glDeleteShader(fragment);

	}

	/*
	 * Set the pointer for a vertex attribute in the program
	 */

	public void pointVertexAttribute(String name, int size, int stride, int offset) {
		int location = glGetAttribLocation(id, name);
		glEnableVertexAttribArray(location);
		glVertexAttribPointer(location, size, GL_FLOAT, false, stride, offset);
	}
	
	/*
	 * Sets a integer uniform
	 */

	public void setUniform(String name, int value) {
		int location = glGetUniformLocation(id, name);
		glUniform1i(location, value);
	}
	
	/*
	 * Sets a float uniform
	 */
	
	public void setUniform(String name, float value) {
		int location = glGetUniformLocation(id, name);
		glUniform1f(location, value);
	}
	
	/*
	 * Sets an integer array uniform
	 */
	
	public void setUniform(String name, int[] value) {
		int location = glGetUniformLocation(id, name);
		glUniform1iv(location, value);
	}
	
	/*
	 * Sets a Vec2 uniform
	 */

	public void setUniform(String name, Vec2 value) {
		int location = glGetUniformLocation(id, name);
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buffer = stack.mallocFloat(2);
			value.ToBuffer(buffer);
			glUniform2fv(location, buffer);
		}
	}
	
	/*
	 * Sets a Mat4 uniform
	 */

	public void setUniform(String name, Mat4 value) {
		int location = glGetUniformLocation(id, name);
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buffer = stack.mallocFloat(4 * 4);
			value.toBuffer(buffer);
			glUniformMatrix4fv(location, false, buffer);
		}
	}
	
	/*
	 * Sets the program ready for use
	 */

	public void use() {
		glUseProgram(id);
	}
	
	/*
	 * Disables the program
	 */
	
	public void disable() {
		glUseProgram(0);
	}

	/*
	 * Deletes the shader program
	 */

	public void dispose() {
		glDeleteProgram(id);
	}

	/*
	 * Load the source code from a given file
	 */

	private static CharSequence loadShaderSource(String path) {
		StringBuilder builder = new StringBuilder();
		try (InputStream in = Main.class.getResourceAsStream(path);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line).append("\n");
			}
		} catch (IOException ex) {
			throw new RuntimeException("Failed to load shader file." + System.lineSeparator() + ex.getMessage());
		}
		CharSequence source = builder.toString();
		return source;
	}

}
