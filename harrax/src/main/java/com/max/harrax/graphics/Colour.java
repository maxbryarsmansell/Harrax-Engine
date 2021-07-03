package com.max.harrax.graphics;

import java.util.Random;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Colour {

    public static Colour BLACK = new Colour(0, 0, 0, 1);
    public static Colour WHITE = new Colour(1, 1, 1, 1);
    public static Colour RED = new Colour(1, 0, 0, 1);
    public static Colour GREEN = new Colour(0, 1, 0, 1);
    public static Colour BLUE = new Colour(0, 0, 1, 1);

    public final float r, g, b, a;

    private static Random rand = new Random();

    public Colour(float red, float green, float blue, float alpha) {
        r = red;
        g = green;
        b = blue;
        a = alpha;
    }

    public Colour(int red, int green, int blue) {
        this(red, green, blue, 1);
    }

    public Colour() {
        this(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1);
    }

    public Vector3f toVec3() {
        return new Vector3f(r, g, b);
    }

    public Vector4f toVec4() {
        return new Vector4f(r, g, b, a);
    }
}
