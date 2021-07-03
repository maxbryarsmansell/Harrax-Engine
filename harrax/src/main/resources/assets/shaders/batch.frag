#version 330 core

layout(location = 0) out vec4 o_Colour;

in vec4 v_Colour;

void main()
{
    o_Colour = v_Colour;
}