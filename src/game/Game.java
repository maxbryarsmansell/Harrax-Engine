package game;

import graphics.Colour;
import graphics.Renderer;
import graphics.Window;
import graphics.layers.Layer;
import graphics.renderables.Group;
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

	private boolean running;
	private GLFWErrorCallback errorCallback;

	float mouseWheelClicks = 0;
	float mouseWheel = 0.01f;
	Vec2 prevMouse = new Vec2(0, 0);

	public void start() {
		init();
		gameLoop();
		dispose();
	}

	public void gameLoop() {
		double lastTime = glfwGetTime();
		double accumulator = 0f;
		double interval = 1f / 60f;
		int fps = 0;

		Random rand = new Random();

		Layer ui = new Layer();
		Layer layer = new Layer();
		
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		for (int i = 0; i < 10000; i++) {
			sprites.add(new Sprite(rand.nextFloat(), rand.nextFloat(), 5, 5, new Colour(rand.nextFloat(), 0.0f, 1.0f)));
			layer.add(sprites.get(i));
		}


		Label label = new Label(font, "fps", -395, 255, Colour.WHITE, new Colour(0.8f, 0.8f, 0.8f, 0.5f));
		ui.add(label);
		ui.add(new Label(font, "Scroll To Zoom", -400, -300, new Colour(), new Colour()));

		while (running) {
			// Check if game should close
			if (window.isClosing()) {
				running = false;
			}

			layer.render();
			ui.render();

			double currentTime = glfwGetTime();
			double delta = currentTime - lastTime;
			lastTime = currentTime;
			accumulator += delta;

			while (accumulator >= interval) {
				fps = (int) Math.round(1 / delta);
				String fpsLabel = fps + "fps";
				label.setText(fpsLabel);

				layer.getCamera().setZoom(5 * mouseWheel);
				layer.getCamera().setPosition(new Vec2(getCursorPosX(window.getID()), getCursorPosY(window.getID())));

				for (int i = 0; i < sprites.size(); i++) {
					sprites.get(i).getPosition().Add(new Vec4(8 * rand.nextFloat() - 4, 8 * rand.nextFloat() - 4, 0f, 1f));
					sprites.get(i).getPosition().Multiply(Mat4.zAxisRotation(rand.nextFloat() / 10));
				}
				accumulator -= interval;
			}

			window.update();
			Renderer.clear();
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

		GLFWScrollCallback c = GLFWScrollCallback.create(this::glfwScrollCallback);
		glfwSetScrollCallback(window.getID(), c);

		/* Initialize renderer */

		font = new Font(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.BOLD, 30), true);

		/* Initializing done, set running to true */
		running = true;
	}

	public void glfwScrollCallback(long window, double xoffset, double yoffset) {
		float max = 100;
		float min = 1;

		mouseWheelClicks += (float) yoffset;

		if (mouseWheelClicks < min) {
			mouseWheelClicks = min;
		}
		if (mouseWheelClicks > max) {
			mouseWheelClicks = max;
		}

		mouseWheel = mouseWheelClicks / max;

	}

	public void dispose() {

		/* Release window and its callbacks */
		window.destroy();
		font.dispose();

		/* Terminate GLFW and release the error callback */
		glfwTerminate();
	}

}
