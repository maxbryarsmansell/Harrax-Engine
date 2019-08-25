package com.max.harrax.game.entities.components;

import java.util.Arrays;
import java.util.List;

import com.max.harrax.game.entities.Entity;

public class TimeComponent implements Component {

	private float timeRemaining;
	private boolean isActive;

	public TimeComponent(float time) {
		this.timeRemaining = time;
		this.isActive = false;
	}

	public TimeComponent(float time, boolean isActive) {
		this.timeRemaining = time;
		this.isActive = isActive;
	}

	@Override
	public void printContents() {
		System.out.format("{ Time remaining: \"%f\", Is active: \"%b\" } \n", timeRemaining, isActive);
	}

	@Override
	public void onEntityAdd(Entity e) {
		if (isActive) {
			System.out.format("Will remove entity %s in %f seconds.\n", e.getName(), timeRemaining);
		}
	}

	@Override
	public void onEntityRemoval(Entity e) {
		if (isActive) {
			System.out.format("Entity %s removed by timer component.\n", e.getName());
		}
	}

	@Override
	public List<Class<?>> getRequireComponents() {
		return Arrays.asList();
	}

	public float getTimeRemaining() {
		return timeRemaining;
	}

	public void setTimeRemaining(float timeRemaining) {
		this.timeRemaining = timeRemaining;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
