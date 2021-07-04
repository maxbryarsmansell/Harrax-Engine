package com.max.harrax.graphics;

import static com.max.harrax.graphics.buffer.ShaderDataType.Float2;
import static com.max.harrax.graphics.buffer.ShaderDataType.Float4;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.List;

import com.max.harrax.Application;
import com.max.harrax.graphics.buffer.BufferElement;
import com.max.harrax.graphics.buffer.BufferLayout;
import com.max.harrax.graphics.buffer.FrameBuffer;
import com.max.harrax.graphics.buffer.VertexArray;
import com.max.harrax.graphics.buffer.VertexBuffer;

import org.joml.Vector2f;
import org.joml.Vector4f;

public class Renderer {

    private static final int MAX_VERTICES = 1024 * 50;
    private static final int MAX_LIGHTS = 10;

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

    private Shader lightingShader;
    private VertexArray lightingVertexArray;
    private VertexBuffer lightingVertexBuffer;
    private ByteBuffer lightByteBuffer;
    private int lightVertexCount;

    private Texture lightTexture;

    private FrameBuffer frameBuffer;

    private int rendererSubmissions;
    private int redererDrawCalls;

    public Renderer() {
        init();
    }

    public void init() {

        // glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        rendererSubmissions = 0;
        redererDrawCalls = 0;

        // Triangle Renderer
        BufferLayout triangleBufferLayout = new BufferLayout(new BufferElement(Float2, "a_Position"),
                new BufferElement(Float4, "a_Colour"));
        triangleShader = new Shader(loadShaderSource("/assets/shaders/batch.vert"),
                loadShaderSource("/assets/shaders/batch.frag"));
        triangleVertexBuffer = new VertexBuffer(triangleBufferLayout, MAX_VERTICES);
        triangleVertexArray = new VertexArray();
        triangleVertexArray.addVertexBuffer(triangleVertexBuffer);
        triangleVertexCount = 0;

        // Line Renderer
        BufferLayout lineBufferLayout = new BufferLayout(new BufferElement(Float2, "a_Position"),
                new BufferElement(Float4, "a_Colour"));
        lineShader = new Shader(loadShaderSource("/assets/shaders/batch.vert"),
                loadShaderSource("/assets/shaders/batch.frag"));
        lineVertexBuffer = new VertexBuffer(lineBufferLayout, MAX_VERTICES);
        lineVertexArray = new VertexArray();
        lineVertexArray.addVertexBuffer(lineVertexBuffer);
        lineVertexCount = 0;

        BufferLayout bufferLayout = new BufferLayout(new BufferElement(Float2, "a_Position"),
                new BufferElement(Float2, "a_TexCoord"), new BufferElement(Float4, "a_Colour"));
        lightingShader = new Shader(loadShaderSource("/assets/shaders/lighting.vert"),
                loadShaderSource("/assets/shaders/lighting.frag"));
        lightingVertexBuffer = new VertexBuffer(bufferLayout, MAX_LIGHTS * 6);
        lightingVertexArray = new VertexArray();
        lightingVertexArray.addVertexBuffer(lightingVertexBuffer);
        lightVertexCount = 0;

        lightTexture = Texture.loadTexture("/assets/light.png");

        // ByteBuffer byteBuffer = lightingVertexBuffer.mapBuffer(null);

        // byteBuffer
        // .putFloat(-1f).putFloat( 1f).putFloat(0f).putFloat(1f)
        // .putFloat(-1f).putFloat(-1f).putFloat(0f).putFloat(0f)
        // .putFloat( 1f).putFloat(-1f).putFloat(1f).putFloat(0f)
        // .putFloat( 1f).putFloat(-1f).putFloat(1f).putFloat(0f)
        // .putFloat(-1f).putFloat( 1f).putFloat(0f).putFloat(1f)
        // .putFloat( 1f).putFloat( 1f).putFloat(1f).putFloat(1f);

        // lightingVertexBuffer.unmapBuffer();

        frameBuffer = new FrameBuffer(Application.get().getWindow().getWidth(),
                                        Application.get().getWindow().getHeight());
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

        // Lighting
        lightingShader.dispose();
        lightingVertexArray.dispose();
        lightingVertexBuffer.dispose();

        lightTexture.dispose();

        frameBuffer.dispose();
    }

