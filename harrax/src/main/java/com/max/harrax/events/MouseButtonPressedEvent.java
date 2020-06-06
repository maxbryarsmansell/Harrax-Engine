package com.max.harrax.events;

public class MouseButtonPressedEvent extends Event {

    private int mouseButton;

    public MouseButtonPressedEvent(int mouseButton) {
        super(EventType.MouseButtonPressed);
        this.mouseButton = mouseButton;
    }

    public int getMouseButton() {
        return mouseButton;
    }

}
