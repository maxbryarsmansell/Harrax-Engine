package com.max.harrax.graphics;

import com.max.harrax.maths.Vec3;
import com.max.harrax.maths.Vec4;

import java.util.Random;

public class Colour {

    public static Colour BLACK = new Colour(0, 0, 0, 1);
    public static Colour WHITE = new Colour(1, 1, 1, 1);
    public static Colour RED = new Colour(1, 0, 0, 1);
    public static Colour GREEN = new Colour(0, 1, 0, 1);
    public static Colour BLUE = new Colour(0, 0, 1, 1);
    public static Colour TRANSPARENT = new Colour(0, 0, 0, 0);

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

    public Vec3 toVec3() {
        return new Vec3(r, g, b);
    }

    public Vec4 toVec4() {
        return new Vec4(r, g, b, a);
    }
}
