package game;

import graphics.Colour;
import graphics.Renderer;
import graphics.Window;
import graphics.layers.Layer;
import graphics.renderables.Label;
import graphics.renderables.Sprite;
import maths.Mat4;
import maths.Vec2;
import maths.Vec4;
import graphics.text.Font;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import org.lwjgl.glfw.GLFWScrollCallback;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Game {

	private Window window;
	private Font font;

	Label zoom;
	
	private boolean running;
	private GLFWErrorCallback errorCallback;

	float mouseWheel = 50;
	Vec2 prevMouse = new Vec2(0, 0);

	public void start() {
		init();
		gameLoop();
		dispose();
	}

	public void gameLoop() {
		double counter = 0;
		double currentTime = glfwGetTime();
		double lastTime = glfwGetTime();
		double delta = 0;
		double accumulator = 0f;
		double interval = 1f / 60f;
		int frames = 0;

		Random rand = new Random();

		Layer ui = new Layer();
		Layer layer = new Layer();

		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		for (int i = 0; i < 10000; i++) {
			sprites.add(new Sprite(rand.nextFloat(), rand.nextFloat(), 5, 5, new Colour(rand.nextFloat(), 0.0f, 1.0f)));
			layer.add(sprites.get(i));
		}

		Label label = new Label(font, "fps", -395, 255, Colour.WHITE, new Colour(0.8f, 0.8f, 0.8f, 0.5f));
		zoom = new Label(font, "Zoom: " + mouseWheel / 50, -400, -250, Colour.WHITE, new Colour(0.8f, 0.8f, 0.8f, 0.5f));
		ui.add(label);
		ui.add(new Label(font, "Scroll To Zoom", -400, -300, new Colour(), new Colour()));
		ui.add(zoom);

		while (running) {
			// Check if game should close
			if (window.isClosing()) {
				running = false;
			}

			currentTime = glfwGetTime();
			delta = currentTime - lastTime;
			lastTime = currentTime;
			accumulator += delta;
			counter += delta;

			if (counter > 1) {
				label.setText(frames + " fps");
				frames = 0;
				counter = 0;
			}

			while (accumulator >= interval) {
				layer.getCamera().setZoom(mouseWheel / 50);
				layer.getCamera().setPosition(new Vec2(getCursorPosX(window.getID()), getCursorPosY(window.getID())));
				for (int i = 0; i < sprites.size(); i++) {
					sprites.get(i).getPosition()
							.Add(new Vec4(8 * rand.nextFloat() - 4, 8 * rand.nextFloat() - 4, 0f, 1f));
					sprites.get(i).getPosition().Multiply(Mat4.zAxisRotation(rand.nextFloat() / 10));
				}
				accumulator -= interval;
			}

			layer.render();
			ui.render();

			window.update();
			Renderer.clear();

			frames++;
		}

		layer.dispose();
		ui.dispose();

	}

	public static float getCursorPosX(long windowID) {
		DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(windowID, posX, null);
		return (float) posX.get(0) - 400;
	}

	public static float getCursorPosY(long windowID) {
		DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(windowID, null, posY);
		return 300 - (float) posY.get(0);
	}

	public void init() {
		errorCallback = GLFWErrorCallback.createPrint();
		glfwSetErrorCallback(errorCallback);

		/* Initialize GLFW */
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW!");
		}

		/* Create GLFW window */
		window = new Window();

		glfwSetKeyCallback(window.getID(), (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true);
		});
		
		glfwSetScrollCallback(window.getID(), (window, xoffset, yoffset) -> {
			mouseWheel += (float) yoffset;
			if (mouseWheel <= 1) {
				mouseWheel = 1;
			}
			zoom.setText("Zoom: " + mouseWheel / 50);
		});

		/* Initialize renderer */

		font = new Font(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.BOLD, 30), true);

		/* Initializing done, set running to true */
		running = true;
	}

	public void dispose() {

		/* Release window and its callbacks */
		window.destroy();
		font.dispose();

		/* Terminate GLFW and release the error callback */
		glfwTerminate();
	}

}
