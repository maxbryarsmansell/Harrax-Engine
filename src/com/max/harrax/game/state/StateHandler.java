package com.max.harrax.game.state;

import java.util.HashMap;
import java.util.Map;

import com.max.harrax.events.Event;

public class StateHandler extends State {

	// Map that takes state names, to their state object
	private final Map<String, State> states;

	// The current state object, and string name
	private State currentState;

	/*
	 * State handler constructor 
	 */
	
	public StateHandler() {
		this.states = new HashMap<>();
		currentState = new EmptyState();
		states.put(null, currentState);
	}
	
	/*
	 * Method for adding a new state to the handler
	 */

	public void add(String name, State state) {
		states.put(name, state);
	}
	
	/*
	 * Method for changing the current state to the handler
	 */

	public void change(String name) {
		currentState = states.get(name);
	}
	
	@Override
	public void onUpdate(float delta) {
		currentState.onUpdate(delta);
	}

	@Override
	public void onEvent(Event event) {
	}
}
