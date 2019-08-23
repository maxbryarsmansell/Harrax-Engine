#version 330 core

layout(location = 0) out vec4 colour;

in vec3 v_Position;
in vec3 v_Normal;

struct Material {
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float shininess;
}; 
  
uniform Material u_Material;
uniform vec3 u_CameraPosition;

const vec3 c_LightColour = vec3(.2, .8, .2);
const vec3 c_LightPos = vec3(4, 0, 2);

void main()
{
	// ambient
    vec3 ambient = c_LightColour * u_Material.ambient;
  	
    // diffuse 
    vec3 norm = normalize(v_Normal);
    vec3 lightDir = normalize(c_LightPos - v_Position);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = c_LightColour * (diff * u_Material.diffuse);
    
    // specular
    vec3 viewDir = normalize(u_CameraPosition - v_Position);
    vec3 reflectDir = reflect(-lightDir, norm);  
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), u_Material.shininess);
    vec3 specular = c_LightColour * (spec * u_Material.specular);  
        
    vec3 result = ambient + diffuse + specular;
    colour = vec4(result, 1.0);
}