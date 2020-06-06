package com.max.harrax.utils;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Timer {

    private double lastLoopTime;

    private float timeCount;
    private int ups;
    private int upsCount;

    public void init() {
        lastLoopTime = getTime();
    }

    public double getTime() {
        return glfwGetTime();
    }

    public float getDelta() {
        double time = getTime();
        float delta = (float) (time - lastLoopTime);
        lastLoopTime = time;
        timeCount += delta;
        return delta;
    }

    public void onUpdate() {
        upsCount++;
        if (timeCount > 1f) {
            ups = upsCount;
            upsCount = 0;
            timeCount -= 1f;
        }
    }

    public int getUps() {
        return ups > 0 ? ups : upsCount;
    }

}
