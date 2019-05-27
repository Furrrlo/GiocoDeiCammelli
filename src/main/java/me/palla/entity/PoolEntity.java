package me.palla.entity;

import me.palla.GiocoPalla;
import me.palla.util.FastMath;
import processing.core.PConstants;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.DoubleConsumer;

public class PoolEntity implements Entity {

    private static final int POOL_COLOR = new Color(33, 33, 33).getRGB();
    private static final int SAND_COLOR = new Color(244, 217, 66).getRGB();

    private static final boolean DEBUG = true;

    // Attributes

    private float xPos;
    private float yPos;

    private float width;
    private float length;

    private float leftBorderHeight;
    private float rightBorderHeight;

    private float topBorderHeight;
    private float bottomBorderHeight;

    private float sandVolume;

    private final Thread physicsThread;

    // Pools

    private PoolEntity topPool;
    private PoolEntity bottomPool;
    private PoolEntity rightPool;
    private PoolEntity leftPool;

    // Rotation

    private boolean invalidateRotationX;
    private float currRotationX;
    private float targetRotationX;

    private boolean invalidateRotationY;
    private float currRotationY;
    private float targetRotationY;

    private float startSandX;
    private float sandXWidth;

    private float startSandY;
    private float sandYWidth;

    public PoolEntity(float xPos, float yPos,
                      float width, float length,
                      float topBorderHeight, float bottomBorderHeight,
                      float leftBorderHeight, float rightBorderHeight) {
        this(
                xPos, yPos,
                width, length,
                topBorderHeight, bottomBorderHeight,
                leftBorderHeight, rightBorderHeight,
                (width * length * 10) / 2F
        );
    }

    public PoolEntity(float xPos, float yPos,
                      float width, float length,
                      float topBorderHeight, float bottomBorderHeight,
                      float leftBorderHeight, float rightBorderHeight,
                      float sandVolume) {

        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.length = length;
        this.topBorderHeight = topBorderHeight;
        this.bottomBorderHeight = bottomBorderHeight;
        this.leftBorderHeight = leftBorderHeight;
        this.rightBorderHeight = rightBorderHeight;
        this.sandVolume = sandVolume;

        this.physicsThread = new PhysicsThread(this);
        this.physicsThread.start();

        invalidateRotationX = true;
        targetRotationX = 0;
        currRotationX = 0;
        applyRotationX(currRotationX);

        invalidateRotationY = true;
        targetRotationY = 0;
        currRotationY = 0;
        applyRotationY(currRotationY);
    }

    @Override
    public void onTick() {

        // Rotate X

        if (invalidateRotationX || targetRotationX != currRotationX) {
            applyRotationX(currRotationX);
            invalidateRotationX = false;
        }

        // Change the x rotation towards the target one
        final float rotationStepX = Math.abs(currRotationX - targetRotationX) / 10f;
        if (currRotationX < targetRotationX)
            currRotationX = Math.min(currRotationX + rotationStepX, targetRotationX);
        else if (currRotationX > targetRotationX)
            currRotationX = Math.max(currRotationX - rotationStepX, targetRotationX);

        // Rotate Y

        if (invalidateRotationY || targetRotationY != currRotationY) {
            applyRotationY(currRotationY);
            invalidateRotationY = false;
        }

        // Change the y rotation towards the target one
        final float rotationStepY = Math.abs(currRotationY - targetRotationY) / 10f;
        if (currRotationY < targetRotationY)
            currRotationY = Math.min(currRotationY + rotationStepY, targetRotationY);
        else if (currRotationY > targetRotationY)
            currRotationY = Math.max(currRotationY - rotationStepY, targetRotationY);
    }

    @Override
    public void onRender() {
        GiocoPalla.getInstance().pushStyle();

        GiocoPalla.getInstance().stroke(POOL_COLOR);
        GiocoPalla.getInstance().fill(POOL_COLOR);
        GiocoPalla.getInstance().rect(xPos, yPos, width, length, 20);

        GiocoPalla.getInstance().fill(SAND_COLOR);
        if(Math.abs(sandXWidth) > 1 && Math.abs(sandYWidth) > 1) // Kind of fixes horrible rendering
            GiocoPalla.getInstance().rect(xPos + startSandX, yPos + startSandY, sandXWidth, sandYWidth, 20);

        GiocoPalla.getInstance().popStyle();

        if(DEBUG)
            drawDebug();
    }

    @Override
    public void rotateX(float rotationX) {
        this.targetRotationX = rotationX;
    }

    @Override
    public void rotateY(float rotationY) {
        this.targetRotationY = rotationY;
    }

    private void applyRotationX(float delta) {
        rotate(delta, sandVolume / length, width, rightBorderHeight, leftBorderHeight,
                (sandStart, sandWidth) -> {
                    startSandX = sandStart;
                    sandXWidth = sandWidth;
                },
                (overflowingArea) -> {
                    final float overflowingVolume = (float) (overflowingArea * sandYWidth);
                    final PoolEntity pool = delta > 0 ? rightPool : leftPool;

                    if(pool != null) {
                        sandVolume -= overflowingVolume;
                        pool.addSandVolume(overflowingVolume);
                    }
                });
    }

    private void applyRotationY(float delta) {
        rotate(delta, sandVolume / width, length, topBorderHeight, bottomBorderHeight,
                (sandStart, sandWidth) -> {
                    startSandY = sandStart;
                    sandYWidth = sandWidth;
                },
                (overflowingArea) -> {
                    final float overflowingVolume = (float) (overflowingArea * sandXWidth);
                    final PoolEntity pool = delta > 0 ? topPool : bottomPool;

                    if(pool != null) {
                        sandVolume -= overflowingVolume;
                        pool.addSandVolume(overflowingVolume);
                    }
                });
    }

