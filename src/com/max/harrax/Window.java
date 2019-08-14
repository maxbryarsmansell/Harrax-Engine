package com.max.harrax;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.Functions.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.*;

import com.max.harrax.events.*;
import com.max.harrax.utils.Property;

public class Window {

	private String title;
	private int width, height;

	private final long window;

	GLFWErrorCallback errorCallback;
	GLFWWindowCloseCallback windowCloseCallback;
	GLFWKeyCallback keyCallback;
	GLFWMouseButtonCallback mouseButtonCallback;
	GLFWCursorPosCallback cursorPosCallback;

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
		window = glfwCreateWindow(width, height, title, 0, 0);

		glfwMakeContextCurrent(window);
		
		GL.createCapabilities();
		
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

		glfwSetMouseButtonCallback(window,
				mouseButtonCallback = GLFWMouseButtonCallback.create((window, button, action, mods) -> {
					Event event;
					switch (action) {
					case GLFW_PRESS:
						event = new MouseButtonPressedEvent(button);
						Application.get().onEvent(event);
						break;
					case GLFW_RELEASE:
						event = new MouseButtonReleasedEvent(button);
						Application.get().onEvent(event);
						break;
					}
				}));

		glfwSetCursorPosCallback(window, cursorPosCallback = GLFWCursorPosCallback.create((window, xpos, ypos) -> {
			Event event = new MouseMovedEvent((float) xpos, (float) ypos);
			Application.get().onEvent(event);
		}));

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
