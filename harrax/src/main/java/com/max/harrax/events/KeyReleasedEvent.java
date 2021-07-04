package com.max.harrax.events;

public class KeyReleasedEvent extends Event {

  private KeyCode keyCode;

  public KeyReleasedEvent(KeyCode keyCode) {
    super(Event.EventType.KeyReleased);
    this.keyCode = keyCode;
  }

  public KeyCode getKeyCode() {
    return keyCode;
  }
}
