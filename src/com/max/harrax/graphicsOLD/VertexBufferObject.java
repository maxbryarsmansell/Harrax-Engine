package com.max.harrax.graphicsOLD;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class VertexBufferObject {
	
	/*
     * Stores the handle of the VBO
     */
	
    private final int id;

    /*
     *  Generates a new VertexBufferObject
     */
    
    public VertexBufferObject() {
        id = glGenBuffers();
    }
    
    /*
     * Binds the VertexBufferObject with a given buffer target
     */
    
    public void bind(int target) {
        glBindBuffer(target, id);
    }
    
    /*
     * Upload null data to the VertexBuffer
     */
    
    public void uploadData(int target, long size, int usage) {
        glBufferData(target, size, usage);
    }
    
    /*
     * Upload/Change data in the VertexBuffer from a given offset
     */
    
    public void uploadSubData(int target, long offset, FloatBuffer data) {
        glBufferSubData(target, offset, data);
    }
    
    /*
     * Upload element data to the vertexBuffer with a given buffer target and usage hint
     */
    
    public void uploadData(int target, IntBuffer data, int usage) {
        glBufferData(target, data, usage);
    }
    
    /*
     * Deletes this VBO
     */
    
    public void dispose() {
        glDeleteBuffers(id);
    }
    
    /*
     * Getter for the VertexBuffer ID
     */
    
    public int getID() {
        return id;
    }
    
}
