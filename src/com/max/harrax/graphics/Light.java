package com.max.harrax.graphics;

import com.max.harrax.maths.Vec3;

public class Light {
	
	private Vec3 position;
	
	private Vec3 ambient;
	private Vec3 diffuse;
	private Vec3 specular;	
	
	public Light(Vec3 position, Vec3 ambient, Vec3 diffuse, Vec3 specular) {
		this.position = position;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
	}

	public Vec3 getPosition() {
		return position;
	}
	
	public Vec3 getAmbient() {
		return ambient;
	}

	public Vec3 getDiffuse() {
		return diffuse;
	}

	public Vec3 getSpecular() {
		return specular;
	}

}
