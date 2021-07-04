#version 330 core

layout(location = 0) out vec4 o_Colour;

in vec4 v_Colour;
in vec2 v_TexCoord;

uniform sampler2D u_LightTexture;

void main()
{
    o_Colour = texture(u_LightTexture, v_TexCoord) * v_Colour;
}