package graphics.renderables;

import game.Game;
import graphics.Colour;
import graphics.Renderer;
import maths.Vec2;
import maths.Vec4;
import graphics.text.Font;

public class Label extends Renderable {

	/*
	 * Attributes for the labels text the font of the label
	 */

	private String text;
	private Font font;
	private Colour textColour;

	/*
	 * Constructor for the label
	 */

	public Label(Font font, String text, float x, float y, Colour colour) {
		super(new Vec4(x, y, 0, 1), null, null);
		this.text = text;
		this.font = font;
		this.textColour = colour;
	}

	// public Label(Font font, String text, float x, float y, Colour colour, Colour
	// fill) {
	// super(new Vec4(x, y, 0, 1), new Vec2(font.getTextWidth(text),
	// font.getTextHeight(text)), fill);
	// this.text = text;
	// this.font = font;
	// this.textColour = colour;
	// }

	public Label(Font font, String text, float percentageX, float percentageY, Colour colour, Colour fill) {
		super(new Vec4((percentageX * Game.VIRTUAL_WIDTH) - Game.VIRTUAL_WIDTH / 2,
				(percentageY * Game.VIRTUAL_HEIGHT) - Game.VIRTUAL_HEIGHT / 2, 0, 1),
				new Vec2(font.getTextWidth(text), font.getTextHeight(text)), fill);
		this.text = text;
		this.font = font;
		this.textColour = colour;
	}

	/*
	 * Overridden submit method for label
	 */

	@Override
	public void submit(Renderer renderer) {
		if (size != null && super.colour != null) {
			renderer.submitQuad(this);
		}
		renderer.submitText(font, position.x, position.y, textColour, text);
	}

	/*
	 * Set text of the label
	 */

	public void setText(String text) {
		this.size = new Vec2(font.getTextWidth(text), font.getTextHeight(text));
		this.text = text;
	}
}
