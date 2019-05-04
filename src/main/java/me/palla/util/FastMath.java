package me.palla.util;

public class FastMath {

    private final static int PRECISION_PER_DEGREE = 10; // Should be high enough

    private final static float PI = (float) Math.PI;
    private final static float[] SIN_TABLE = new float[360 * PRECISION_PER_DEGREE];

    static {
        for (int i = 0; i < SIN_TABLE.length; i++) {
            final float actualDegrees = (float) i / PRECISION_PER_DEGREE;
            final float radians = actualDegrees * PI / 180F;
            SIN_TABLE[i] = (float) Math.sin(radians);
        }
    }

    private FastMath() {}

    private static float lookup(float angle) {
        final int angle0 = (int) (angle * 180F / PI) % 360;
        if(angle0 < 0)
            return -SIN_TABLE[-angle0 * PRECISION_PER_DEGREE];
        return SIN_TABLE[angle0 * PRECISION_PER_DEGREE];
    }

    public static float sin(float angle) {
        return lookup(angle);
    }

    public static float cos(float angle) {
        return lookup(angle + 90);
    }
}
