package com.max.harrax.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import com.max.harrax.maths.Vec2;

public class MousePosHandler extends GLFWCursorPosCallback{

	private static double mouseX;
	private static double mouseY;

	@Override
	public void invoke(long window, double x, double y) {
		mouseX = x;
		mouseY = y;
	}

	public static Vec2 getMousePosition() {
		return new Vec2((float) mouseX, (float) mouseY);
	}

	public static double getMouseX() {
		return mouseX;
	}

	public static double getMouseY() {
		return mouseY;
	}

}
