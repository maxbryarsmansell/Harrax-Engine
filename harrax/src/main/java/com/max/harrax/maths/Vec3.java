package com.max.harrax.maths;

import java.nio.FloatBuffer;
import java.text.DecimalFormat;

public class Vec3 {

    // private static final float EPSILON = 0.000001f;

    public static final Vec3 ZERO = new Vec3();

    /*
     * x, y and z components, represented as floats.
     */

    public float x;
    public float y;
    public float z;

    public Vec3() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }

    public Vec3(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3(final Vec3 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public Vec3 add(final Vec3 other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        return this;
    }

    public Vec3 sub(final Vec3 other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        return this;
    }

    public Vec3 mult(final float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        return this;
    }

    public Vec3 div(float scalar) {
        this.x /= scalar;
        this.y /= scalar;
        this.z /= scalar;
        return this;
    }

    public float dot(final Vec3 other) {
        return (x * other.x) + (y * other.y) + (z * other.z);
    }

    public Vec3 cross(final Vec3 other) {
        return new Vec3(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
    }

    public float dist(final Vec3 other) {
        float a = x - other.x;
        float b = y - other.y;
        float c = z - other.z;
        return (float) Math.sqrt(a * a + b * b + c * c);
    }

    public float mag() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public void normalize() {
        float length = mag();
        x /= length;
        y /= length;
        z /= length;
    }

    public Vec3 norm() {
        float length = mag();
        return new Vec3(x / length, y / length, z / length);
    }

    public Vec3 negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    public Vec4 toVec4() {
        return new Vec4(x, y, z, 1);
    }

    public boolean equals(final Vec3 other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x)) {
            return false;
        }
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y)) {
            return false;
        }
        if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z)) {
            return false;
        }

        return true;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return new StringBuilder().append("(").append(df.format(x)).append(", ").append(df.format(y)).append(", ").append(df.format(z)).append(")").toString();
    }

    public void toBuffer(FloatBuffer buffer) {
        buffer.put(x).put(y).put(z);
        buffer.flip();
    }

}
