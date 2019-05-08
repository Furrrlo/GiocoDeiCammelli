package me.palla.util;

import me.palla.GiocoPalla;

public class ScaledResolution {

    private final float width;
    private final float height;

    private final float scaledWidth;
    private final float scaledHeight;

    private final float widthScaleFactor;
    private final float heightScaleFactor;

    @SuppressWarnings("unused")
    public ScaledResolution(float width,
                            float height,
                            float scaledWidth,
                            float scaledHeight) {
        this.width = width;
        this.height = height;
        this.scaledWidth = scaledWidth;
        this.scaledHeight = scaledHeight;

        this.heightScaleFactor = this.widthScaleFactor = Math.min(height / scaledHeight, width / scaledWidth);
    }

    public ScaledResolution(float width,
                            float height,
                            float scaleFactor) {
        this.width = width;
        this.height = height;
        this.widthScaleFactor = scaleFactor;
        this.heightScaleFactor = scaleFactor;

        this.scaledWidth = width * scaleFactor;
        this.scaledHeight = height * scaleFactor;
    }

    public void setupScaling(boolean enable) {
        if(enable) {
            GiocoPalla.getInstance().translate(getWidthDifference() / 2, getHeightDifference() / 2);
            GiocoPalla.getInstance().scale(getWidthScaleFactor(), getHeightScaleFactor());
        } else {
            GiocoPalla.getInstance().scale(1 / getWidthScaleFactor(), 1 / getHeightScaleFactor());
            GiocoPalla.getInstance().translate(-getWidthDifference() / 2, -getHeightDifference() / 2);
        }
    }

    public float scaleX(float toScale) {
        return (toScale - getWidthDifference() / 2) / getWidthScaleFactor();
    }

    public float scaleY(float toScale) {
        return (toScale - getHeightDifference() / 2) / getHeightScaleFactor();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getScaledWidth() {
        return scaledWidth;
    }

    public float getScaledHeight() {
        return scaledHeight;
    }

    public float getWidthScaleFactor() {
        return widthScaleFactor;
    }

    public float getHeightScaleFactor() {
        return heightScaleFactor;
    }

    public float getWidthDifference() {
        return width - scaledWidth * widthScaleFactor;
    }

    public float getHeightDifference() {
        return height - scaledHeight * heightScaleFactor;
    }
}
