package com.max.harrax.events;

public class KeyPressedEvent extends Event {

  private KeyCode keyCode;

  public KeyPressedEvent(KeyCode keyCode) {
    super(EventType.KeyPressed);
    this.keyCode = keyCode;
  }

  public KeyCode getKeyCode() {
    return keyCode;
  }
}