    private static void rotate(float delta,
                               float area,
                               float width,
                               float borderHeight1, float borderHeight2,
                               BiConsumer<Float, Float> onWaterBounds,
                               DoubleConsumer overflowingWater) {

        // Prevent divisons by 0

        if (delta == 0) {
            onWaterBounds.accept(0f, width);
            return;
        }

        // For comments on how it works refer to
        // the Paint.NET file /docs/PAINT_POOL.pdn
        // or to the exported /docs/PAINT_POOL.png

        float border = borderHeight1;

        boolean reversed = false;
        if (delta < 0) {
            // If the angle is on the other side I can use the same exact
            // method to calculate stuff and then just change the point
            // where I start to draw sand
            delta = -delta;
            border = borderHeight2;
            reversed = true;
        }

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
        float b = a * FastMath.sin(beta);
        float c = a * FastMath.sin(gamma);

        // Find area of the sand which got out of the box

        // Refer to
        // the Paint.NET file /docs/PAINT_OVERFLOWING_POOL.pdn
        // or to the exported /docs/PAINT_OVERFLOWING_POOL.png

        if (c > border) {
            final float toRemoveHeight = c - border;
            final float toRemoveBase = toRemoveHeight * FastMath.sin(beta); // Still using the law of sines
            final float toRemoveArea = toRemoveBase * toRemoveHeight / 2f;
            overflowingWater.accept(toRemoveArea);
        }

        // Make the last difference and find where the sand starts

        b = Math.min(width, b);
        // Unused
//        c = Math.min(border, c);

        if (!reversed)
            onWaterBounds.accept(width - b, b);
        else
            onWaterBounds.accept(0f, b);
    }

    /**
     * @brief Disegna per ogni direzione se è presente una vasca (Pool) e l'altezza utilizzata (h), per rendere
     *         più semplice la ricerca di bug.
     */
    private void drawDebug() {
        GiocoPalla.getInstance().pushStyle();

        final float textSize = 10;
        GiocoPalla.getInstance().textSize(textSize);

        // Make the color reverse based on background
        GiocoPalla.getInstance().blendMode(PConstants.DIFFERENCE);
        GiocoPalla.getInstance().fill(Color.white.getRGB());

        GiocoPalla.getInstance().textAlign(PConstants.CENTER, PConstants.TOP);
        GiocoPalla.getInstance().text("Pool: " + (topPool != null),
                xPos + width / 2f,
                yPos);
        GiocoPalla.getInstance().text("h: " + String.format("%1$.2g", topBorderHeight),
                xPos + width / 2f,
                yPos + textSize + 0.25f);

        GiocoPalla.getInstance().text("Pool: " + (bottomPool != null),
                xPos + width / 2f,
                yPos + length - textSize);
        GiocoPalla.getInstance().text("h: " + String.format("%1$.2g", bottomBorderHeight),
                xPos + width / 2f,
                yPos + length - textSize - textSize - 0.25f);

        GiocoPalla.getInstance().textAlign(PConstants.LEFT, PConstants.TOP);
        GiocoPalla.getInstance().text("Pool: " + (leftPool != null),
                xPos,
                yPos + length / 2 - (0.125f + textSize) * 2);
        GiocoPalla.getInstance().text("h: " + String.format("%1$.2g", leftBorderHeight),
                xPos,
                yPos + length / 2 - 0.125f - textSize);

        GiocoPalla.getInstance().textAlign(PConstants.RIGHT, PConstants.TOP);
        GiocoPalla.getInstance().text("Pool: " + (rightPool != null),
                xPos + width,
                yPos + length / 2 + 0.125f);
        GiocoPalla.getInstance().text("h: " + String.format("%1$.2g", rightBorderHeight),
                xPos + width,
                yPos + length / 2 + 0.25f + textSize);

        GiocoPalla.getInstance().popStyle();
    }

    // Important getters and setters

    public void setWidth(float width) {
        this.width = width;
        invalidateRotationX = true;
    }

    public void setLength(float length) {
        this.length = length;
        invalidateRotationY = true;
    }

    private void setSandVolume(float sandVolume) {
        this.sandVolume = sandVolume;
        invalidateRotationX = true;
        invalidateRotationY = true;
    }

    private void addSandVolume(float sandVolume) {
        setSandVolume(this.sandVolume + sandVolume);
    }

    // Package access so the entityManager can use them

    void setTopPool(PoolEntity topPool) {
        this.topPool = topPool;
    }

    void setBottomPool(PoolEntity bottomPool) {
        this.bottomPool = bottomPool;
    }

    void setRightPool(PoolEntity rightPool) {
        this.rightPool = rightPool;
    }

    void setLeftPool(PoolEntity leftPool) {
        this.leftPool = leftPool;
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

    public float getTopBorderHeight() {
        return topBorderHeight;
    }

    public void setTopBorderHeight(float topBorderHeight) {
        this.topBorderHeight = topBorderHeight;
    }

    public float getBottomBorderHeight() {
        return bottomBorderHeight;
    }

    public void setBottomBorderHeight(float bottomBorderHeight) {
        this.bottomBorderHeight = bottomBorderHeight;
    }

    public float getSandVolume() {
        return sandVolume;
    }

    public float getTargetRotationX() {
        return targetRotationX;
    }

    public float getTargetRotationY() {
        return targetRotationY;
    }
}
