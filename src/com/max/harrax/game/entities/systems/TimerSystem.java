package com.max.harrax.game.entities.systems;

import java.util.Arrays;
import java.util.List;

import com.max.harrax.game.entities.Entity;
import com.max.harrax.game.entities.components.TimeComponent;

public class TimerSystem implements System{

	@Override
	public void update(List<Entity> entities, float delta) {
		for (Entity e : entities) {
			TimeComponent timer = e.getComponent(TimeComponent.class);
			if (timer.isActive()) {
				float timeLeft = timer.getTimeRemaining();
				if (timeLeft <= 0) {
					e.setShouldRemove();
				} else {
					timer.setTimeRemaining(timeLeft - delta); 
				}
			}
		}
	}

	@Override
	public void render(List<Entity> entities) {
		
	}

	@Override
	public List<Class<?>> getComponentsRequired() {
		return Arrays.asList(TimeComponent.class);
	}

}
