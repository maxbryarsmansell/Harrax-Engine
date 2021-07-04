#version 330 core

layout(location = 0) in vec2 a_Position;
layout(location = 1) in vec2 a_TexCoord;
layout(location = 2) in vec4 a_Colour;

out vec2 v_TexCoord;
out vec4 v_Colour;

uniform mat4 u_ViewProjection;

void main()
{
    gl_Position = u_ViewProjection * vec4(a_Position, 0.0, 1.0);

    v_TexCoord = a_TexCoord;
    v_Colour = a_Colour;
}
