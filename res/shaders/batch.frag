#version 150 core

// Data from vertex shader

in vec4 vertexColour;
in vec2 textureCoord;
in float ts;

// Final colour of fragment

out vec4 fragColour;

// Uniform data

uniform sampler2D[32] textures;

// Main function

void main()
{
	// Result is equal to colour initially
	vec4 result = vertexColour;
	
	// If the texture slot id is not the default 0
	// Then multiply the result by the texture colour
	
	if (ts > 0.0){
		int tid = int(ts - 0.5);
		result *= texture(textures[tid], textureCoord);
	}
	
	// Set the fragment colour to the result
	
    fragColour = result;
}