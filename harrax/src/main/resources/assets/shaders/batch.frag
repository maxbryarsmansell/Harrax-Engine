#version 330 core

layout(location = 0) out vec4 o_Colour;

in vec4 v_Colour;
in vec3 v_Position;

struct Light
{
    vec3 position;
    vec4 colour;
};

uniform Light u_Lights[10];
uniform int u_NumLights;

void main()
{
    float ambientStrength = 0.8;
    vec4 ambient = ambientStrength * vec4(1, 1, 1, 1);

   	vec4 diff;

   	for (int i = 0; i < u_NumLights; i++)
   	{
   		vec3 toLight = u_Lights[i].position - v_Position;
       	float brightness = clamp(dot(normalize(toLight), vec3(0, 0, 1)), 0.0, 1.0);
       	brightness *= clamp(1.0 - (length(toLight) / 1000), 0.0, 1.0);
   		diff += u_Lights[i].colour * brightness;
   	}

    o_Colour = v_Colour * (ambient + diff);
}