package com.max.sandbox;

import com.max.harrax.Application;
import com.max.harrax.events.Event;
import com.max.harrax.events.Event.EventType;
import com.max.harrax.events.EventDispatcher;
import com.max.harrax.events.KeyCode;
import com.max.harrax.events.KeyPressedEvent;
import com.max.harrax.events.WindowResizeEvent;
import com.max.harrax.graphics.Colour;
import com.max.harrax.graphics.OrthographicCamera;
import com.max.harrax.graphics.Renderer;
import com.max.harrax.layer.Layer;

import org.joml.Math;
import org.joml.Vector2f;

public class SandboxLayer extends Layer {

    private Renderer renderer;
    private OrthographicCamera camera;

    private float time;
    private Vector2f position;

    public void onAttach() {

        int width = Application.get().getWindow().getWidth();
        int height = Application.get().getWindow().getHeight();
        float aspect = (float) width / (float) height;

        renderer = new Renderer();
        camera = new OrthographicCamera(0f, 9f * aspect, 0f, 9f);

        time = 0f;
        position = new Vector2f();
    }

    public void onDetach() {

    }

    public void onUpdate(float delta) {

        time += delta;
        position.set(Math.sin(time) * 3f + 8, Math.cos(time) * 3f + 4);

        renderer.beginScene(camera);

        renderer.submitLight(new Vector2f(3, 3), 2, Colour.RED);
        renderer.submitLight(position, 4, Colour.BLUE);

        renderer.submitQuad(position.x, 4f, 2f, 2f, Colour.GREEN);
        renderer.submitQuad(6f, 3f, position.y, 2f, new Colour(0.8f, 0.6f, 0.5f, 0.8f));
        renderer.submitQuad(6f, position.y, 1.5f, 3f, new Colour(0.5f, 0.4f, 0.9f, 0.5f));
        renderer.submitLine(position, new Vector2f(12, 7), Colour.RED);
        renderer.submitLine(new Vector2f(2, 8), position, Colour.BLUE);
        renderer.endScene();
    }

    public void onDispose() {
        renderer.dispose();

    }

    public void onEvent(Event event) {
        EventDispatcher eventDispatcher = new EventDispatcher(event);
        eventDispatcher.dispatch(EventType.WindowResize, e -> onWindowResize((WindowResizeEvent) e));
        eventDispatcher.dispatch(EventType.KeyPressed, e -> onKeyPressed((KeyPressedEvent) e));
    }

    private boolean onWindowResize(WindowResizeEvent e) {

        float width = e.getWidth();
        float height = e.getHeight();
        float aspect = width / height;

        camera.set(0f, 9f * aspect, 0f, 9f);

        return false;
    }

    private boolean onKeyPressed(KeyPressedEvent e) {

        if (e.getKeyCode() == KeyCode.Escape) {
            Application.get().shutdown();
        }

        return false;
    }

}
