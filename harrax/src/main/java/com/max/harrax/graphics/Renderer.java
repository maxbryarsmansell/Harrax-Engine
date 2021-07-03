package com.max.harrax.graphics;

import static com.max.harrax.graphics.buffer.ShaderDataType.Float2;
import static com.max.harrax.graphics.buffer.ShaderDataType.Float4;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;

import java.nio.ByteBuffer;
import java.util.List;

import com.max.harrax.graphics.buffer.BufferElement;
import com.max.harrax.graphics.buffer.BufferLayout;
import com.max.harrax.graphics.buffer.VertexArray;
import com.max.harrax.graphics.buffer.VertexBuffer;

import org.joml.Vector2f;
import org.joml.Vector4f;

public class Renderer {

    private static final int MAX_VERTICES = 1024 * 50;

    private Shader triangleShader;
    private VertexArray triangleVertexArray;
    private VertexBuffer triangleVertexBuffer;
    private ByteBuffer triangleByteBuffer;
    private int triangleVertexCount;

    private Shader lineShader;
    private VertexArray lineVertexArray;
    private VertexBuffer lineVertexBuffer;
    private ByteBuffer lineByteBuffer;
    private int lineVertexCount;

    private int rendererSubmissions;
    private int redererDrawCalls;

    public Renderer() {
        init();
    }

    public void init() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        rendererSubmissions = 0;
        redererDrawCalls = 0;

        // Triangle Renderer
        BufferLayout triangleBufferLayout = new BufferLayout(new BufferElement(Float2, "a_Position"),
                new BufferElement(Float4, "a_Colour"));
        triangleShader = new Shader("/assets/shaders/batch.vert", "/assets/shaders/batch.frag");
        triangleVertexArray = new VertexArray();
        triangleVertexBuffer = new VertexBuffer(triangleBufferLayout, MAX_VERTICES);
        triangleVertexCount = 0;
        triangleVertexArray.addVertexBuffer(triangleVertexBuffer);

