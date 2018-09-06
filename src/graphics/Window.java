package graphics;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLCapabilities;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import utils.Property;

public class Window {

	/*
	 * Attributes for the window handle (id) Window width and height Title of the
	 * window
	 */

	private final long id;
	private int width, height;
	private float aspect;
	private String title;
	private boolean fullscreen;

	public Window() {
		System.out.println("Initilisation started.");

		// Creating a temporary window for getting the available OpenGL version
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		long temp = glfwCreateWindow(1, 1, "", NULL, NULL);
		glfwMakeContextCurrent(temp);
		createCapabilities();
		GLCapabilities caps = getCapabilities();
		glfwDestroyWindow(temp);

		// Checking for the version of OpenGL
		glfwDefaultWindowHints();
		if (caps.OpenGL32) {
			// Hints for OpenGL 3.2 core profile
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		} else {
			throw new RuntimeException("OpenGL 3.2 is not supported.");
		}

		// Loading window properties

		this.title = Property.loadProperty("title", "window");
		this.fullscreen = Boolean.parseBoolean(Property.loadProperty("fullscreen", "window"));

		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		
		// Create window with specified OpenGL context
		if (fullscreen) {
			long monitor = glfwGetPrimaryMonitor();
			GLFWVidMode mode = glfwGetVideoMode(monitor);
			glfwWindowHint(GLFW.GLFW_RED_BITS, mode.redBits());
			glfwWindowHint(GLFW.GLFW_GREEN_BITS, mode.greenBits());
			glfwWindowHint(GLFW.GLFW_BLUE_BITS, mode.blueBits());
			glfwWindowHint(GLFW.GLFW_REFRESH_RATE, mode.refreshRate());
			this.width = mode.width();
			this.height = mode.height();
			this.aspect = width / height;
			id = glfwCreateWindow(width, height, title, monitor, NULL);
		} else {
			this.width = Integer.parseInt(Property.loadProperty("width", "window"));
			this.height = Integer.parseInt(Property.loadProperty("height", "window"));
			this.aspect = width / height;
			id = glfwCreateWindow(width, height, title, NULL, NULL);
		}

		if (id == NULL) {
			glfwTerminate();
			throw new RuntimeException("Failed to create the GLFW window.");
		}

		// glfwSetInputMode(id, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

		// Make the OpenGL context current
		glfwMakeContextCurrent(id);

		// Make the window visible
		glfwShowWindow(id);

		// Use V-Sync
		glfwSwapInterval(0);

		String version = glGetString(GL_VERSION);
		String vendor = glGetString(GL_VENDOR);
		String gpuModel = glGetString(GL_RENDERER);
		String shaderVersion = glGetString(GL_SHADING_LANGUAGE_VERSION);

		System.out.println("--------------------------");
		System.out.println("LWJGL Version: " + Version.getVersion() + ".");
		System.out.println("OpenGL Version: " + version);
		System.out.println("Vendor: " + vendor);
		System.out.println("GPU: " + gpuModel);
		System.out.println("GLSL Version: " + shaderVersion);
		System.out.println("--------------------------");

		System.out.println("Window and OpenGL initilisation successful.");
	}

	/*
	 * Function which updates the window
	 */

	public void update() {
		glfwSwapBuffers(id);
		glfwPollEvents();
	}

	/*
	 * Function which checks if the window is closing
	 */

	public boolean isClosing() {
		return glfwWindowShouldClose(id);
	}

	/*
	 * Function which destroys the window
	 */

	public void destroy() {
		glfwDestroyWindow(id);
	}

	/*
	 * Get width of the window
	 */

	public int getWidth() {
		return width;
	}

	/*
	 * Get height of the window
	 */

	public int getHeight() {
		return height;
	}
	
	public float getAspect() {
		return aspect;
	}

	public long getID() {
		return id;
	}

}
