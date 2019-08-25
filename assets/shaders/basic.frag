#version 330 core

layout(location = 0) out vec4 colour;

in vec3 v_Position;
in vec3 v_Normal;

void main()
{
    vec3 result = vec3(1, 1, 1);
    colour = vec4(result, 1.0);
}