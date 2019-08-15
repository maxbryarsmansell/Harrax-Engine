package com.max.harrax.layer;

import java.util.ArrayList;
import java.util.ListIterator;

public class LayerStack {

	private ArrayList<Layer> layerStack;

	private int layerInsertIndex;

	public LayerStack() {
		this.layerStack = new ArrayList<Layer>();
		this.layerInsertIndex = 0;
	}

	public void pushLayer(Layer layer) {
		this.layerStack.add(layerInsertIndex, layer);
		layerInsertIndex++;
		layer.onAttach();
	}

	public void popLayer(Layer layer) {
		
		int layerIndex = layerStack.indexOf(layer);
		
		if (layerIndex != -1) {
			layer.onDetach();
			this.layerStack.remove(layer);
			layerInsertIndex--;
		}
	}
	
	public ListIterator<Layer> start(){
		return layerStack.listIterator();
	}
	
	public ListIterator<Layer> end(){
		return layerStack.listIterator(layerInsertIndex);
	}
	
	

}
