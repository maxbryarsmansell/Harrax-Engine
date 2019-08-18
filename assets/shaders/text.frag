#version 330 core

layout(location = 0) out vec4 colour;

in vec4 v_Colour;
in vec2 v_Texcord;

uniform sampler2D u_Texture;

void main()
{
	vec4 result = v_Colour * texture(u_Texture, v_Texcord);

	colour = result;
}