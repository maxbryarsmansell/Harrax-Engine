package com.max.harrax.graphics;

import com.max.harrax.maths.Vec3;
import com.max.harrax.maths.Vec4;

import java.util.Random;

public class Colour {

    public static Colour BLACK = new Colour(0, 0, 0, 255);
    public static Colour WHITE = new Colour(255, 255, 255, 255);
    public static Colour RED = new Colour(255, 0, 0, 255);
    public static Colour GREEN = new Colour(0, 255, 0, 255);
    public static Colour BLUE = new Colour(0, 0, 255, 255);
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
        this(red, green, blue, 255);
    }

    public Colour() {
        this(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255), 255);
    }

    public Vec3 toVec3() {
        return new Vec3(r, g, b);
    }

    public Vec4 toVec4() {
        return new Vec4(r, g, b, a);
    }

}
