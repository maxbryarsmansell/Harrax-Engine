package com.max.harrax.layer;

import com.max.harrax.events.EventListener;

public abstract class Layer implements EventListener {

    public abstract void onAttach();

    public abstract void onDetach();

    public abstract void onUpdate(float delta);

    public abstract void onDispose();
}
