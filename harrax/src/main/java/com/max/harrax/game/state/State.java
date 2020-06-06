package com.max.harrax.game.state;

import com.max.harrax.events.Event;
import com.max.harrax.layer.Layer;

import java.util.ArrayList;

public class State {

    private ArrayList<Layer> layerStack;

    public State() {
        this.layerStack = new ArrayList<Layer>();
    }

    public void onUpdate(float delta) {
        for (Layer layer : layerStack) {
            layer.onUpdate(delta);
        }
    }

    public void onEvent(Event event) {
        for (int i = layerStack.size() - 1; i >= 0; i--) {
            layerStack.get(i).onEvent(event);
        }
    }

    public void addLayer(Layer layer) {
        this.layerStack.add(layer);
    }

}
