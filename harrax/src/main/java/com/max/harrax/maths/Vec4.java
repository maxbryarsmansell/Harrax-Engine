package com.max.harrax.maths;

import java.nio.FloatBuffer;
import java.text.DecimalFormat;

public class Vec4 implements Cloneable {

    /*
     * x, y, z and w components, represented as floats.
     */

    public float x;
    public float y;
    public float z;
    public float w;

    public Vec4() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 0.0f;
    }

    public Vec4(final float x, final float y, final float z, final float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vec4(final Vec4 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        this.w = other.w;
    }

    public Vec4 add(final Vec4 other) {
        x += other.x;
        y += other.y;
        z += other.z;
        w += other.w;
        return this;
    }

    public Vec4 mult(final Mat4 other) {
        float a = x * other.elements[0] + y * other.elements[4] + z * other.elements[8] + w * other.elements[12];
        float b = x * other.elements[1] + y * other.elements[5] + z * other.elements[9] + w * other.elements[13];
        float c = x * other.elements[2] + y * other.elements[6] + z * other.elements[10] + w * other.elements[14];
        float d = x * other.elements[3] + y * other.elements[7] + z * other.elements[11] + w * other.elements[15];
        x = a;
        y = b;
        z = c;
        w = d;
        return this;
    }

    public float dot(final Vec4 other) {
        return (x * other.x) + (y * other.y) + (z * other.z) + (w * other.w);
    }

    public float dist(final Vec4 other) {
        float a = x - other.x;
        float b = y - other.y;
        float c = z - other.z;
        float d = w - other.w;
        return (float) Math.sqrt(a * a + b * b + c * c + d * d);
    }

    public float mag() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public Vec4 normalize() {
        float length = mag();
        x /= length;
        y /= length;
        z /= length;
        w /= length;
        return this;
    }

    public void negate() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
    }

    public boolean equals(final Vec4 other) {
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
        if (Float.floatToIntBits(w) != Float.floatToIntBits(other.w)) {
            return false;
        }

        return true;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return new StringBuilder().append("(").append(df.format(x)).append(", ").append(df.format(y)).append(", ")
                .append(df.format(z)).append(", ").append(df.format(w)).append(")").toString();
    }

    public void toBuffer(FloatBuffer buffer) {
        buffer.put(x).put(y).put(z).put(w);
        buffer.flip();
    }

}
