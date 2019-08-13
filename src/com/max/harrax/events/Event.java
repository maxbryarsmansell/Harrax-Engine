package com.max.harrax.events;

public class Event {

	public enum EventType {
		None, WindowClose, AppTick, AppUpdate, AppRender, KeyPressed, KeyReleased, MouseButtonPressed,
		MouseButtonReleased, MouseMoved, MouseScrolled,
	}

	private EventType type;
	public boolean isHandled;

	protected Event(EventType type) {
		this.type = type;
		this.isHandled = false;
	}

	public EventType getType() {
		return this.type;
	}

}
