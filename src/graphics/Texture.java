package graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public class Texture {
	
	/*
	 * Store the handle for the texture
	 */
	private final int id;
	
	/*
	 * Texture width and height attributes
	 */
	
	private int width, height;
	
	/*
	 * Texture object constructor
	 */
	
	public Texture() {
		// Generate a new OpenGL texture handle
		id = glGenTextures();
	}
	
	/*
     * Binds the texture.
     */
	
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }
    
    /*
     * Set given parameters of the texture
     */
    
    public void setParameter(int name, int value) {
    	glTexParameteri(GL_TEXTURE_2D, name, value);
    }
    
    /*
     * Delete the texture.
     */
    
    public void dispose() {
        glDeleteTextures(id);
    }
    
    /*
     * Gets the texture width
     */
    
    public int getWidth() {
        return width;
    }

    /*
     * Sets the texture width
     */
    
    public void setWidth(int width) {
        if (width > 0) {
            this.width = width;
        }
    }

    /*
     * Gets the texture height
     */
    
    public int getHeight() {
        return height;
    }

    /*
     * Sets the texture height
     */
    
    public void setHeight(int height) {
        if (height > 0) {
            this.height = height;
        }
    }
    
    public int getID() {
    	return id;
    }

    /*
     * Uploads image data to OpenGL
     */
    public void uploadData(int internalFormat, int width, int height, int format, ByteBuffer data) {
        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
    }
    
    /*
     * Create an empty texture
     */
    
    public static Texture createEmptyTexture() {
        Texture texture = new Texture();
        texture.setWidth(1);
        texture.setHeight(1);
        
        ByteBuffer data = BufferUtils.createByteBuffer(4);
        data.put((byte) -1).put((byte) -1).put((byte) -1).put((byte) -1);
        data.flip();

        texture.bind();
        
        texture.setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        texture.setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        texture.setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        texture.setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        texture.uploadData(GL_RGBA8, 1, 1, GL_RGBA, data);

        return texture;
    }
    


    /*
     * Create a texture from given data
     */
    
    public static Texture createTexture(int width, int height, ByteBuffer data) {
        Texture texture = new Texture();
        texture.setWidth(width);
        texture.setHeight(height);
        
        texture.bind();
        
        texture.setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        texture.setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        texture.setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        texture.setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        texture.uploadData(GL_RGBA8, width, height, GL_RGBA, data);

        return texture;
    }
	
}