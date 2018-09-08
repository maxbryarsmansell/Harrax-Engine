package state;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import graphics.Colour;
import graphics.layers.Layer;
import graphics.renderables.Label;
import graphics.renderables.Sprite;
import graphics.text.Font;
import maths.Mat4;
import maths.Vec4;

public class TestState implements State {

	boolean paused;

	Layer layer;
	Layer pausedLayer;
	Font font;
	ArrayList<Sprite> sprites;
	Random rand;
	Label pauseLabel;
	
	float width, height;

	@Override
	public void update(float delta) {
		if (!paused) {
			for (int i = 0; i < sprites.size(); i++) {
				sprites.get(i).getPosition().Add(new Vec4(8 * rand.nextFloat() - 4, 8 * rand.nextFloat() - 4, 0f, 1f));
				sprites.get(i).getPosition().Multiply(Mat4.zAxisRotation(rand.nextFloat() / 10));
			}
		}

	}

	@Override
	public void render() {
		layer.render();
		if (paused) {
			pausedLayer.render();
		}
	}

	@Override
	public void enter() {
		
		try (MemoryStack stack = MemoryStack.stackPush()) {
            long window = GLFW.glfwGetCurrentContext();
            IntBuffer widthBuffer = stack.mallocInt(1);
            IntBuffer heightBuffer = stack.mallocInt(1);
            GLFW.glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
            width = widthBuffer.get();
            height = heightBuffer.get();
        }
		
		sprites = new ArrayList<Sprite>();
		rand = new Random();
		layer = new Layer(width, height);
		font = new Font(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.BOLD, 30), true);

		for (int i = 0; i < 10000; i++) {
			sprites.add(new Sprite(rand.nextFloat(), rand.nextFloat(), 5, 5, new Colour(rand.nextFloat(), 0.0f, 1.0f)));
			layer.add(sprites.get(i));
		}

		
		
		pausedLayer = new Layer(width, height);
		pausedLayer.add(new Sprite(-width / 2, -height / 2, width, height, new Colour(0.6f, 0.6f, 0.6f, 0.8f)));
		pauseLabel = new Label(new Font(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.BOLD, 100), true), "PAUSED", new Colour(), Label.ALIGNMENT.CENTRE, Label.ALIGNMENT.CENTRE, false);
		pausedLayer.add(pauseLabel);
		
	}

	@Override
	public void exit() {
	}

	@Override
	public void dispose() {
		layer.dispose();
		pausedLayer.dispose();
		font.dispose();
	}

	@Override
	public void pause() {
		pauseLabel.setTextColour(new Colour());
		paused = paused ? false : true;
	}

}
