package game;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import graphics.Colour;
import graphics.Renderer;
import graphics.Window;
import graphics.layers.Layer;
import graphics.renderables.Label;
import graphics.text.Font;
import state.StateHandler;
import state.TestState;
import utils.Timer;

public class Game {

	private static final int TARGET_UPS = 60;
	private static final float TARGET_DELTA = 1f / TARGET_UPS;

	private GLFWErrorCallback errorCallback;

	private boolean running;

	private Window window;

	private StateHandler state;
	
	private Timer timer;
	
	Layer debugLayer;
	Label fpsLabel;
	Label upsLabel;

	public Game() {
		timer = new Timer();
		state = new StateHandler();
	}

	public void start() {
		init();
		loop();
		dispose();
	}

	private void init() {
		errorCallback = GLFWErrorCallback.createPrint();
		glfwSetErrorCallback(errorCallback);

		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW!");
		}

		window = new Window();
		
		glfwSetKeyCallback(window.getID(), (window, key, scancode, action, mods) -> {
			if ( key == GLFW.GLFW_KEY_P && action == GLFW.GLFW_RELEASE )
				state.pause();
		});
		
		timer.init();
		
		debugLayer = new Layer(800, 600);
		fpsLabel = new Label(Font.DEBUG_FONT, "fps", Colour.WHITE, Label.ALIGNMENT.LEFT, Label.ALIGNMENT.TOP, 5);
		upsLabel = new Label(Font.DEBUG_FONT, "ups", Colour.WHITE, Label.ALIGNMENT.LEFT, Label.ALIGNMENT.TOP, 5);
		debugLayer.add(fpsLabel);
		debugLayer.add(upsLabel);

		//state.add("game", new GameState());
		//state.change("game");
		
		state.add("test", new TestState());
		state.change("test");

		running = true;
	}

	private void dispose() {
		/* Set empty state to trigger the exit method in the current state */
		state.change(null);
		state.dispose();
		
		debugLayer.dispose();

		/* Release window and its callbacks */
		window.destroy();

		/* Terminate GLFW and release the error callback */
		glfwTerminate();
		errorCallback.free();
	}

	public void loop() {
		float delta;
		float accumulator = 0f;

		while (running) {
			if (window.isClosing()) {
				running = false;
			}
			
			delta = timer.getDelta();
            accumulator += delta;

			// Update
			while (accumulator >= TARGET_DELTA) {
				state.update(TARGET_DELTA);
				timer.updateUPS();
				fpsLabel.setText("fps " + timer.getFPS());
				upsLabel.setText("ups " + timer.getUPS());
				accumulator -= TARGET_DELTA;
			}

			state.render();
			timer.updateFPS();
			
			debugLayer.render();

            timer.update();
			
			window.update();
			Renderer.clear();
		}
	}
}
