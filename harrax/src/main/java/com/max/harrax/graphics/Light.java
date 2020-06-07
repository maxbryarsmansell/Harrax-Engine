package com.max.harrax.graphics;

import com.max.harrax.maths.Vec3;

public class Light {

    private Vec3 position;
    private Colour colour;

    public Light(Vec3 position, Colour colour) {
        this.position = position;
        this.colour = colour;
    }

    public Vec3 getPosition() {
        return position;
    }

    public void setPosition(Vec3 position) {
        this.position = position;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }
}
