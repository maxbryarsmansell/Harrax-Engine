package com.max.harrax.layer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL40;

import com.max.harrax.events.Event;
import com.max.harrax.events.EventDispatcher;
import com.max.harrax.events.KeyPressedEvent;
import com.max.harrax.events.MouseButtonPressedEvent;
import com.max.harrax.events.MouseButtonReleasedEvent;
import com.max.harrax.events.MouseMovedEvent;
import com.max.harrax.graphics.Camera;
import com.max.harrax.graphics.Material;
import com.max.harrax.graphics.Mesh;
import com.max.harrax.graphics.OrthographicCamera;
import com.max.harrax.graphics.PerspectiveCamera;
import com.max.harrax.graphics.Renderer;
import com.max.harrax.graphics.Shader;
import com.max.harrax.maths.Helper;
import com.max.harrax.maths.Mat4;
import com.max.harrax.maths.Vec3;

import static org.lwjgl.opengl.GL40.*;


public class DebugLayer extends Layer {
	
	Mesh quadMesh;
	
	Shader shader;

	Camera camera;
	Vec3 cameraPosition = new Vec3(0, 0, 0);
	float cameraRotation = 0.0f;
	
	public DebugLayer() {
		super("Debug");
	}

	@Override
	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.EventType.KeyPressed, (Event e) -> (onKeyboardPress((KeyPressedEvent) e)));
		dispatcher.dispatch(Event.EventType.MouseButtonPressed, (Event e) -> (onMousePressed((MouseButtonPressedEvent) e)));
		dispatcher.dispatch(Event.EventType.MouseButtonReleased, (Event e) -> (onMouseReleased((MouseButtonReleasedEvent) e)));
		dispatcher.dispatch(Event.EventType.MouseMoved, (Event e) -> (onMouseMoved((MouseMovedEvent) e)));
	}

	private boolean onKeyboardPress(KeyPressedEvent e) {
		System.out.println((char)e.getKeyCode() + " pressed");
		
		switch (e.getKeyCode()) {
		case GLFW.GLFW_KEY_W:
			cameraPosition.z -= 0.5f;
			break;
		case GLFW.GLFW_KEY_S:
			cameraPosition.z += 0.5f;
			break;

		case GLFW.GLFW_KEY_E:
			cameraRotation += 0.1f;
			break;
		case GLFW.GLFW_KEY_Q:
			cameraRotation -= 0.1f;
			break;
		}
		
		System.out.println(cameraPosition.toString());
		
		return true;
	}
	
	private boolean onMousePressed(MouseButtonPressedEvent e) {
		System.out.println(e.getMouseButton() == 0 ? "Mouse 1 pressed" : (e.getMouseButton() == 1 ? "Mouse 2 pressed" : "Other Mouse pressed"));
		return true;
	}
	
	private boolean onMouseReleased(MouseButtonReleasedEvent e) {
		//System.out.println(e.getMouseButton() == 0 ? "Mouse 1 released" : (e.getMouseButton() == 1 ? "Mouse 2 released" : "Other Mouse released"));
		
		return true;
	}
	
	private boolean onMouseMoved(MouseMovedEvent e) {
		//System.out.println(e.getPos().toString());
		
		return true;
	}

	@Override
	public void onUpdate(float delta) {
		
		//camera.setPosition(cameraPosition);
		//camera.setRotation(cameraRotation);

		GL40.glEnable(GL_DEPTH_TEST);
		
		Renderer.beginScene(camera);
		
		Renderer.submit(shader, Material.DEBUG_MATERIAL, quadMesh, Mat4.yAxisRotation(0.2f).mult(Mat4.xAxisRotation(0.2f)).mult(Mat4.translation(cameraPosition)));
		
		Renderer.endScene(); 
	}

	@Override
	public void onAttach() {
		System.out.println(name + " layer added to the layer stack.");
	
		
		quadMesh = Mesh.cubeMesh();
		
		
		shader = new Shader("/shaders/basic.vert", "/shaders/basic.frag");
		camera = new PerspectiveCamera(Helper.toRadians(45f), 4f / 3f);
		//camera = new OrthographicCamera(-4, 4, -3, 3);
	}
	
	@Override
	public void dispose() {
		quadMesh.dispose();
		shader.dispose();
	}

	@Override
	public void onDetach() {
		System.out.println(name + " layer removed from the layer stack.");
	}
}
