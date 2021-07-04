#version 330 core

layout(location = 0) out vec4 o_Colour;

in vec4 v_Colour;
in vec2 v_TexCoords;

void main()
{
    o_Colour = v_Colour;
}