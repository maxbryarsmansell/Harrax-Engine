package com.max.harrax;

import com.max.harrax.events.*;
import org.lwjgl.Version;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public class Window {
    private String title;
    private int width, height;
    private boolean vSync;

    private final long window;

    GLFWErrorCallback errorCallback;
    GLFWWindowCloseCallback windowCloseCallback;
    GLFWWindowSizeCallback windowSizeCallback;
    GLFWKeyCallback keyCallback;
    GLFWMouseButtonCallback mouseButtonCallback;
    GLFWCursorPosCallback cursorPosCallback;

    public Window(String title, int width, int height) {
        System.out.println("Window initilisation started.");
        System.out.println("LWJGL Version: " + Version.getVersion() + ".");

        this.title = title;
        this.width = width;
        this.height = height;

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW!");

        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
        window = glfwCreateWindow(width, height, title, 0, 0);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        setVsync(vSync);

        glfwSetWindowCloseCallback(window, windowCloseCallback = GLFWWindowCloseCallback.create((window) ->
        {
            Event event = new WindowCloseEvent();
            Application.get().onEvent(event);
        }));

        glfwSetKeyCallback(window, keyCallback = GLFWKeyCallback.create((window, key, scancode, action, mods) ->
        {
            switch (action) {
                case GLFW_PRESS:
                    Application.get().onEvent(new KeyPressedEvent(key));
                    break;
                case GLFW_RELEASE:
                    Application.get().onEvent(new KeyReleasedEvent(key));
                    break;
            }
        }));

        glfwSetMouseButtonCallback(window, mouseButtonCallback = GLFWMouseButtonCallback.create((window, button, action, mods) ->
        {
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

        glfwSetCursorPosCallback(window, cursorPosCallback = GLFWCursorPosCallback.create((window, xpos, ypos) ->
        {
            Event event = new MouseMovedEvent((float) xpos, (float) ypos);
            Application.get().onEvent(event);
        }));

        glfwSetWindowSizeCallback(window, windowSizeCallback = GLFWWindowSizeCallback.create((window, w, h) ->
        {
            Event event = new WindowResizeEvent(w, h);
            Application.get().onEvent(event);
        }));

        System.out.println("GLFW has created a window successfully.");
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

    public void setVsync(boolean state) {
        vSync = state;
        glfwSwapInterval(state ? 1 : 0);
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

    public int getNativeWindow() {
        return height;
    }
}
