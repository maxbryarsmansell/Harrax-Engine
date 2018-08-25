package graphics;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.Version;
import utils.Property;

public class Window {
	
	/*
	 * Attributes for 
	 * the window handle (id)
	 * Window width and height
	 * Title of the window
	 */

	private final long id;
	private int width, height;
	private String title;

	public Window() {
		System.out.println("Initilisation started.");
		System.out.println("LWJGL Version: " + Version.getVersion() + ".");
		
		// Loading window properties

		this.title = Property.loadProperty("title", "window");
		this.width = Integer.parseInt(Property.loadProperty("width", "window"));
		this.height = Integer.parseInt(Property.loadProperty("height", "window"));

		// Creating a temporary window for getting the available OpenGL version
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		long temp = glfwCreateWindow(1, 1, "", NULL, NULL);
		glfwMakeContextCurrent(temp);
		GL.createCapabilities();
		GLCapabilities caps = GL.getCapabilities();
		glfwDestroyWindow(temp);
		
		// Checking for the version of OpenGL
		glfwDefaultWindowHints();
		if (caps.OpenGL32) {
			// Hints for OpenGL 3.2 core profile 
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		} else if (caps.OpenGL21) {
			// Hints for legacy OpenGL 2.1
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
		} else {
			throw new RuntimeException("Neither OpenGL 3.2 nor OpenGL 2.1 is supported.");
		}

		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

		// Create window with specified OpenGL context
		id = glfwCreateWindow(width, height, title, NULL, NULL);
		if (id == NULL) {
			glfwTerminate();
			throw new RuntimeException("Failed to create the GLFW window.");
		}

		// Make the OpenGL context current
		glfwMakeContextCurrent(id);

		// Make the window visible
		glfwShowWindow(id);
		
		// Use V-Sync
		glfwSwapInterval(0);

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
	
	public long getID() {
		return id;
	}

}
