package state;

import java.util.HashMap;
import java.util.Map;

public class StateHandler implements State {

	private final Map<String, State> states;

	private State currentState;

	public StateHandler() {
		this.states = new HashMap<>();
		currentState = new EmptyState();
		states.put(null, currentState);
	}

	public void add(String name, State state) {
		states.put(name, state);
	}

	public void change(String name) {
		currentState.exit();
		currentState = states.get(name);
		currentState.enter();
	}

	@Override
	public void update(float delta) {
		currentState.update(delta);

	}

	@Override
	public void render() {
		currentState.render();
	}
	
	@Override
	public void pause() {
		currentState.pause();
	}

	@Override
	public void enter() {
		currentState.enter();
	}

	@Override
	public void exit() {
		currentState.exit();
	}

	public void dispose() {
		for (State state : states.values()) {
			state.dispose();
		}
	}

}
