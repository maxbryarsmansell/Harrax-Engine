package com.max.harrax.graphics;

import com.max.harrax.maths.Vec3;

public class Material {
	
	public static final Material DEBUG_MATERIAL = new Material(
			new Vec3(1f, .5f, .31f),
			new Vec3(1f, .5f, .31f),
			new Vec3(.5f, .5f, .5f),
			10f);
	
	private Vec3 ambient;
	private Vec3 diffuse;
	private Vec3 specular;
	private float shininess;
	
	public Material(Vec3 ambient, Vec3 diffuse, Vec3 specular, float shininess) {
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.shininess = shininess;
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

	public float getShininess() {
		return shininess;
	}

}
