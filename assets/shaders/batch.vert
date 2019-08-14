#version 150 core

// Buffer attributes data

in vec2 position;
in vec2 texcoord;
in float tid;
in vec4 colour;

// Data to send to the fragment shader

out vec4 vertexColour;
out vec2 textureCoord;
out float ts;

// Uniform data

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

// Main function

void main() {
	// Vertex colour
    vertexColour = colour;
    
    // Texture coordinate
    textureCoord = texcoord;
    
    // texture slot id
    ts = tid;
    
    // Model - View - Projection matrix calculation
    mat4 mvp = projection * view * model;
    
    // Setting the position of the vertex by multiplying the buffer position with the MVP matrix
    gl_Position = mvp * vec4(position, 0.0, 1.0);
}