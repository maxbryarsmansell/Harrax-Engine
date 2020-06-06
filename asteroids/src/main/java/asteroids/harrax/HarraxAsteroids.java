package asteroids.harrax;

import asteroids.Asteroids;
import asteroids.Vector;
import asteroids.Views;
import com.max.harrax.Application;
import com.max.harrax.events.*;
import com.max.harrax.events.Event;
import com.max.harrax.graphics.Colour;
import com.max.harrax.layer.Layer;
import com.max.harrax.maths.Vec2;
import com.max.harrax.maths.Vec3;
import com.max.harrax.maths.Vec4;
import org.lwjgl.glfw.GLFW;
import org.magnos.entity.Ents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class HarraxAsteroids extends Layer
{
	public static void main(String[] args)
	{
		Application app = new Application("Asteroids", WIDTH, HEIGHT);
		app.pushLayer(new HarraxAsteroids());
		app.run();
	}

	public static final float SHIP_SPEED = 300;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	public Random random = new Random();
	public Asteroids a;

	com.max.harrax.graphics.Renderer renderer;
	com.max.harrax.graphics.OrthographicCamera camera;

	public HarraxAsteroids() {
		super("Asteroids");

		renderer = new com.max.harrax.graphics.Renderer();
		camera = new com.max.harrax.graphics.OrthographicCamera(0, WIDTH, 0, HEIGHT);
	}

	private boolean onKeyPressed(KeyPressedEvent e) {

		if (e.getKeyCode() == GLFW.GLFW_KEY_D)
			a.shipForce.x = SHIP_SPEED;
		else if (e.getKeyCode() == GLFW.GLFW_KEY_A)
			a.shipForce.x = -SHIP_SPEED;
		else if (e.getKeyCode() == GLFW.GLFW_KEY_W)
			a.shipForce.y = SHIP_SPEED;
		else if (e.getKeyCode() == GLFW.GLFW_KEY_S)
			a.shipForce.y = -SHIP_SPEED;
		else if (e.getKeyCode() == GLFW.GLFW_KEY_SPACE)
			a.shooting = true;
		else if (e.getKeyCode() == GLFW.GLFW_KEY_B)
			a.shipBrakes = true;
		else if (e.getKeyCode() == GLFW.GLFW_KEY_ESCAPE)
			Application.get().shutdown();

		return false;
	}

	private boolean onKeyReleased(KeyReleasedEvent e) {

		if (e.getKeyCode() == GLFW.GLFW_KEY_D)
			a.shipForce.x = 0;
		else if (e.getKeyCode() == GLFW.GLFW_KEY_A)
			a.shipForce.x = 0;
		else if (e.getKeyCode() == GLFW.GLFW_KEY_W)
			a.shipForce.y = 0;
		else if (e.getKeyCode() == GLFW.GLFW_KEY_S)
			a.shipForce.y = 0;
		else if (e.getKeyCode() == GLFW.GLFW_KEY_SPACE)
			a.shooting = false;
		else if (e.getKeyCode() == GLFW.GLFW_KEY_B)
			a.shipBrakes = false;

		return false;
	}

	private boolean onMouseMoved(MouseMovedEvent e) {

		a.shipLook.set( e.getMouseX(), HEIGHT - e.getMouseY());

		return false;
	}

	@Override
	public void onAttach() {
		Ents.setViewDefault( Views.SHIP, new HarraxShipRenderer());
		Ents.setViewDefault( Views.ASTEROID, new HarraxAsteroidRenderer(0.3f, 20, random ) );
		Ents.setViewDefault( Views.LASER, new HarraxLaserRenderer( 0.1f ) );
		Ents.setViewDefault( Views.PARTICLE, new HarraxParticleRenderer() );

		a = new Asteroids();
		a.random = random;
		a.shipBrakes = true;
		a.setSize( WIDTH, HEIGHT );
		a.reset();
		a.spawn( Asteroids.INITIAL_SPAWN_COUNT );
	}

	@Override
	public void onDetach() {
	}

	@Override
	public void onUpdate(float delta) {

		a.update(delta);

		renderer.beginScene(camera);
		renderer.submitQuad(0,0, 1280, 720, new Colour(0.5f, 0.5f, 0.5f, 0.2f));
		a.draw(renderer);
		renderer.endScene();
	}

	@Override
	public void dispose() {
		a.delete();
		renderer.dispose();
	}

	@Override
	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.EventType.KeyPressed, e -> onKeyPressed((KeyPressedEvent)e));
		dispatcher.dispatch(Event.EventType.KeyReleased, e -> onKeyReleased((KeyReleasedEvent) e));
		dispatcher.dispatch(Event.EventType.MouseMoved, e -> onMouseMoved((MouseMovedEvent) e));
	}
}
