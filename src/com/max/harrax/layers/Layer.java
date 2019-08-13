package com.max.harrax.layers;

import java.util.ArrayList;
import java.util.Iterator;

import com.max.harrax.events.Event;
import com.max.harrax.events.EventListener;
import com.max.harrax.graphics.Camera;
import com.max.harrax.graphics.Renderer;
import com.max.harrax.graphics.Shader;
import com.max.harrax.graphics.renderables.Renderable;

public abstract class Layer implements EventListener{
	
	private String name;
	private boolean isVisible;

	public Layer(String name) {
		this.name = name;
	}

	public abstract void onRender();
	
	public abstract void onUpdate();
	
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
