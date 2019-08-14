package com.max.harrax.layer;

import java.util.ArrayList;
import java.util.Iterator;

import com.max.harrax.events.Event;
import com.max.harrax.events.EventListener;
import com.max.harrax.graphicsOLD.Camera;
import com.max.harrax.graphicsOLD.Renderer;
import com.max.harrax.graphicsOLD.Shader;
import com.max.harrax.graphicsOLD.renderables.Renderable;

public abstract class Layer implements EventListener{
	
	protected String name;
	protected boolean isVisible;

	public Layer(String name) {
		this.name = name;
	}
	
	public abstract void onAttach();
	
	public abstract void onDetach();
	
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