    public void beginScene(Camera camera) {

        if (triangleVertexCount > 0 || lineVertexCount > 0) {
            endScene();
        }

        redererDrawCalls = 0;
        rendererSubmissions = 0;

        // Triangle Shader
        triangleShader.bind();
        triangleShader.setUniformMatrix4fv("u_ViewProjection", camera.getViewProjectionMatrix());
        triangleShader.unbind();

        // Line Shader
        lineShader.bind();
        lineShader.setUniformMatrix4fv("u_ViewProjection", camera.getViewProjectionMatrix());
        lineShader.unbind();

        // Light Shader
        lightingShader.bind();
        lightingShader.setUniformMatrix4fv("u_ViewProjection", camera.getViewProjectionMatrix());
        lightingShader.unbind();

        mapTriangleBuffer();
        mapLineBuffer();
        mapLightBuffer();
    }

    public static void setViewportSize(int width, int height) {
        glViewport(0, 0, width, height);
    }

    public static void clear() {
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void enableDepthTest() {
        glEnable(GL_DEPTH_TEST);
    }

    public static void disableDepthTest() {
        glDisable(GL_DEPTH_TEST);
    }

    public void endScene() {
        flush();
    }

    private void flush() {

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        // glEnable(GL_DEPTH_TEST);

        flushTriangleBuffer();
        flushLineBuffer();

        // Lighting Pass

        // frameBuffer.bind();

        // glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        // glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        // glDisable(GL_DEPTH_TEST);
        
        flushLightBuffer();

        // frameBuffer.unbind();

        // lightingShader.bind();
        // lightingVertexArray.bind();

        // frameBuffer.bindColourTexture();

        // glDrawArrays(GL_TRIANGLES, 0, lightVertexCount);

        // lightingVertexArray.unbind();
        // lightingShader.unbind();

    }

    private void mapTriangleBuffer() {
        triangleByteBuffer = triangleVertexBuffer.mapBuffer(triangleByteBuffer);
    }

    private void mapLineBuffer() {
        lineByteBuffer = lineVertexBuffer.mapBuffer(lineByteBuffer);
    }

    private void mapLightBuffer() {
        lightByteBuffer = lightingVertexBuffer.mapBuffer(lightByteBuffer);
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

    private void flushLightBuffer() {

        lightingVertexBuffer.unmapBuffer();

        // Check whether there are vertices to flush
        if (lightVertexCount == 0)
            return;

        lightingShader.bind();
        lightingVertexArray.bind();

        lightTexture.bind();

        glDrawArrays(GL_TRIANGLES, 0, lightVertexCount);

        lightingVertexArray.unbind();
        lightingShader.unbind();

        lightVertexCount = 0;

        redererDrawCalls++;
    }

    public void submitLight(Vector2f pos, float radius, Colour colour) {

        if (MAX_LIGHTS * 6 - lightVertexCount < 6) {
            flushLightBuffer();
            mapLightBuffer();
        }

        lightByteBuffer.putFloat(pos.x - radius).putFloat(pos.y + radius).putFloat(0f).putFloat(1f).putFloat(colour.r).putFloat(colour.g).putFloat(colour.b).putFloat(colour.a);
        lightByteBuffer.putFloat(pos.x - radius).putFloat(pos.y - radius).putFloat(0f).putFloat(0f).putFloat(colour.r).putFloat(colour.g).putFloat(colour.b).putFloat(colour.a);
        lightByteBuffer.putFloat(pos.x + radius).putFloat(pos.y - radius).putFloat(1f).putFloat(0f).putFloat(colour.r).putFloat(colour.g).putFloat(colour.b).putFloat(colour.a);
        lightByteBuffer.putFloat(pos.x + radius).putFloat(pos.y - radius).putFloat(1f).putFloat(0f).putFloat(colour.r).putFloat(colour.g).putFloat(colour.b).putFloat(colour.a);
        lightByteBuffer.putFloat(pos.x - radius).putFloat(pos.y + radius).putFloat(0f).putFloat(1f).putFloat(colour.r).putFloat(colour.g).putFloat(colour.b).putFloat(colour.a);
        lightByteBuffer.putFloat(pos.x + radius).putFloat(pos.y + radius).putFloat(1f).putFloat(1f).putFloat(colour.r).putFloat(colour.g).putFloat(colour.b).putFloat(colour.a);

        lightVertexCount += 6;

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

    private static String loadShaderSource(String path) {

        StringBuilder builder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Renderer.class.getResourceAsStream(path)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Failed to load shader file.");
        }

        return builder.toString();
    }
}
