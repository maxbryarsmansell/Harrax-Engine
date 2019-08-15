package com.max.harrax;

import static org.lwjgl.opengl.GL40.*;

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

		assert (appInstance != null) : "There should only be one instance of application.";

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
			Renderer.clear();
			timer.update();
			
			float delta = timer.getDelta();
			accumulator += delta;

			//glBegin(GL_TRIANGLES);
			//glVertex3f(-0.5f, -0.5f, 0.0f);   glColor3f(0.0f, 1.0f, 0.0f);
			//glVertex3f(0.8f, -0.5f, 0.0f);   glColor3f(0.0f, 1.0f, 0.8f);
			//glVertex3f(0.0f, 0.5f, 1.0f);   glColor3f(0.0f, 1.0f, 0.7f);
		    //glEnd();

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
