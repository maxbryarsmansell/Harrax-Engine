package com.max.harrax.game.ecs.systems;

import com.max.harrax.game.ecs.components.ComponentType;
import com.max.harrax.game.ecs.components.MeshComponent;
import com.max.harrax.game.ecs.components.TransformComponent;
import com.max.harrax.game.ecs.Entity;
import com.max.harrax.graphics.Camera;
import com.max.harrax.graphics.Renderer;
import com.max.harrax.layer.Layer;

import java.util.Arrays;
import java.util.List;

import static com.max.harrax.game.ecs.components.ComponentType.*;

public class RenderSystem extends System {

    private Renderer renderer;
    private Camera camera;

    public RenderSystem(Camera camera) {
        this.renderer = new Renderer();
        this.camera = camera;
    }

    @Override
    public void onUpdate(float delta) {
        renderer.beginScene(camera);

        for (Entity e : entities) {
            TransformComponent tc = (TransformComponent) e.getComponent(TransformComponent);
            MeshComponent mc = (MeshComponent) e.getComponent(MeshComponent);
            
            // renderer.submitQuad(tc.getPosition(), tc.getSize(), mc.getColour());
        }

        renderer.endScene();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    @Override
    public List<ComponentType> getRequiredComponents() {
        return Arrays.asList(TransformComponent, MeshComponent);
    }

}
