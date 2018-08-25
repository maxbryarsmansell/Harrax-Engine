package graphics;

import static org.lwjgl.opengl.GL30.*;

public class VertexArrayObject {
	
	 /*
     * Stores the handle of the VAO.
     */
	
    private final int id;
    
    /*
     * Generates a new VertexArrayObject
     */
    
    public VertexArrayObject() {
        id = glGenVertexArrays();
    }
    
    /*
     * Binds the VertexArray
     */
    
    public void bind() {
        glBindVertexArray(id);
    }

    /*
     * Deletes the VertexArray
     */
    
    public void dispose() {
        glDeleteVertexArrays(id);
    }
    
    /*
     * Getter for the VertexArray
     */
    
    public int getID() {
        return id;
    }

}
