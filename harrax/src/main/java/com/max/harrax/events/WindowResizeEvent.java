package com.max.harrax.events;

public class WindowResizeEvent extends Event {
    int width, height;

    public WindowResizeEvent(int width, int height) {
        super(EventType.WindowResize);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
