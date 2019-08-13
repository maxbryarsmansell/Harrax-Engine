package com.max.harrax.graphics;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWWindowCloseCallback;

import com.max.harrax.events.Event;
import com.max.harrax.events.KeyPressedEvent;
import com.max.harrax.events.WindowCloseEvent;
import com.max.harrax.game.Application;
import com.max.harrax.utils.Property;

public class Window {

	private String title;
	private int width, height;

	private final long window;
	
	
	GLFWErrorCallback errorCallback;
	GLFWWindowCloseCallback windowCloseCallback;
	GLFWKeyCallback keyCallback;

	public Window() {
		System.out.println("Window initilisation started.");
		System.out.println("LWJGL Version: " + Version.getVersion() + ".");

		// Loading window properties
		this.title = Property.loadProperty("title", "window");
		this.width = Integer.parseInt(Property.loadProperty("width", "window"));
		this.height = Integer.parseInt(Property.loadProperty("height", "window"));

		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW!");

		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

		glfwDefaultWindowHints();
		window = glfwCreateWindow(width, height, title, NULL, NULL);

		glfwMakeContextCurrent(window);
		setVsync(false);

		glfwSetWindowCloseCallback(window, windowCloseCallback = GLFWWindowCloseCallback.create((window) -> {
			Event event = new WindowCloseEvent();
			Application.get().onEvent(event);
		}));

		glfwSetKeyCallback(window, GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
			switch (action) {
			case GLFW_PRESS:
				Event event = new KeyPressedEvent(key);
				Application.get().onEvent(event);
				break;
			}

		}));

		// glfwSetCursorPosCallback(window.getID(), cursorPosCallback = new
		// MousePosHandler());
		// glfwSetMouseButtonCallback(window.getID(), mouseButtonCallback = new
		// MouseButtonHandler());

		System.out.println("Window and OpenGL initilisation successful.");
	}

	public void onUpdate() {
		glfwPollEvents();
		glfwSwapBuffers(window);
	}

	public void shutdown() {
		glfwDestroyWindow(window);
		glfwTerminate();
	}

	public void setVsync(boolean state) {
		glfwSwapInterval(state ? 1 : 0);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
