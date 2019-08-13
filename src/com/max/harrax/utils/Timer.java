package com.max.harrax.utils;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Timer {
	
	// The time that the last loop took place
    private double lastLoopTime;
    
    // Timer attribute used to keep track of time
    private float timeCount;
    
    // Attributes for storing the fps
    private int fps;
    private int fpsCount;
    
    // Attributes for storing the ups
    private int ups;
    private int upsCount;

    /*
     * Method for initialising the timer with the current time
     */
    
    public void init() {
        lastLoopTime = getTime();
    }

    /*
     * Method for getting the time since GLFW initialised 
     */
    
    private double getTime() {
        return glfwGetTime();
    }
    
    /*
     * Method for getting the time delta (time difference between loops)
     */

    public float getDelta() {
        double time = getTime();
        float delta = (float) (time - lastLoopTime);
        lastLoopTime = time;
        timeCount += delta;
        return delta;
    }
    
    /*
     * Method for incrementing the fps counter
     */

    public void incrementFps() {
        fpsCount++;
    }
    

    /*
     * Method for incrementing the ups counter
     */

    public void incrementUps() {
        upsCount++;
    }
    
    /*
     * Method for updating the ups, and fps after 1 second has passed.
     */

    public void update() {
        if (timeCount > 1f) {
            fps = fpsCount;
            fpsCount = 0;

            ups = upsCount;
            upsCount = 0;

            timeCount -= 1f;
        }
    }
    
    /*
     * Method for returning the fps 
     */

    public int getFps() {
        return fps > 0 ? fps : fpsCount;
    }
    
    /*
     * Method for returning the ups 
     */

    public int getUps() {
        return ups > 0 ? ups : upsCount;
    }

}
