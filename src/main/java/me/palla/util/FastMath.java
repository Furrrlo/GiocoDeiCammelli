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

    private FastMath() {
    }

    private static float lookup(float degrees) {
        degrees = degrees % 360;
        if (degrees < 0)
            return -SIN_TABLE[(int) (-degrees * PRECISION_PER_DEGREE)];
        return SIN_TABLE[(int) (degrees * PRECISION_PER_DEGREE)];
    }

    public static float sin(float radians) {
        return lookup(toDegrees(radians));
    }

    public static float cos(float radians) {
        return lookup(toDegrees(radians) + 90);
    }

    private static float toDegrees(float radians) {
        return radians * 180.0F / PI;
    }
}
