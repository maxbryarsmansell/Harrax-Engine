package com.max.harrax.events;

public class EventDispatcher {

	private Event event;

	public EventDispatcher(Event event) {
		this.event = event;
	}

	public void dispatch(Event.EventType type, EventHandler handler) {
		if (event.isHandled) {
			return;
		} else if (event.getType() == type) {
			event.isHandled = handler.onEvent(event);
		}
	}

}
