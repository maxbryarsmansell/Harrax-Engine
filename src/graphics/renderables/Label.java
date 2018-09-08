package graphics.renderables;

import java.util.ArrayList;

import graphics.Bound;
import graphics.Colour;
import graphics.Renderer;
import graphics.text.Font;
import maths.Vec2;
import maths.Vec4;

public class Label extends Renderable {

	private static ArrayList<Bound> bounds = new ArrayList<Bound>();

	public enum ALIGNMENT {
		CENTRE, TOP, BOTTOM, LEFT, RIGHT,
	}

	/*
	 * Attributes for the labels text the font of the label
	 */

	private String text;
	private Font font;
	private Colour colour;
	private ALIGNMENT xAlignment;
	private ALIGNMENT yAlignment;
	private float padding;
	Bound bound;

	/*
	 * Constructor for the label
	 */

	public Label(Font font, String text, Colour colour) {
		this(font, text, colour, ALIGNMENT.CENTRE, ALIGNMENT.CENTRE, 0, true);
	}

	public Label(Font font, String text, Colour colour, ALIGNMENT xAlignment, ALIGNMENT yAlignment) {
		this(font, text, colour, xAlignment, yAlignment, 0, true);
	}

	public Label(Font font, String text, Colour colour, ALIGNMENT xAlignment, ALIGNMENT yAlignment, float padding) {
		this(font, text, colour, xAlignment, yAlignment, padding, true);
	}

	public Label(Font font, String text, Colour colour, ALIGNMENT xAlignment, ALIGNMENT yAlignment, boolean offset) {
		this(font, text, colour, xAlignment, yAlignment, 0, offset);
	}

	public Label(Font font, String text, Colour colour, ALIGNMENT xAlignment, ALIGNMENT yAlignment, float padding,
			boolean offset) {
		super(new Vec4(), new Vec2(), Colour.BLACK);
		this.text = text;
		this.font = font;
		this.colour = colour;
		this.xAlignment = xAlignment;
		this.yAlignment = yAlignment;
		this.padding = padding;
		bound = new Bound();
		if (offset) {
			bounds.add(bound);
		}
		calculateAlignment();
	}

	/*
	 * Overridden submit method for label
	 */

	@Override
	public void submit(Renderer renderer) {
		renderer.submitText(font, position.x, position.y, this.colour, text);
	}

	/*
	 * Set text of the label
	 */

	public void setText(String text) {
		this.text = text;
		calculateAlignment();
	}

	private void calculateAlignment() {
		this.size = new Vec2(font.getTextWidth(text), font.getTextHeight(text));
		switch (xAlignment) {
		case CENTRE:
			position.x = 0 - size.x / 2;
			break;
		case LEFT:
			position.x = -400 + padding;
			break;
		case RIGHT:
			position.x = 400 - size.x - padding;
			break;
		default:
			position.x = 0;
			break;
		}

		switch (yAlignment) {
		case CENTRE:
			position.y = 0 - size.y / 2;
			break;
		case TOP:
			position.y = 300 - size.y - padding;
			break;
		case BOTTOM:
			position.y = -300 + padding;
			break;
		default:
			position.y = 0;
			break;
		}

		Bound newBound = new Bound(position.x, position.y, size.x, size.y);

		if (bounds.contains(bound)) {
			bounds.remove(bound);
			bounds.add(newBound);
			bound = newBound;
		}

		for (Bound b : bounds) {
			if (b == bound) {
				continue;
			}
			if (bound.isCollision(b)) {
				if (position.y - b.height < -300) {
					setOffset(0, b.height);
					bound.y += b.height;
				} else {
					setOffset(0, -b.height);
					bound.y += -b.height;
				}
			}
		}

	}

	public void setOffset(float oX, float oY) {
		this.position.x += oX;
		this.position.y += oY;
	}
	
	public void setTextColour(Colour colour){
		this.colour = colour;
	}
}
