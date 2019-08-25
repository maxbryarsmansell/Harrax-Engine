package com.max.harrax.graphics;

import com.max.harrax.maths.Vec3;

public class Material {
	
	public static final Material MATERIAL_EMERALD = new Material(
			new Vec3(.0215f, .1745f, .0215f),
			new Vec3(.07568f, .61424f, .07568f),
			new Vec3(.633f, .727811f, .633f),
			.6f * 128);
	
	public static final Material MATERIAL_GOLD = new Material(
			new Vec3(.24725f, .1995f, .0745f),
			new Vec3(.75164f, .60648f, .22648f),
			new Vec3(.628281f, .555802f, .366065f),
			.4f * 128);
	
	public static final Material MATERIAL_CHROME = new Material(
			new Vec3(0.25f, 0.25f,0.25f),
			new Vec3(0.4f, 0.4f, 0.4f),
			new Vec3(0.774597f, 0.774597f,
					0.774597f), 0.6f * 128
			);
	
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
