package com.max.harrax;

import com.max.harrax.events.*;
import com.max.harrax.game.ecs.Entity;
import com.max.harrax.game.ecs.EntityManager;
import com.max.harrax.game.ecs.components.MeshComponent;
import com.max.harrax.game.ecs.components.PhysicsComponent;
import com.max.harrax.game.ecs.components.TransformComponent;
import com.max.harrax.game.ecs.systems.PhysicsSystem;
import com.max.harrax.game.ecs.systems.RenderSystem;
import com.max.harrax.graphics.Colour;
import com.max.harrax.graphics.OrthographicCamera;
import com.max.harrax.graphics.Renderer;
import com.max.harrax.layer.Layer;
import com.max.harrax.maths.Vec2;

import java.util.ArrayList;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class TestingLayer extends Layer {

    private static Vec2 centre = new Vec2(640, 360);
    private static Random r = new Random();

    private OrthographicCamera camera;

    private EntityManager manager;
    private Entity bird;
    private Entity pipe;

    public TestingLayer() {
        super("Testing Layer");
    }

    @Override
    public void onEvent(Event event) {
        EventDispatcher dispatcher = new EventDispatcher(event);

        dispatcher.dispatch(Event.EventType.KeyPressed, (Event e) -> (onKeyboardPress((KeyPressedEvent) e)));
        dispatcher.dispatch(Event.EventType.MouseButtonPressed,
                (Event e) -> (onMousePressed((MouseButtonPressedEvent) e)));
        dispatcher.dispatch(Event.EventType.MouseButtonReleased,
                (Event e) -> (onMouseReleased((MouseButtonReleasedEvent) e)));
        dispatcher.dispatch(Event.EventType.MouseMoved, (Event e) -> (onMouseMoved((MouseMovedEvent) e)));
    }

    private boolean onKeyboardPress(KeyPressedEvent e) {
        if (e.getKeyCode() == GLFW_KEY_SPACE) {
        }
        return true;
    }

    private boolean onMousePressed(MouseButtonPressedEvent e) {
        return true;
    }

    private boolean onMouseReleased(MouseButtonReleasedEvent e) {
        return true;
    }

    private boolean onMouseMoved(MouseMovedEvent e) {
        return true;
    }

    @Override
    public void onAttach() {
        camera = new OrthographicCamera(0, 1280, 0, 720);
        camera.setRotation(0f);

        manager = new EntityManager();
        manager.addSystem(new RenderSystem(camera));
        manager.addSystem(new PhysicsSystem());

        bird = manager.createEntity("Bird", new TransformComponent(100, 500, 50, 50), new MeshComponent(Colour.BLUE));
        pipe = manager.createEntity("Pipe", new TransformComponent(1000, 0, 75, 300), new MeshComponent(Colour.GREEN), new PhysicsComponent(new Vec2(-10, 0)));

        System.out.println(bird);
    }

    @Override
    public void onDetach() {
    }

    @Override
    public void onUpdate(float delta) {
        manager.onUpdate(delta);
    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}
