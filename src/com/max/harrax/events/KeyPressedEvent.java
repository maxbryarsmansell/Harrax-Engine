package com.max.harrax.events;

public class KeyPressedEvent extends Event{
	
	private int keyCode;
	
	public KeyPressedEvent(int keyCode) {
		super(EventType.KeyPressed);
		this.keyCode = keyCode;
	}

	public int getKeyCode() {
		return keyCode;
	}
}
