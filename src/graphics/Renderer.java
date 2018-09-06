package graphics;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

import org.lwjgl.system.MemoryUtil;

import graphics.renderables.Renderable;
import maths.Mat4;
import maths.Vec2;
import maths.Vec4;
import graphics.text.Font;
import graphics.text.Glyph;

public class Renderer {

	private final int VERTEX_SIZE = 9;

	/*
	 * Buffer Layout
	 * VERTEX => float x, float y. 
	 * TEXTURECOORD => float x, float y.
	 * TEXTUREID => float ts.
	 * COLOUR => float r, float g, float b, float a.
	 * 
	 */

	private final int MAX_SPRITES = 10000;
	private final int RENDERABLE_VERTEX_COUNT = 6;
	private final int RENDERABLE_FLOAT_SIZE = VERTEX_SIZE * RENDERABLE_VERTEX_COUNT;
	private final int MAX_FLOATS = MAX_SPRITES * RENDERABLE_FLOAT_SIZE;

	/*
	 * Attributes for
	 * Vertex Array Object
	 * Vertex Buffer Object
	 * Shader program
	 * Camera
	 * The batch vertices
	 * Transformation stack to hold transformations
	 * Array to store textures that have been submitted
	 * Counter for the numbers of vertices in the current batch
	 * Boolean flag for whether the renderer is drawing
	 */
	
	private VertexArrayObject vao;
	private VertexBufferObject vbo;
	private Shader shader;
	private Camera camera;
	private FloatBuffer vertices;
	private Stack<Mat4> transformationStack;
	private ArrayList<Texture> textures;
	private int vertexCount;
	private boolean drawing;

	/*
	 * Constructors for the renderer
	 */

	public Renderer() {
		init(new Shader("res/shaders/batch.vert", "res/shaders/batch.frag"), new Camera());
	}

	public Renderer(Shader shader, Camera camera) {
		init(shader, camera);
	}

	/*
	 * Function which starts the rendering process.
	 */

	public void begin() {
		if (drawing) {
			throw new IllegalStateException("Renderer is already drawing!");
		}
		drawing = true;
		vertexCount = 0;
	}

	/*
	 * Function which ends the rendering process.
	 */

	public void end() {
		if (!drawing) {
			throw new IllegalStateException("Renderer isn't drawing!");
		}
		drawing = false;
		flush();
	}
	
	/*
	 * Function for disposing of resources used by the renderer
	 */

	public void dispose() {
		MemoryUtil.memFree(vertices);
		if (vao != null) {
			vao.dispose();
		}
		vbo.dispose();
	}
	
	/*
	 * Function which flushes the current batch of data.
	 */

	public void flush() {
		// Only flush the batch if there is data ready to flush.
		if (vertexCount > 0) {

			for (int i = 0; i < textures.size(); i++) {
				glActiveTexture(GL_TEXTURE0 + i);
				glBindTexture(GL_TEXTURE_2D, textures.get(i).getID());
			}

			// Set the buffer ready for reading from
			vertices.flip();
			vao.bind();
			shader.use();
			setCamera(camera);
			
			// Upload the new vertex data
			vbo.bind(GL_ARRAY_BUFFER);
			vbo.uploadSubData(GL_ARRAY_BUFFER, 0, vertices);

			// Draw the batch
			glDrawArrays(GL_TRIANGLES, 0, vertexCount);

			// Clear ready for the next batch
			vertices.clear();
			vertexCount = 0;
		}
	}
	

	public void push_transformation(Mat4 transformation) {
		transformationStack.push(transformation.Multiply(transformationStack.lastElement()));
	}

	public void pop_transformation() {
		if (transformationStack.size() > 1) {
			transformationStack.pop();
		}
	}
	
	/*
	 * Set projection and view matrix uniforms in the shader
	 */

	public void setCamera(Camera camera) {
		this.camera = camera;
		
		shader.setUniform("view", camera.getViewMatrix());
		shader.setUniform("projection", camera.getProjectionMatrix());
		
	}
	
	/*
	 * Submit a renderable to the vertex buffer
	 */

	public void submitQuad(Renderable renderable) {
		// check whether there is enough space in the current batch
		if (vertices.remaining() < RENDERABLE_VERTEX_COUNT * VERTEX_SIZE) {
			flush();
		}

		// Get the position of the renderable, and apply the transformation at the top of the stack
		Vec4 position = new Vec4(renderable.getPosition()).Multiply(transformationStack.lastElement());
		// Get the size of the renderable
		Vec2 size = renderable.getSize();
		// Get the colour fo the renderable
		Colour colour = renderable.getColour();

		// Convert the position and size to float coordinates
		float x0 = position.x;
		float y0 = position.y;
		float x1 = position.x + size.x;
		float y1 = position.y + size.y;

		// Set default texture coordinates
		float u0 = 0.0f;
		float v0 = 0.0f;
		float u1 = 1.0f;
		float v1 = 1.0f;

		// Set default texture slot / texture id
		float ts = 0.0f;

		// Convert colour to floats of RGBA
		float r = colour.getRed();
		float g = colour.getGreen();
		float b = colour.getBlue();
		float a = colour.getAlpha();
		
		// Put the vertex data into the float buffer
		// There are 6 vertices per quad
		vertices.put(x0).put(y0).put(u0).put(v0).put(ts).put(r).put(g).put(b).put(a);
		vertices.put(x0).put(y1).put(u0).put(v1).put(ts).put(r).put(g).put(b).put(a);
		vertices.put(x1).put(y1).put(u1).put(v1).put(ts).put(r).put(g).put(b).put(a);

		vertices.put(x0).put(y0).put(u0).put(v0).put(ts).put(r).put(g).put(b).put(a);
		vertices.put(x1).put(y1).put(u1).put(v1).put(ts).put(r).put(g).put(b).put(a);
		vertices.put(x1).put(y0).put(u1).put(v0).put(ts).put(r).put(g).put(b).put(a);

		// Increment the vertex count
		vertexCount += RENDERABLE_VERTEX_COUNT;
	}
	
