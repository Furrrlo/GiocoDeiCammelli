package me.palla.entity;

import me.palla.GiocoPalla;
import me.palla.util.FastMath;

import java.awt.*;

public class PoolEntity implements Entity {

    // Constants

    private static final int ROTATION_STEP = 1;

    // Attributes

    private float xPos;
    private float yPos;

    private float width;
    private float length;

    private float leftBorderHeight;
    private float rightBorderHeight;

    private float waterVolume;

    // Rotation

    private boolean invalidateRotationX;
    private float currRotationX;
    private float targetRotationX;

    private float startWaterX;
    private float waterXWidth;

    public PoolEntity(float xPos, float yPos,
                      float width, float length,
                      float leftBorderHeight, float rightBorderHeight,
                      float waterVolume) {

        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.length = length;
        this.leftBorderHeight = leftBorderHeight;
        this.rightBorderHeight = rightBorderHeight;
        this.waterVolume = waterVolume;

        invalidateRotationX = true;
        targetRotationX = 0;
        currRotationX = 0;
    }

    @Override
    public void onTick() {

        if(invalidateRotationX || targetRotationX != currRotationX) {
            rotateX(currRotationX);
            invalidateRotationX = false;
        }

        if(targetRotationX > 0)
            currRotationX += ROTATION_STEP;
        else if(targetRotationX < 0)
            currRotationX -= ROTATION_STEP;
    }

    @Override
    public void onRender() {
        GiocoPalla.getInstance().pushStyle();

        GiocoPalla.getInstance().stroke(33, 33, 33);
        GiocoPalla.getInstance().fill(33, 33, 33);
        GiocoPalla.getInstance().rect(xPos, yPos, width, length);

        GiocoPalla.getInstance().fill(Color.BLUE.getRGB());
        GiocoPalla.getInstance().rect(xPos + startWaterX, yPos, waterXWidth, length);

        GiocoPalla.getInstance().popStyle();
    }

    private void rotateX(float delta) {

        // For comments on how it works refer to
        // the Paint.NET file /docs/PAINT_POOL.pdn
        // or to the exported /docs/PAINT_POOL.png

        boolean reversed = false;
        if(delta < 0) {
            // If the angle is on the other side I can use the same exact
            // method to calculate stuff and then just change the point
            // where I start to draw water
            delta = -delta;
            reversed = true;
        }

        // Find the area from the volume
        // As we are on the X axis, we need to divide by the length

        final float area = waterVolume / length;

        // Find the 3 angles of the triangle
        // alpha is always 90 degrees as it's the border of the boxes and never changes
        // gamma is the same as the inclination based on the Thales' theorem (https://en.wikipedia.org/wiki/Intercept_theorem)
        // beta is just the difference

        float alpha = 90F;
        float gamma = delta;
        float beta = 180F - alpha - gamma;

        // Convert all of them to radians to calculate sine and cosine

        // Unused
//        delta = (float) Math.toRadians(delta);
//        epsilon = (float) Math.toRadians(epsilon);
        alpha = (float) Math.toRadians(alpha);
        gamma = (float) Math.toRadians(gamma);
        beta = (float) Math.toRadians(beta);

        // Find the length of the sides based on area + angles
        // A = (b * h) / 2
        // In this case our base is a
        // Based on the law of sines (https://en.wikipedia.org/wiki/Law_of_sines)
        // h : sin(gamma) = b : sin(90) -> h = b * sin(gamma)
        // and
        // a : sin(alpha) = b : sin(beta) -> b = a * sin(beta) / sin(alpha)
        // So
        // A = (a * h) / 2 = (a * (b * sin(gamma))) / 2 = (a * ((a * sin(beta) / sin(alpha)) * sin(gamma))) / 2 =
        //   = (a * a * sin(beta) * sin(gamma)) / (2sin(alpha))
        // -> a = sqrt((2A * sin(alpha)) / (sin(beta) * sin(gamma)))
        // Then:
        // b = a * sin(beta)
        // c = a * sin(gamma)

        float a = (float) Math.sqrt((2 * area * FastMath.sin(alpha)) / (FastMath.sin(beta) * FastMath.sin(gamma)));
        final float b = Math.min(width, a * FastMath.sin(beta));
        // Unused
//        final float c = Math.min(rightBorderHeight, a * FastMath.sin(gamma));

        // Make the last difference and find where the water starts

        if(!reversed) {
            startWaterX = width - b;
            waterXWidth = b;
        } else {
            startWaterX = 0;
            waterXWidth = b;
        }
    }

    // Important getters and setters

    public void setWidth(float width) {
        this.width = width;
        invalidateRotationX = true;
    }

    public void setLength(float length) {
        this.length = length;
        // TODO: invalidate y axis rotation
    }

    public void setWaterVolume(float waterVolume) {
        this.waterVolume = waterVolume;
        invalidateRotationX = true;
        // TODO: invalidate y axis rotation
    }

    // Getters and setters

    public float getPosX() {
        return xPos;
    }

    public void setPosX(float xPos) {
        this.xPos = xPos;
    }

    public float getPosY() {
        return yPos;
    }

    public void setPosY(float yPos) {
        this.yPos = yPos;
    }

    public float getWidth() {
        return width;
    }

    public float getLength() {
        return length;
    }

    public float getLeftBorderHeight() {
        return leftBorderHeight;
    }

    public void setLeftBorderHeight(float leftBorderHeight) {
        this.leftBorderHeight = leftBorderHeight;
    }

    public float getRightBorderHeight() {
        return rightBorderHeight;
    }

    public void setRightBorderHeight(float rightBorderHeight) {
        this.rightBorderHeight = rightBorderHeight;
    }

    public float getWaterVolume() {
        return waterVolume;
    }

    public float getTargetRotationX() {
        return targetRotationX;
    }

    public void setTargetRotationX(float targetRotationX) {
        this.targetRotationX = targetRotationX;
    }
}
