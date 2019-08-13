package com.max.harrax;

import java.util.Stack;

import com.max.harrax.events.Event;
import com.max.harrax.events.EventDispatcher;
import com.max.harrax.events.WindowCloseEvent;
import com.max.harrax.events.Event.EventType;
import com.max.harrax.graphics.Window;
import com.max.harrax.layers.DebugLayer;
import com.max.harrax.layers.Layer;
import com.max.harrax.utils.Timer;

public class Application {

	/*
	 * Variables for The target update rate The target time (delta) between updates
	 */
	private static final int TARGET_UPS = 60;
	private static final float TARGET_DELTA = 1f / TARGET_UPS;

	private static Application appInstance;
	
	private Stack<Layer> layerStack;

	private Window window;
	
	// Time to keep track of updates
	private Timer timer;
	
	// The pause state of the game
	private boolean running;
	
	// Constructor
	public Application() {
		
		assert appInstance != null : "There should only be one instance of application.";
		appInstance = this;
		
		timer = new Timer();
		window = new Window();
		layerStack = new Stack<Layer>();
		
		pushLayer(new DebugLayer());
		
		running = true;
	}
	
	public static Application get() {
		return appInstance;
	}

	public void pushLayer(Layer layer) {
		this.layerStack.push(layer);
	}

	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(EventType.WindowClose, (Event e) -> (onWindowClose((WindowCloseEvent)e)));
		
		for (int i = layerStack.size() - 1; i >= 0; i--)
		{
			layerStack.get(i).onEvent(event);
			if (event.isHandled)
				break;
		}
	}

	public void run() {
		float accumulator = 0f;

		while (running) {
			
			timer.update();

			float delta = timer.getDelta();
			accumulator += delta;

			// Update
			while (accumulator >= TARGET_DELTA) {
				accumulator -= TARGET_DELTA;
				
				for (Layer layer : layerStack) {
					layer.onUpdate();
				}
			}

			window.onUpdate();
		}
		
		window.shutdown();
	}
	
	public boolean onWindowClose(WindowCloseEvent event) {
		running = false;
		return true;
	}
}