	/*
	 * Submit text to the vertex buffer
	 */

	public void submitText(Font font, float x, float y, Colour colour, CharSequence text) {
		// Get the font width and height
		int fontWidth = font.getFontWidth();
		int fontHeight = font.getFontHeight();
		
		// Get the font char-glyph map
		Map<Character, Glyph> glyphs = font.getGlyphs();
		
		// Get the font texture
		Texture fontTexture = font.getTexture();
		
		// Calculate the texture slot
		float ts = 0.0f;
		if (fontTexture != null) {
			ts = submitTexture(fontTexture);
		}
		
		// Variables to store current character position
		float drawX = x;
		float drawY = y;
		
		// Iterate through each character in the text
		for (int i = 0; i < text.length(); i++) {
			
			// check whether there is enough space in the current batch
			if (vertices.remaining() < RENDERABLE_VERTEX_COUNT * VERTEX_SIZE) {
				flush();
			}
			
			// Get the current character
			char character = text.charAt(i);
			
			// If the character is a new line, then set x and y to draw at the next line
			if (character == '\n') {
				drawY -= fontHeight;
				drawX = x;
				continue;
			}
			
			// If the character is a carriage return, just skip it
			if (character == '\r') {
				continue;
			}
			
			// Get the glyph for the current character
			Glyph glyph = glyphs.get(character);
			
			// Get the position of the character, and apply the transformation at the top of the stack
			Vec4 position = new Vec4(drawX, drawY, 0, 1).Multiply(transformationStack.lastElement());
			
			// Convert the position and size to float coordinates
			float x0 = position.x;
			float x1 = position.x + glyph.width;
			float y0 = position.y;
			float y1 = position.y + glyph.height;
			
			// Calculate texture coordinates for the character
			float u0 = (float) glyph.x / fontWidth;
			float u1 = (float) (glyph.x + glyph.width) / (float) fontWidth;
			float v0 = (float) glyph.y / fontTexture.getHeight();
			float v1 = (float) (glyph.y + glyph.height) / (float) fontHeight;
			
			// Convert colour to RGBA floats
			float r = colour.getRed();
			float g = colour.getGreen();
			float b = colour.getBlue();
			float a = colour.getAlpha();
			
			// Put the vertex data into the float buffer
			// There are 6 vertices per character
			vertices.put(x0).put(y0).put(u0).put(v0).put(ts).put(r).put(g).put(b).put(a);
			vertices.put(x0).put(y1).put(u0).put(v1).put(ts).put(r).put(g).put(b).put(a);
			vertices.put(x1).put(y1).put(u1).put(v1).put(ts).put(r).put(g).put(b).put(a);

			vertices.put(x0).put(y0).put(u0).put(v0).put(ts).put(r).put(g).put(b).put(a);
			vertices.put(x1).put(y1).put(u1).put(v1).put(ts).put(r).put(g).put(b).put(a);
			vertices.put(x1).put(y0).put(u1).put(v0).put(ts).put(r).put(g).put(b).put(a);
			
			// Increment the x position count
			drawX += glyph.width;
			
			// Increment the vertex count
			vertexCount += RENDERABLE_VERTEX_COUNT;
			
		}
	}
	
	/*
	 * Initialisation method for the renderer
	 */
	
	private void init(Shader shader, Camera camera) {
		// Initialise attributes
		this.vertexCount = 0;
		this.drawing = false;
		this.transformationStack = new Stack<Mat4>();
		this.transformationStack.push(new Mat4());
		this.textures = new ArrayList<Texture>();
		this.shader = shader;
		this.camera = camera;

		// Generate Vertex Array Object
		vao = new VertexArrayObject();
		vao.bind();

		// Generate Vertex Buffer Object
		vbo = new VertexBufferObject();
		vbo.bind(GL_ARRAY_BUFFER);

		// Allocate the memory for the vertices buffer
		vertices = MemoryUtil.memAllocFloat(MAX_FLOATS);

		// Upload null data to allocate storage for the VBO
		long size = vertices.capacity() * Float.BYTES;
		vbo.uploadData(GL_ARRAY_BUFFER, size, GL_DYNAMIC_DRAW);

		shader.use();

		// Set the layout of memory
		shader.pointVertexAttribute("position", 2, VERTEX_SIZE * Float.BYTES, 0);
		shader.pointVertexAttribute("texcoord", 2, VERTEX_SIZE * Float.BYTES, 2 * Float.BYTES);
		shader.pointVertexAttribute("tid", 1, VERTEX_SIZE * Float.BYTES, 4 * Float.BYTES);
		shader.pointVertexAttribute("colour", 4, VERTEX_SIZE * Float.BYTES, 5 * Float.BYTES);
		int[] texIDs = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
		shader.setUniform("textures", texIDs);
		// Set model matrix to identity matrix
		shader.setUniform("model", new Mat4());
		// Set the camera
		setCamera(camera);
		// Enable blending
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	/*
	 * Submit a new texture
	 */
	
	private float submitTexture(Texture texture) {
		float ts = 0.0f;
		boolean found = false;
		for (int i = 0; i < textures.size(); i++) {
			if (textures.get(i) == texture) {
				ts = (float) (i + 1);
				found = true;
				break;
			}
		}
		if (!found) {
			textures.add(texture);
			ts = (float) (textures.size());
		}
		return ts;
	}
	
	/*
	 * Clears the drawing area.
	 */

	public static void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
}
