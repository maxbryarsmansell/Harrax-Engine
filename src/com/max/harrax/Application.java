package com.max.harrax;

import java.util.ListIterator;
import java.util.Stack;

import org.lwjgl.glfw.GLFW;

import com.max.harrax.events.Event;
import com.max.harrax.events.EventDispatcher;
import com.max.harrax.events.KeyPressedEvent;
import com.max.harrax.events.WindowCloseEvent;
import com.max.harrax.events.Event.EventType;
import com.max.harrax.layer.DebugLayer;
import com.max.harrax.layer.Layer;
import com.max.harrax.utils.Timer;

public class Application {

	/*
	 * Variables for The target update rate The target time (delta) between updates
	 */
	private static final int TARGET_UPS = 60;
	private static final float TARGET_DELTA = 1f / TARGET_UPS;

	private static Application appInstance;

	private DebugLayer debugLayer;
	private LayerStack layerStack;
	private Window window;
	private Timer timer;

	private boolean running;

	// Constructor
	public Application() {

		assert true : "There should only be one instance of application.";

		appInstance = this;

		timer = new Timer();
		window = new Window();
		layerStack = new LayerStack();

		debugLayer = new DebugLayer();
		pushLayer(debugLayer);

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
		float accumulator = 0f;

		while (running) {

			timer.update();

			float delta = timer.getDelta();
			accumulator += delta;

			// Update
			while (accumulator >= TARGET_DELTA) {
				accumulator -= TARGET_DELTA;

				ListIterator<Layer> iterator = layerStack.start();

				while (iterator.hasNext()) {
					iterator.next().onUpdate();
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
