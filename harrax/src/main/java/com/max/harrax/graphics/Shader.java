package com.max.harrax.graphics;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1fv;
import static org.lwjgl.opengl.GL20.glUniform1iv;
import static org.lwjgl.opengl.GL20.glUniform3fv;
import static org.lwjgl.opengl.GL20.glUniform4fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

public class Shader {

    private final int id;

    public Shader(String vertexPath, String fragmentPath) {
        // Get the source code from the given files.
        CharSequence vertexSource = loadShaderSource(vertexPath);
        CharSequence fragmentSource = loadShaderSource(fragmentPath);

        // Stores the handle of the vertex and fragment shader
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

    public void setUniform1i(String name, int value) {
        int location = glGetUniformLocation(id, name);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer ib = stack.mallocInt(1).put(value);
            glUniform1iv(location, ib);
        }
    }

    public void setUniform1fv(String name, float value) {
        int location = glGetUniformLocation(id, name);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(1).put(value);
            glUniform1fv(location, fb);
        }
    }

    public void setUniform3fv(String name, Vector3f value) {
        int location = glGetUniformLocation(id, name);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = value.get(stack.mallocFloat(3));
            glUniform3fv(location, fb);
        }
    }

    public void setUniform4fv(String name, Vector4f value) {
        int location = glGetUniformLocation(id, name);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = value.get(stack.mallocFloat(4));
            glUniform4fv(location, fb);
        }
    }

    public void setUniformMatrix4fv(String name, Matrix4f value) {
        int location = glGetUniformLocation(id, name);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = value.get(stack.mallocFloat(4 * 4));
            glUniformMatrix4fv(location, false, fb);
        }
    }

    public void bind() {
        glUseProgram(id);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void dispose() {
        glDeleteProgram(id);
    }

    private static CharSequence loadShaderSource(String path) {
        StringBuilder builder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Shader.class.getResourceAsStream(path)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Failed to load shader file." + System.lineSeparator() + e.getMessage());
        }
        CharSequence source = builder.toString();
        return source;
    }
}
