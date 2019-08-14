package com.max.harrax.game.entities.systems;

import java.util.Arrays;
import java.util.List;

import com.max.harrax.game.entities.Entity;
import com.max.harrax.game.entities.components.RenderComponent;
import com.max.harrax.game.entities.components.TransformComponent;
import com.max.harrax.graphicsOLD.Camera;
import com.max.harrax.layer.Layer;

public class RenderSystem implements System {

	private Layer renderLayer;

	public RenderSystem(Camera camera) {
	}

	@Override
	public void update(List<Entity> entities, float delta) {

	}

	@Override
	public void render(List<Entity> entities) {
		for (Entity e : entities) {
			//if (!renderLayer.contains(e.getComponent(RenderComponent.class).getSprite())) {
			//	renderLayer.add(e.getComponent(RenderComponent.class).getSprite());
			//}
			TransformComponent transform = e.getComponent(TransformComponent.class);
			RenderComponent render = e.getComponent(RenderComponent.class);
			render.getSprite().setPosition(transform.getPosition());
			render.getSprite().setSize(transform.getSize());
		}
		//renderLayer.render();
	}

	@Override
	public List<Class<?>> getComponentsRequired() {
		return Arrays.asList(TransformComponent.class, RenderComponent.class);
	}

}
