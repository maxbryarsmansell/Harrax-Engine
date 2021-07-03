package com.max.harrax;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_RENDERER;
import static org.lwjgl.opengl.GL11.GL_VENDOR;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glGetString;

import com.max.harrax.events.Event;
import com.max.harrax.events.KeyPressedEvent;
import com.max.harrax.events.KeyReleasedEvent;
import com.max.harrax.events.MouseButtonPressedEvent;
import com.max.harrax.events.MouseButtonReleasedEvent;
import com.max.harrax.events.MouseMovedEvent;
import com.max.harrax.events.WindowCloseEvent;
import com.max.harrax.events.WindowResizeEvent;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWWindowCloseCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

public class Window {

    private String title;
    private int width, height;

    private final long window;

    GLFWErrorCallback errorCallback;
    GLFWWindowCloseCallback windowCloseCallback;
    GLFWWindowSizeCallback windowSizeCallback;
    GLFWKeyCallback keyCallback;
    GLFWMouseButtonCallback mouseButtonCallback;
    GLFWCursorPosCallback cursorPosCallback;

    public Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW!");

        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

        glfwDefaultWindowHints();

        window = glfwCreateWindow(width, height, title, 0, 0);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        String vendor = glGetString(GL_VENDOR);
        String renderer = glGetString(GL_RENDERER);
        String version = glGetString(GL_VERSION);

        System.out.printf("[Vendor]: %s\n", vendor);
        System.out.printf("[Renderer]: %s\n", renderer);
        System.out.printf("[Version]: %s\n", version);

        glfwSetWindowCloseCallback(window, windowCloseCallback = GLFWWindowCloseCallback.create((window) -> {
            Event event = new WindowCloseEvent();
            Application.get().onEvent(event);
        }));

        glfwSetKeyCallback(window, keyCallback = GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
            switch (action) {
                case GLFW_PRESS:
                    Application.get().onEvent(new KeyPressedEvent(key));
                    break;
                case GLFW_RELEASE:
                    Application.get().onEvent(new KeyReleasedEvent(key));
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

        glfwSetWindowSizeCallback(window, windowSizeCallback = GLFWWindowSizeCallback.create((window, w, h) -> {
            Event event = new WindowResizeEvent(w, h);
            Application.get().onEvent(event);
        }));
    }

    public void onUpdate() {
        glfwPollEvents();
        glfwSwapBuffers(window);
    }

    public void dispose() {
        errorCallback.free();
        windowCloseCallback.free();
        keyCallback.free();
        mouseButtonCallback.free();
        cursorPosCallback.free();

        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
