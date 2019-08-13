package com.max.harrax.graphics.layers;

import com.max.harrax.events.Event;
import com.max.harrax.events.EventDispatcher;
import com.max.harrax.events.KeyPressedEvent;
import com.max.harrax.graphics.renderables.Label;

public class DebugLayer extends Layer {

	Label fpsLabel, upsLabel;
	
	public DebugLayer() {
		super("Debug");
		
		//fpsLabel = new Label(Font.DEBUG_FONT, "fps", Colour.BLUE, Label.ALIGNMENT.RIGHT, Label.ALIGNMENT.TOP, 5);

		//upsLabel = new Label(Font.DEBUG_FONT, "ups", Colour.BLUE, Label.ALIGNMENT.RIGHT, Label.ALIGNMENT.TOP, 5);
	}

	@Override
	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.EventType.KeyPressed, (Event e) -> (onKeyboardPress((KeyPressedEvent) e)));
	}

	private boolean onKeyboardPress(KeyPressedEvent e) {
		System.out.println((char)e.getKeyCode());
		
		return true;
	}

	@Override
	public void onRender() {

	}

	@Override
	public void onUpdate() {

	}
}
