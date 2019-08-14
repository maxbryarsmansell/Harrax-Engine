package com.max.harrax.layer;

import com.max.harrax.events.Event;
import com.max.harrax.events.EventDispatcher;
import com.max.harrax.events.KeyPressedEvent;
import com.max.harrax.events.MouseButtonPressedEvent;
import com.max.harrax.events.MouseButtonReleasedEvent;
import com.max.harrax.events.MouseMovedEvent;
import com.max.harrax.graphicsOLD.Colour;
import com.max.harrax.graphicsOLD.Renderer;
import com.max.harrax.graphicsOLD.renderables.Label;
import com.max.harrax.graphicsOLD.text.Font;

public class DebugLayer extends Layer {
	
	Renderer renderer;
	
	public DebugLayer() {
		super("Debug");
		
		renderer = new Renderer();
		
	}

	@Override
	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.EventType.KeyPressed, (Event e) -> (onKeyboardPress((KeyPressedEvent) e)));
		dispatcher.dispatch(Event.EventType.MouseButtonPressed, (Event e) -> (onMousePressed((MouseButtonPressedEvent) e)));
		//dispatcher.dispatch(Event.EventType.MouseButtonReleased, (Event e) -> (onMouseReleased((MouseButtonReleasedEvent) e)));
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
		System.out.println(e.getPos().toString());
		
		return true;
	}

	@Override
	public void onUpdate() {

		renderer.begin();
		
		renderer.submitText(Font.DEBUG_FONT, 20, 20, Colour.GREEN, "Hellow, World!", 1.0f);
		
		renderer.end();
		
	}

	@Override
	public void onAttach() {
		System.out.println(name + " layer added to the layerstack.");
	}

	@Override
	public void onDetach() {
		System.out.println(name + " layer removed from the layerstack.");
	}
}
