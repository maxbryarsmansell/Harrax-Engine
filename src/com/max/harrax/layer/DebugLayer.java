package com.max.harrax.layer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import com.max.harrax.events.Event;
import com.max.harrax.events.EventDispatcher;
import com.max.harrax.events.KeyPressedEvent;
import com.max.harrax.events.MouseButtonPressedEvent;
import com.max.harrax.events.MouseButtonReleasedEvent;
import com.max.harrax.events.MouseMovedEvent;
import com.max.harrax.graphics.BufferElement;
import com.max.harrax.graphics.BufferLayout;
import com.max.harrax.graphics.IndexBuffer;
import com.max.harrax.graphics.Renderer;
import com.max.harrax.graphics.Shader;
import com.max.harrax.graphics.ShaderDataType;
import com.max.harrax.graphics.VertexArray;
import com.max.harrax.graphics.VertexBuffer;
import com.max.harrax.maths.Mat4;


public class DebugLayer extends Layer {
	
	VertexArray vArray;
	VertexBuffer vBuffer;
	IndexBuffer iBuffer;
	
	Shader shader;

	
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

		Renderer.beginScene(new Mat4());
		
		Renderer.submit(shader, vArray, Mat4.Scale(2.0f));
		
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
		
		FloatBuffer vert = BufferUtils.createFloatBuffer(vertices.length);
		vert.put(vertices);
		vert.flip();
		
		int[] indices = new int[] {
				0, 1, 2
		};
		
		IntBuffer ind = BufferUtils.createIntBuffer(indices.length);
		ind.put(indices);
		ind.flip();
		
		vBuffer = new VertexBuffer(vert);
	
		BufferLayout layout = new BufferLayout(
				new BufferElement(ShaderDataType.Float3, "a_Position"),
				new BufferElement(ShaderDataType.Float4, "a_Colour")
				);
		
		vBuffer.setLayout(layout);
		
		vArray = new VertexArray();
		
		vArray.addVertexBuffer(vBuffer);
		
		iBuffer = new IndexBuffer(ind);
		vArray.setIndexBuffer(iBuffer);
		
		shader = new Shader("/shaders/basic.vert", "/shaders/basic.frag");
	}

	@Override
	public void onDetach() {
		System.out.println(name + " layer removed from the layer stack.");
	}
}
