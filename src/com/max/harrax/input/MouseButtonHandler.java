package com.max.harrax.input;

import org.lwjgl.glfw.GLFWMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseButtonHandler extends GLFWMouseButtonCallback {

	public static boolean[] buttons = new boolean[8];

	@Override
	public void invoke(long window, int button, int action, int mods) {
		buttons[button] = action != GLFW_RELEASE;
	}

	public static boolean isButtonDown(int button) {
		return buttons[button];
	}

}
