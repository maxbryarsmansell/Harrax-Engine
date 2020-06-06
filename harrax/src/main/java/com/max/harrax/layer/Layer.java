package com.max.harrax.layer;

import com.max.harrax.events.EventListener;

public abstract class Layer implements EventListener {

    protected String name;
    protected boolean isVisible;

    public Layer(String name) {
        this.name = name;
    }

    public abstract void onAttach();

    public abstract void onDetach();

    public abstract void onUpdate(float delta);

    public abstract void dispose();

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public String getName() {
        return name;
    }
}
