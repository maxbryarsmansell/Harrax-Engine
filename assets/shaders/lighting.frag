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
  
struct Light {
    vec3 position;
  
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

uniform Material u_Material;
uniform Light u_Light; 
uniform vec3 u_CameraPosition;

void main()
{
	// ambient
    vec3 ambient = u_Light.ambient * u_Material.ambient;
  	
    // diffuse 
    vec3 norm = normalize(v_Normal);
    vec3 lightDir = normalize(u_Light.position - v_Position);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = u_Light.diffuse * (diff * u_Material.diffuse);
    
    // specular
    vec3 viewDir = normalize(u_CameraPosition - v_Position);
    vec3 reflectDir = reflect(-lightDir, norm);  
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), u_Material.shininess);
    vec3 specular = u_Light.specular * (spec * u_Material.specular);  
        
    vec3 result = ambient + diffuse + specular;
    colour = vec4(result, 1.0);
}