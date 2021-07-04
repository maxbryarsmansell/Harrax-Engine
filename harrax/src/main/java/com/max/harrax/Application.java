package com.max.harrax;

import java.util.ListIterator;

import com.max.harrax.events.Event;
import com.max.harrax.events.Event.EventType;
import com.max.harrax.events.EventDispatcher;
import com.max.harrax.events.WindowCloseEvent;
import com.max.harrax.events.WindowResizeEvent;
import com.max.harrax.graphics.Renderer;
import com.max.harrax.layer.Layer;
import com.max.harrax.layer.LayerStack;
import com.max.harrax.utils.Timer;

public class Application {

    private static Application application;

    public static Application get() {
        return application;
    }

    private LayerStack layerStack;
    private Window window;
    private Timer timer;

    private boolean running;

    public Application() {
        this("Harrax", 1280, 720);
    }

    public Application(String title, int width, int height) {
        if (application == null)
            application = this;
        else
            return;

        window = new Window(title, width, height);
        timer = new Timer();
        layerStack = new LayerStack();
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
        dispatcher.dispatch(Event.EventType.WindowResize, (Event e) -> (onWindowResize((WindowResizeEvent) e)));

        ListIterator<Layer> iterator = layerStack.end();
        while (iterator.hasPrevious()) {
            iterator.previous().onEvent(event);
            if (event.isHandled)
                break;
        }
    }

    public void run() {
        running = true;

        float acc = 0.0f;

        while (running) {
            Renderer.clear();

            timer.onUpdate();
            float delta = timer.getDelta();

            acc += delta;
            if (acc >= 1.0f) {
                acc = 0.0f;
                System.out.println(timer.getUps() + " ups.");
            }

            ListIterator<Layer> iterator = layerStack.start();
            while (iterator.hasNext()) {
                Layer layer = iterator.next();
                
                layer.onUpdate(delta);
            }

            window.onUpdate();
        }

        running = false;

        layerStack.dispose();
        window.dispose();
    }

    public void shutdown() {
        running = false;
    }

    private boolean onWindowResize(WindowResizeEvent event) {
        Renderer.setViewportSize(event.getWidth(), event.getHeight());
        return false;
    }

    private boolean onWindowClose(WindowCloseEvent event) {
        running = false;
        return false;
    }

    public Window getWindow() {
        return window;
    }
}
