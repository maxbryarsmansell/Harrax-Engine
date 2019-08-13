package com.max.harrax.graphics.renderables;

import java.util.ArrayList;

import com.max.harrax.graphics.Colour;
import com.max.harrax.graphics.Renderer;
import com.max.harrax.graphics.layers.Layer;
import com.max.harrax.graphics.text.Font;
import com.max.harrax.maths.Vec2;

public class Label extends Renderable {

	// Static array for storing labels that are to be aligned
	private static ArrayList<Label> labels = new ArrayList<Label>();

	public enum ALIGNMENT {
		NONE, CENTRE, TOP, BOTTOM, LEFT, RIGHT,
	}

	/*
	 * Attributes for the labels text the font of the label
	 */

	private String text;
	private Font font;
	private Colour textColour;
	private ALIGNMENT xAlignment;
	private ALIGNMENT yAlignment;
	private float padding;
	private float scale;
	private float frustumWidth, frustumHeight;

	/*
	 * Constructor for the label
	 */

	public Label(Font font, String text, Colour textColour) {
		this(font, text, textColour, ALIGNMENT.NONE, ALIGNMENT.NONE, 0, 1, Colour.EMPTY_COLOUR);
	}

	public Label(Font font, String text, Colour textColour, ALIGNMENT xAlignment, ALIGNMENT yAlignment) {
		this(font, text, textColour, xAlignment, yAlignment, 0, 1, Colour.EMPTY_COLOUR);
	}

	public Label(Font font, String text, Colour textColour, ALIGNMENT xAlignment, ALIGNMENT yAlignment,
			Colour fillColour) {
		this(font, text, textColour, xAlignment, yAlignment, 0, 1, fillColour);
	}

	public Label(Font font, String text, Colour textColour, ALIGNMENT xAlignment, ALIGNMENT yAlignment, float padding) {
		this(font, text, textColour, xAlignment, yAlignment, padding, 1, Colour.EMPTY_COLOUR);
	}

	public Label(Font font, String text, Colour textColour, ALIGNMENT xAlignment, ALIGNMENT yAlignment, float padding,
			float scale, Colour fillColour) {
		super(new Vec2(), new Vec2(), fillColour);
		this.text = text;
		this.font = font;
		this.textColour = textColour;
		this.xAlignment = xAlignment;
		this.yAlignment = yAlignment;
		this.padding = padding;
		this.scale = scale;
	}

	public Label(Font font, String text, Colour textColour, float x, float y, Colour fillColour) {
		super(new Vec2(x, y), new Vec2(), fillColour);
		this.text = text;
		this.font = font;
		this.textColour = textColour;
		this.xAlignment = ALIGNMENT.NONE;
		this.yAlignment = ALIGNMENT.NONE;
		this.padding = 0;
	}

	@Override
	public void onAddedToLayer(Layer layer) {
		super.onAddedToLayer(layer);
		//frustumWidth = layer.getCamera().getFrustumWidth();
		//frustumHeight = layer.getCamera().getFrustumHeight();
		calculatePosition();
	}

	@Override
	public void onRemovedFromLayer(Layer layer) {
		super.onRemovedFromLayer(layer);
		labels.remove(this);
	}

	/*
	 * Overridden submit method for label
	 */

	@Override
	public void submit(Renderer renderer) {
		if (visible) {
			if (colour != Colour.EMPTY_COLOUR) {
				renderer.drawQuad(position.x, position.y, size.x, size.y, super.colour);
			}
			renderer.submitText(font, position.x, position.y, textColour, text, scale);
		}
	}

	private void calculatePosition() {
		size = new Vec2(font.getTextWidth(text) * scale, font.getTextHeight(text) * scale);

		switch (xAlignment) {
		case NONE:
			return;
		case CENTRE:
			position.x = 0;
			break;
		case LEFT:
			position.x = -frustumWidth / 2 + padding;
			break;
		case RIGHT:
			position.x = frustumWidth / 2 - padding - size.x;
			break;
		default:
			position.x = 0;
			break;
		}

		switch (yAlignment) {
		case NONE:
			return;
		case CENTRE:
			position.y = 0;
			break;
		case TOP:
			position.y = frustumHeight / 2 - padding - size.y;
			break;
		case BOTTOM:
			position.y = -frustumHeight / 2 + padding;
			break;
		default:
			position.y = 0;
			break;
		}

		// Only keep track of labels which should be aligned
		labels.add(this);

		// Iterate through other labels which should be aligned
		for (Label l : labels) {
			if (l == this) {
				// Ignore if the label the same as this one
				continue;
			}
			// Check if this label is intersecting the other one
			if (position.x < l.getPosition().x + l.getSize().x && position.x + size.x > l.getPosition().x
					&& position.y < l.getPosition().y + l.getSize().y && position.y + size.y > l.getPosition().y) {
				// Offset the label to stop for the intersection
				if (position.y - l.getSize().y <= -frustumHeight / 2) {
					setOffset(0, l.getSize().y / 2 + size.y / 2);
				} else {
					setOffset(0, -l.getSize().y / 2 - size.y / 2);
				}

			}
		}

	}

	/*
	 * Set text of the label
	 */

	public void setText(String text) {
		this.text = text;
		calculatePosition();
	}
	
	public String getText() {
		return text;
	}

	public void setOffset(float oX, float oY) {
		this.position.x += oX;
		this.position.y += oY;
	}

	public void setTextColour(Colour textColour) {
		this.textColour = textColour;
	}
	
	public void setFillColour(Colour fillColour) {
		setColour(fillColour);
	}

	public void setPadding(float padding) {
		this.padding = padding;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

}
