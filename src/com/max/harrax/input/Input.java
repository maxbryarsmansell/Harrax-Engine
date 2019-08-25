package com.max.harrax.input;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;

import org.lwjgl.system.MemoryStack;

import com.max.harrax.Application;
import com.max.harrax.maths.Vec2;

public class Input {

	public static boolean[] keys = new boolean[1024];

	public static boolean isKeyDown(int key) {
		long window = Application.get().getWindow().getNativeWindow();
		int state = glfwGetKey(window, key);
		return state == GLFW_PRESS || state == GLFW_REPEAT;
	}
	
	public static Vec2 mousePosition() {
		long window = Application.get().getWindow().getNativeWindow();
		Vec2 ret = new Vec2();
		try (MemoryStack stack = MemoryStack.stackPush()){
			DoubleBuffer xpos = stack.mallocDouble(1);
			DoubleBuffer ypos = stack.mallocDouble(1);
			glfwGetCursorPos(window, xpos, ypos);
			ret.x = (float) xpos.get();
			ret.y = (float) ypos.get();
		}
		return ret;
	}

}