        // Line Renderer
        BufferLayout lineBufferLayout = new BufferLayout(new BufferElement(Float2, "a_Position"),
                new BufferElement(Float4, "a_Colour"));
        lineShader = new Shader("/assets/shaders/batch.vert", "/assets/shaders/batch.frag");
        lineVertexArray = new VertexArray();
        lineVertexBuffer = new VertexBuffer(lineBufferLayout, MAX_VERTICES);
        lineVertexCount = 0;
        lineVertexArray.addVertexBuffer(lineVertexBuffer);
    }

    public void dispose() {
        // Triangle Renderer
        triangleShader.dispose();
        triangleVertexArray.dispose();
        triangleVertexBuffer.dispose();

        // Line Renderer
        lineShader.dispose();
        lineVertexArray.dispose();
        lineVertexBuffer.dispose();
    }

    public void beginScene(Camera camera) {

        if (triangleVertexCount > 0 || lineVertexCount > 0) {
            endScene();
        }

        redererDrawCalls = 0;
        rendererSubmissions = 0;

        // System.out.println(camera.getViewProjectionMatrix());

        // Triangle Shader
        triangleShader.bind();
        triangleShader.setUniformMatrix4fv("u_ViewProjection", camera.getViewProjectionMatrix());
        triangleShader.unbind();

        // Line Shader
        lineShader.bind();
        lineShader.setUniformMatrix4fv("u_ViewProjection", camera.getViewProjectionMatrix());
        lineShader.unbind();

        mapTriangleBuffer();
        mapLineBuffer();
    }

    public static void setViewportSize(int width, int height) {
        glViewport(0, 0, width, height);
    }

    public static void clear() {
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void endScene() {
        flush();
    }

    private void flush() {
        flushTriangleBuffer();
        flushLineBuffer();
    }

    private void mapTriangleBuffer() {
        triangleByteBuffer = triangleVertexBuffer.mapBuffer(triangleByteBuffer);
    }

    private void mapLineBuffer() {
        lineByteBuffer = lineVertexBuffer.mapBuffer(lineByteBuffer);
    }

    private void flushTriangleBuffer() {

        triangleVertexBuffer.unmapBuffer();

        // Check whether there are vertices to flush
        if (triangleVertexCount == 0)
            return;

        triangleShader.bind();
        triangleVertexArray.bind();

        glDrawArrays(GL_TRIANGLES, 0, triangleVertexCount);

        triangleVertexArray.unbind();
        triangleShader.unbind();

        triangleVertexCount = 0;

        redererDrawCalls++;
    }

    private void flushLineBuffer() {

        lineVertexBuffer.unmapBuffer();

        // Check whether there are vertices to flush
        if (lineVertexCount == 0)
            return;

        lineShader.bind();
        lineVertexArray.bind();

        glDrawArrays(GL_LINES, 0, lineVertexCount);

        lineVertexArray.unbind();
        lineShader.unbind();

        lineVertexCount = 0;

        redererDrawCalls++;
    }

    public void submitLine(Vector2f p1, Vector2f p2, Colour colour) {

        if (MAX_VERTICES - lineVertexCount < 2) {
            flushLineBuffer();
            mapLineBuffer();
        }

        lineByteBuffer.putFloat(p1.x).putFloat(p1.y).putFloat(colour.r).putFloat(colour.g).putFloat(colour.b)
                .putFloat(colour.a);
        lineByteBuffer.putFloat(p2.x).putFloat(p2.y).putFloat(colour.r).putFloat(colour.g).putFloat(colour.b)
                .putFloat(colour.a);

        lineVertexCount += 2;

        rendererSubmissions++;
    }

    public void submitTriangle(Vector2f p1, Vector2f p2, Vector2f p3, Colour colour) {

        if (MAX_VERTICES - triangleVertexCount < 3) {
            flushTriangleBuffer();
            mapTriangleBuffer();
        }

        triangleByteBuffer.putFloat(p1.x).putFloat(p1.y).putFloat(colour.r).putFloat(colour.g).putFloat(colour.b)
                .putFloat(colour.a);
        triangleByteBuffer.putFloat(p2.x).putFloat(p2.y).putFloat(colour.r).putFloat(colour.g).putFloat(colour.b)
                .putFloat(colour.a);
        triangleByteBuffer.putFloat(p3.x).putFloat(p3.y).putFloat(colour.r).putFloat(colour.g).putFloat(colour.b)
                .putFloat(colour.a);

        triangleVertexCount += 3;

        rendererSubmissions++;
    }

    public void submitQuad(float x, float y, float width, float height, Colour colour) {

        if (MAX_VERTICES - triangleVertexCount < 6) {
            flushTriangleBuffer();
            mapTriangleBuffer();
        }

        triangleByteBuffer.putFloat(x - width).putFloat(y - height).putFloat(colour.r).putFloat(colour.g)
                .putFloat(colour.b).putFloat(colour.a);
        triangleByteBuffer.putFloat(x - width).putFloat(y + height).putFloat(colour.r).putFloat(colour.g)
                .putFloat(colour.b).putFloat(colour.a);
        triangleByteBuffer.putFloat(x + width).putFloat(y + height).putFloat(colour.r).putFloat(colour.g)
                .putFloat(colour.b).putFloat(colour.a);

        triangleByteBuffer.putFloat(x - width).putFloat(y - height).putFloat(colour.r).putFloat(colour.g)
                .putFloat(colour.b).putFloat(colour.a);
        triangleByteBuffer.putFloat(x + width).putFloat(y + height).putFloat(colour.r).putFloat(colour.g)
                .putFloat(colour.b).putFloat(colour.a);
        triangleByteBuffer.putFloat(x + width).putFloat(y - height).putFloat(colour.r).putFloat(colour.g)
                .putFloat(colour.b).putFloat(colour.a);

        triangleVertexCount += 6;

        rendererSubmissions++;
    }

    public void submitPolygon(List<Vector4f> vertices, Colour colour) {

        if (MAX_VERTICES - triangleVertexCount < (vertices.size() - 2) * 3) {
            flushTriangleBuffer();
            mapTriangleBuffer();
        }

        float amt = 0.5f;
        Colour fillColour = new Colour(colour.r * amt, colour.g * amt, colour.b * amt, colour.a);

        for (int i = 0; i < vertices.size() - 2; i++) {
            triangleByteBuffer.putFloat(vertices.get(0).x).putFloat(vertices.get(0).y).putFloat(fillColour.r)
                    .putFloat(fillColour.g).putFloat(fillColour.b).putFloat(fillColour.a);

            triangleByteBuffer.putFloat(vertices.get(i + 1).x).putFloat(vertices.get(i + 1).y).putFloat(fillColour.r)
                    .putFloat(fillColour.g).putFloat(fillColour.b).putFloat(fillColour.a);

            triangleByteBuffer.putFloat(vertices.get(i + 2).x).putFloat(vertices.get(i + 2).y).putFloat(fillColour.r)
                    .putFloat(fillColour.g).putFloat(fillColour.b).putFloat(fillColour.a);

            triangleVertexCount += 3;
        }

        rendererSubmissions++;
    }

    public int getRendererSubmissions() {
        return rendererSubmissions;
    }

    public int getRedererDrawCalls() {
        return redererDrawCalls;
    }
}
