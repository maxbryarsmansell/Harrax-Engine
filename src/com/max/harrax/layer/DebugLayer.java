package com.max.harrax.layer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.glfw.GLFW;

import com.max.harrax.events.Event;
import com.max.harrax.events.EventDispatcher;
import com.max.harrax.events.KeyPressedEvent;
import com.max.harrax.events.MouseButtonPressedEvent;
import com.max.harrax.events.MouseButtonReleasedEvent;
import com.max.harrax.events.MouseMovedEvent;
import com.max.harrax.graphics.OrthographicCamera;
import com.max.harrax.graphics.Renderer;
import com.max.harrax.graphics.Shader;
import com.max.harrax.graphics.buffer.BufferElement;
import com.max.harrax.graphics.buffer.BufferLayout;
import com.max.harrax.graphics.buffer.IndexBuffer;
import com.max.harrax.graphics.buffer.ShaderDataType;
import com.max.harrax.graphics.buffer.VertexArray;
import com.max.harrax.graphics.buffer.VertexBuffer;
import com.max.harrax.maths.Mat4;
import com.max.harrax.maths.Vec3;


public class DebugLayer extends Layer {
	
	VertexArray vArray;
	VertexBuffer vBuffer;
	IndexBuffer iBuffer;
	
	Shader shader;

	OrthographicCamera camera;
	Vec3 cameraPosition = new Vec3();
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
			cameraPosition.y += 0.5f;
			break;
		case GLFW.GLFW_KEY_S:
			cameraPosition.y -= 0.5f;
			break;
		case GLFW.GLFW_KEY_A:
			cameraPosition.x -= 0.5f;
			break;
		case GLFW.GLFW_KEY_D:
			cameraPosition.x += 0.5f;
			break;
		case GLFW.GLFW_KEY_E:
			cameraRotation += 0.1f;
			break;
		case GLFW.GLFW_KEY_Q:
			cameraRotation -= 0.1f;
			break;
		}
		
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
		
		camera.setPosition(cameraPosition);
		camera.setRotation(cameraRotation);

		Renderer.beginScene(camera);
		
		Renderer.submit(shader, vArray, Mat4.scale(1f));
		
		Renderer.endScene(); 
	}

	@Override
	public void onAttach() {
		System.out.println(name + " layer added to the layer stack.");
	
		float[] vertices = new float[] {
				-0.5f, -0.5f, 0.0f, 0.8f, 0.3f, 0.8f, 1.0f,
				 0.5f, -0.5f, 0.0f, 0.2f, 0.6f, 0.5f, 1.0f,
				 0.0f,  0.5f, 0.0f, 0.8f, 0.2f, 0.2f, 1.0f
		};
		
		FloatBuffer vert = MemoryUtil.memAllocFloat(vertices.length);
		vert.put(vertices);
		vert.flip();
		
		int[] indices = new int[] {
				0, 1, 2
		};
		
		IntBuffer ind = MemoryUtil.memAllocInt(indices.length);
		ind.put(indices);
		ind.flip();
		
		BufferLayout layout = new BufferLayout(
				new BufferElement(ShaderDataType.Float3, "a_Position"),
				new BufferElement(ShaderDataType.Float4, "a_Colour")
				);
		
		vArray = new VertexArray();
		
		iBuffer = new IndexBuffer(ind);
		MemoryUtil.memFree(ind);
		vArray.setIndexBuffer(iBuffer);
		
		vBuffer = new VertexBuffer(vert);
		vBuffer.setLayout(layout);
		MemoryUtil.memFree(vert);
		vArray.addVertexBuffer(vBuffer);
		
		shader = new Shader("/shaders/basic.vert", "/shaders/basic.frag");
		camera = new OrthographicCamera(-1.6f, 1.6f, -0.9f, 0.9f);
	}
	
	@Override
	public void dispose() {
		vArray.dispose();
		vBuffer.dispose();
		iBuffer.dispose();
		shader.dispose();
	}

	@Override
	public void onDetach() {
		System.out.println(name + " layer removed from the layer stack.");
	}
}
