package com.max.harrax;

import java.util.ListIterator;

import com.max.harrax.events.Event;
import com.max.harrax.events.Event.EventType;
import com.max.harrax.events.EventDispatcher;
import com.max.harrax.events.WindowCloseEvent;
import com.max.harrax.graphics.Renderer;
import com.max.harrax.layer.DebugLayer;
import com.max.harrax.layer.Layer;
import com.max.harrax.layer.LayerStack;
import com.max.harrax.utils.Timer;

public class Application {

	private static Application appInstance;

	private LayerStack layerStack;
	private Window window;
	private Timer timer;

	private boolean running;

	// Constructor
	public Application() {

		assert (appInstance != null) : "There should only be one instance of application.";

		appInstance = this;

		timer = new Timer();
		window = new Window();
		window.setVsync(true);
		layerStack = new LayerStack();

		pushLayer(new DebugLayer());
		
		running = true;
	}

	public static Application get() {
		return appInstance;
	}

	public void pushLayer(Layer layer) {
		this.layerStack.pushLayer(layer);
	}

	public void popLayer(Layer layer) {
		this.layerStack.popLayer(layer);
	}

	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(EventType.WindowClose, (Event e) -> (onWindowClose((WindowCloseEvent) e)));
		
		ListIterator<Layer> iterator = layerStack.end();
		while (iterator.hasPrevious()) {
			iterator.previous().onEvent(event);
			if (event.isHandled)
				break;
		}
	}

	public void run() {
		
		float acc = 0.0f;
		while (running) {
			Renderer.clear();
			
			timer.update();
			float delta = timer.getDelta();
			
			acc += delta;
			if(acc >= 1.0f) {
				acc = 0.0f;
				System.out.println(timer.getUps() + " ups.");
			}
			
			ListIterator<Layer> iterator = layerStack.start();

			while (iterator.hasNext()) {
				iterator.next().onUpdate(delta);
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
