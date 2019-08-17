package com.max.harrax.layer;

import static org.lwjgl.opengl.GL40.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.system.MemoryUtil;

import com.max.harrax.events.Event;
import com.max.harrax.events.EventDispatcher;
import com.max.harrax.events.KeyPressedEvent;
import com.max.harrax.events.MouseButtonPressedEvent;
import com.max.harrax.events.MouseButtonReleasedEvent;
import com.max.harrax.events.MouseMovedEvent;
import com.max.harrax.graphics.BufferElement;
import com.max.harrax.graphics.BufferLayout;
import com.max.harrax.graphics.IndexBuffer;
import com.max.harrax.graphics.Shader;
import com.max.harrax.graphics.ShaderDataType;
import com.max.harrax.graphics.VertexArray;
import com.max.harrax.graphics.VertexBuffer;


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
		System.out.println((char)e.getKeyCode());
		return true;
	}
	
	private boolean onMousePressed(MouseButtonPressedEvent e) {
		System.out.println(e.getMouseButton());
		return true;
	}
	
	private boolean onMouseReleased(MouseButtonReleasedEvent e) {
		System.out.println(e.getMouseButton());
		
		return true;
	}
	
	private boolean onMouseMoved(MouseMovedEvent e) {
		//System.out.println(e.getPos().toString());
		
		return true;
	}

	@Override
	public void onUpdate(float delta) {

		shader.bind();
		vArray.bind();
		
		glDrawElements(GL_TRIANGLES, vArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT, 0);	
		
		shader.unbind();
		vArray.unbind();
	}

	@Override
	public void onAttach() {
		System.out.println(name + " layer added to the layerstack.");
	
		float[] vertices = new float[] {
				-0.5f, -0.5f, 0.0f, 0.8f, 0.3f, 0.8f, 1.0f,
				 0.5f, -0.5f, 0.0f, 0.2f, 0.6f, 0.5f, 1.0f,
				 0.0f,  0.5f, 0.0f, 0.8f, 0.2f, 0.2f, 1.0f
		};
		
		int[] indices = new int[] {
				0, 1, 2
		};
		
		for (float f : vertices) {
			//System.out.println(f);
		}
		
		vBuffer = new VertexBuffer(vertices, 3 * 7);
	
		BufferLayout layout = new BufferLayout(
				new BufferElement(ShaderDataType.Float3, "a_Position"),
				new BufferElement(ShaderDataType.Float4, "a_Colour")
				);
		
		vBuffer.setLayout(layout);
		
		vArray = new VertexArray();
		
		vArray.addVertexBuffer(vBuffer);
		
		iBuffer = new IndexBuffer(indices, 3);
		vArray.setIndexBuffer(iBuffer);
		
		shader = new Shader("/shaders/basic.vert", "/shaders/basic.frag");

	}

	@Override
	public void onDetach() {
		System.out.println(name + " layer removed from the layerstack.");
	}
}
