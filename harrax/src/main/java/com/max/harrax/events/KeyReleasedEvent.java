package com.max.harrax.events;

public class KeyReleasedEvent extends Event {

    private int keyCode;

    public KeyReleasedEvent(int keyCode) {
        super(Event.EventType.KeyReleased);
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }

}
