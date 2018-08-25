package graphics.layers;

import java.util.ArrayList;

import graphics.Camera;
import graphics.Renderer;
import graphics.Shader;
import graphics.renderables.Renderable;

public class Layer {
	
	/*
	 * Attributes for
	 * A list of renderables in the layer
	 * A camera
	 * A shader program
	 * A renderer to render the renderables
	 */
	
	private ArrayList<Renderable> renderables;
	private Camera camera;
	private Shader shader;
	private Renderer renderer;
	
	/*
	 * Layer constructors
	 */
	
	public Layer() {
		this.renderables = new ArrayList<Renderable>();
		this.camera = new Camera(800, 600);
		this.shader = new Shader("res/shaders/batch.vert", "res/shaders/batch.frag");
		this.renderer = new Renderer(shader, camera);
	}
	
	public Layer(Shader shader, Camera camera) {
		this.renderables = new ArrayList<Renderable>();
		this.camera = camera;
		this.shader = shader;
		this.renderer = new Renderer(shader, camera);
	}
	
	/*
	 * Render all of the renderables
	 */
	
	public void render() {
		renderer.begin();
		for (Renderable renderable : renderables) {
			renderable.submit(renderer);
		}
		renderer.end();
	}
	
	/*
	 * Dispose of the resources used by the layer
	 */
	
	public void dispose() {
		renderer.dispose();
		shader.dispose();
	}
	
	/*
	 * Add a renderable to the layer
	 */
	
	public void add(Renderable renderable) {
		renderables.add(renderable);
	}
	
	/*
	 * Remove a renderable from the layer
	 */
	
	public void remove(Renderable renderable) {
		renderables.remove(renderable);
	}
	
	/*
	 * Getters and setters
	 */
	
	public Shader getShader() {
		return shader;
	}
	
	public Camera getCamera() {
		return camera;
	}

}
