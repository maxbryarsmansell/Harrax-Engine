package com.max.harrax.graphicsOLD.text;

import java.awt.Canvas;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.system.MemoryUtil;

import com.max.harrax.graphicsOLD.Texture;

public class Font {
	
	public static Font DEBUG_FONT = new Font(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.BOLD, 20), true);
	
	/*
	 * Attributes for
	 * mapping characters to glyphs
	 * the texture atlas
	 * the font width and height
	 */

	private final Map<Character, Glyph> glyphs;
	private final Texture texture;
	private int fontWidth, fontHeight;
	
	/*
	 * Constructor for the font
	 */

	public Font(java.awt.Font font, boolean antiAlias) {
		glyphs = new HashMap<>();
		texture = createFontTexture(font, antiAlias);
	}
	
	/*
	 * Function which generates a font texture atlas
	 */

	private Texture createFontTexture(java.awt.Font font, boolean antiAlias) {
		// variables to store the image width and height
		int imageWidth = 0;
		int imageHeight = 0;

		// An array of the character textures
		ArrayList<BufferedImage> characters = new ArrayList<BufferedImage>();

		// Loop through each character code in the ASCII character set
		// Ignore control codes 0 - 31, 127
		for (int i = 32; i < 256; i++) {
			if (i == 127) {
				continue;
			}
			
			// Cast the loop counter to a char type
			char c = (char) i;
			// Convert the char to a buffered image
			BufferedImage character = createCharImage(font, c, antiAlias);
			
			// If the character is null then don't include it
			if (character == null) {
				continue;
			}
			
			// Add the character to the hash map
			characters.add(character);

			// Update the image width and height
			imageWidth += character.getWidth();
			imageHeight = Math.max(imageHeight, character.getHeight());
		}
		
		// Set the final font height and width
		this.fontHeight = imageHeight;
		this.fontWidth = imageWidth;
		

		// Create a new image to represent the full texture atlas
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();

		// Variable which represents the x position of the current character in the atlas
		int currentCharX = 0;
		// Variable which represents the current index in the characters array list
		int imageIndex = 0;

		// Loop through each character code in the ASCII character set
		// Ignore control codes 0 - 31, 127
		for (int i = 32; i < 256; i++) {
			if (i == 127) {
				continue;
			}
			
			// Cast the loop counter to a char type
			char c = (char) i;
			// Get the char image from the characters array list
			BufferedImage charImage = characters.get(imageIndex);
			// Get the character width and height
			int charWidth = charImage.getWidth();
			int charHeight = charImage.getHeight();

			// Create a new glyph
			Glyph ch = new Glyph(charWidth, charHeight, currentCharX, image.getHeight() - charHeight);
			// Draw the char onto the texture atlas
			g.drawImage(charImage, currentCharX, 0, null);
			// Put the character into the hashmap
			glyphs.put(c, ch);
			// Increase the x position of the current char in the texture atlas
			currentCharX += ch.width;
			// Increase the image index by one
			imageIndex++;
		}

		// Flip image horizontal to get the origin to bottom left
		AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);
		transform.translate(0, -image.getHeight());
		AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		image = operation.filter(image, null);

		// Get pixel data of image
		int[] pixels = new int[imageWidth * imageHeight];
		image.getRGB(0, 0, imageWidth, imageHeight, pixels, 0, imageWidth);

		// Put pixel data into a byte buffer
		ByteBuffer buffer = MemoryUtil.memAlloc(imageWidth * imageHeight * 4);
		for (int i = 0; i < imageHeight; i++) {
			for (int j = 0; j < imageWidth; j++) {
				// Pixel in RBGA format
				int pixel = pixels[i * imageWidth + j];
				// Red component 0xAARRGGBB >> 16 = 0x0000AARR & 0xFF = 0x000000RR
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				// Green component 0xAARRGGBB >> 8 = 0x00AARRGG & 0xFF = 0x000000GG
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				// Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB & 0xFF = 0x000000BB
				buffer.put((byte) (pixel & 0xFF));
				// Alpha component 0xAARRGGBB >> 24 = 0x000000AA & 0xFF = 0x000000AA
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}
		// Flip the buffer so it can be read from
		buffer.flip();

		// Create texture from the byte buffer
		Texture fontTexture = Texture.createTexture(imageWidth, imageHeight, buffer);
		MemoryUtil.memFree(buffer);
		// Return the new texture
		return fontTexture;
	}
	
	/*
	 * Function which generates a character texture
	 */

	private BufferedImage createCharImage(java.awt.Font font, char c, boolean antiAlias) {
		// A canvas must be created in order to get the metrics of a font
		Canvas canvas = new Canvas();
		FontMetrics metrics = canvas.getFontMetrics(font);

		// Get the width and height of a character
		int charWidth = metrics.charWidth(c);
		int charHeight = metrics.getHeight();

		// Return null if the character has no width
		if (charWidth == 0) {
			return null;
		}

		// Create a new image to represent the character
		BufferedImage image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		// If anti aliasing is true, then set the rendering hint
		if (antiAlias) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		
		// Draw the character to the image
		g.setFont(font);
		g.setPaint(java.awt.Color.WHITE);
		// The function draws the character from the baseline, so we must offset it by the font ascent
		g.drawString(String.valueOf(c), 0, metrics.getAscent());
		g.dispose();
		// Return the characters image
		return image;
	}
	
	/*
	 * Dispose of the font
	 */

	public void dispose() {
		texture.dispose();
	}
	
	/*
	 * Getters and setters
	 */

	public int getTextHeight(CharSequence text) {
		int lines = 1;
		for(int i = 0; i < text.length(); i++) {
		    char ch = text.charAt(i);
		    if(ch == '\n') {
		        lines++;
		    }
		}
		int textHeight = lines * fontHeight;
		return textHeight;
	}

	public int getTextWidth(CharSequence text) {
		int width = 0;
		int lineWidth = 0;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == '\n') {
				width = Math.max(width, lineWidth);
				lineWidth = 0;
				continue;
			}
			if (c == '\r') {
				continue;
			}
			Glyph g = glyphs.get(c);
			lineWidth += g.width;
		}
		width = Math.max(width, lineWidth);
		return width;
	}

	public int getFontHeight() {
		return fontHeight;
	}
	
	public int getFontWidth() {
		return fontWidth;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public Map<Character, Glyph> getGlyphs() {
		return glyphs;
	}

}
