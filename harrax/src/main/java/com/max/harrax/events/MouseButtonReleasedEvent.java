package com.max.harrax.events;

public class MouseButtonReleasedEvent extends Event {

    private int mouseButton;

    public MouseButtonReleasedEvent(int mouseButton) {
        super(EventType.MouseButtonReleased);
        this.mouseButton = mouseButton;
    }

    public int getMouseButton() {
        return mouseButton;
    }

}
