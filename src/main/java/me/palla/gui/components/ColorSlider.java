package me.palla.gui.components;

import me.palla.value.ColorValue;

import java.awt.*;

public class ColorSlider extends BaseGuiComponent<ColorSlider> {

    // Constants

    public static final float FONT_HEIGHT = 18 * 2;
    public static final float SMALL_FONT_HEIGHT = FONT_HEIGHT * 15F / 18F;

    public static final float VERTICAL_BORDER = 6;
    public static final float TEXT_BORDER = 5;
    public static final float RECT_BORDER = 3;

    // Attributes

    private float textWidth;
    private float maxHexTextWidth;

    private float sliderWidth;
    private float sliderHeight;
    private float rectWidth;
    private float radius;

    private float firstLineHeight;
    private float secondLineHeight;

    private final ColorValue value;

    public ColorSlider(ColorValue value) {
        this.value = value;
    }

    @Override
    protected void onResize() {
        super.onResize();

        sliderWidth = maxHexTextWidth + 40;
        rectWidth = sliderHeight = SMALL_FONT_HEIGHT + 2;
        radius = FONT_HEIGHT * 16f / 36f;

        firstLineHeight = FONT_HEIGHT;
        secondLineHeight = sliderHeight;

        final float firstLineWidth = textWidth + TEXT_BORDER + rectWidth;
        final float secondLineWidth = (rectWidth + RECT_BORDER) * 3 + sliderWidth;

        height = firstLineHeight + VERTICAL_BORDER + secondLineHeight;
        width = Math.max(firstLineWidth, secondLineWidth);
    }

    @Override
    public void onClick(float xPos, float yPos) {

        final float secondLineY = y + firstLineHeight + VERTICAL_BORDER - 1;

        if (collides(x, secondLineY, rectWidth, secondLineHeight, xPos, yPos)) {
            value.set(Color.white);
            value.setRainbow(false);
        } else if (collides(x + rectWidth + RECT_BORDER, secondLineY, rectWidth, secondLineHeight, xPos, yPos)) {
            value.set(Color.black);
            value.setRainbow(false);
        } else if (collides(x + (rectWidth + RECT_BORDER) * 2, secondLineY, sliderWidth, sliderHeight, xPos, yPos)) {
            final float sliderX = x + (rectWidth + RECT_BORDER) * 2;
            final float currentHue = 999 * (xPos - sliderX) / sliderWidth;
            value.set(Color.getHSBColor(currentHue / 1000f, 1, 1));
            value.setRainbow(false);
        } else if (collides(x + (rectWidth + RECT_BORDER) * 2 + sliderWidth + RECT_BORDER,
                secondLineY, rectWidth, sliderHeight, xPos, yPos)) {
            value.setRainbow(true);
        }
    }

    private static boolean collides(float minX, float minY, float width, float height, float actualX, float actualY) {
        return minX <= actualX && actualX <= minX + width && minY <= actualY && actualY <= minY + height;
    }

    // Getters

    public String getHex() {
        String hex = Integer.toHexString(value.getRGB()).toUpperCase();
        if (hex.length() > 2)
            hex = hex.substring(2);
        hex = "#" + hex;

        return hex;
    }

    public ColorValue getValue() {
        return value;
    }

    public float getTextWidth() {
        return textWidth;
    }

    public float getSliderWidth() {
        return sliderWidth;
    }

    public float getSliderHeight() {
        return sliderHeight;
    }

    public float getRectWidth() {
        return rectWidth;
    }

    public float getRadius() {
        return radius;
    }

    public float getFirstLineHeight() {
        return firstLineHeight;
    }

    public float getSecondLineHeight() {
        return secondLineHeight;
    }

    // Setters

    public void setTextWidth(float textWidth) {
        this.textWidth = textWidth;
    }

    public void setMaxHexTextWidth(float maxHexTextWidth) {
        this.maxHexTextWidth = maxHexTextWidth;
    }
}
