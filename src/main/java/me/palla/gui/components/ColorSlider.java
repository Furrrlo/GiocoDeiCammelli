package me.palla.gui.components;

import me.palla.GiocoPalla;
import me.palla.value.ColorValue;

import java.awt.*;

public class ColorSlider extends BaseGuiComponent {

    private static final float FONT_HEIGHT = 18 * 2;
    private static final float SMALL_FONT_HEIGHT = FONT_HEIGHT * 15F / 18F;

    private static final float VERTICAL_BORDER = 6;
    private static final float TEXT_BORDER = 5;
    private static final float RECT_BORDER = 3;

    private float textWidth;

    private float sliderWidth;
    private float sliderHeight;
    private float rectWidth;
    private float radius;

    private float firstLineHeight;
    private float secondLineHeight;

    private final ColorValue value;

    public ColorSlider(ColorValue value) {
        this.value = value;
        onResize();
    }

    @Override
    protected void onResize() {

        GiocoPalla.getInstance().textSize(SMALL_FONT_HEIGHT);
        final float maxHexTextWidth = GiocoPalla.getInstance().textWidth("#FFFFFF");
        GiocoPalla.getInstance().textSize(FONT_HEIGHT);
        textWidth = GiocoPalla.getInstance().textWidth(value.getName());

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
    public void onRender() {

        GiocoPalla.getInstance().pushStyle();

        // Style

        GiocoPalla.getInstance().textAlign(LEFT, TOP);
        GiocoPalla.getInstance().stroke(0, 0, 0);

        // Calculate hex

        GiocoPalla.getInstance().textSize(SMALL_FONT_HEIGHT);
        final String hex = getHex();
        final float hexTextWidth = GiocoPalla.getInstance().textWidth(hex);

        GiocoPalla.getInstance().translate(x, y);

        // First line

        GiocoPalla.getInstance().rectMode(CORNER);

        GiocoPalla.getInstance().fill(0xFFFFFFFF);
        GiocoPalla.getInstance().textSize(FONT_HEIGHT);
        GiocoPalla.getInstance().text(value.getName(), 0, (firstLineHeight - FONT_HEIGHT) / 2F);

        GiocoPalla.getInstance().translate(textWidth + TEXT_BORDER, 0);

        GiocoPalla.getInstance().fill(value.getRGB());
        GiocoPalla.getInstance().rect(0, firstLineHeight - sliderHeight, rectWidth, sliderHeight, radius);
        GiocoPalla.getInstance().translate(-(textWidth + TEXT_BORDER), 0);

        // Second line

        GiocoPalla.getInstance().rectMode(CORNERS);

        GiocoPalla.getInstance().translate(0, firstLineHeight + VERTICAL_BORDER);
        GiocoPalla.getInstance().fill(0xFFFFFFFF);
        GiocoPalla.getInstance().rect(0, -1, rectWidth, sliderHeight + 1, radius);
        GiocoPalla.getInstance().translate(rectWidth + RECT_BORDER, 0);

        GiocoPalla.getInstance().fill(0xFF000000);
        GiocoPalla.getInstance().rect(0, -1, rectWidth, sliderHeight + 1, radius);
        GiocoPalla.getInstance().translate(rectWidth + RECT_BORDER,0);

        drawBorderedRainbowRect(0, -1, sliderWidth, sliderHeight + 1, radius);

        GiocoPalla.getInstance().fill(0xFFFFFFFF);
        GiocoPalla.getInstance().textSize(SMALL_FONT_HEIGHT);
        GiocoPalla.getInstance().text(hex, (sliderWidth - hexTextWidth) / 2, 0);

        GiocoPalla.getInstance().translate(sliderWidth + RECT_BORDER, 0);
        GiocoPalla.getInstance().fill(ColorValue.getRainbowColor().getRGB());
        GiocoPalla.getInstance().rect(0, -1, rectWidth, sliderHeight + 1, radius);

        GiocoPalla.getInstance().translate(-(sliderWidth + RECT_BORDER),0);
        GiocoPalla.getInstance().translate(-(rectWidth + RECT_BORDER),0);
        GiocoPalla.getInstance().translate(-(rectWidth + RECT_BORDER),0);
        GiocoPalla.getInstance().translate(0, -(firstLineHeight + VERTICAL_BORDER));

        // End

        GiocoPalla.getInstance().translate(-x, -y);

        GiocoPalla.getInstance().popStyle();
    }

    private String getHex() {
        String hex = Integer.toHexString(value.getRGB()).toUpperCase();
        if(hex.length() > 2)
            hex = hex.substring(2);
        hex = "#" + hex;

        return hex;
    }

    @Override
    public void onClick(float xPos, float yPos) {

        final float secondLineY = y + firstLineHeight + VERTICAL_BORDER - 1;

        if(collides(x, secondLineY, rectWidth, secondLineHeight, xPos, yPos)) {
            value.set(Color.white);
            value.setRainbow(false);
        } else if(collides(x + rectWidth + RECT_BORDER, secondLineY, rectWidth, secondLineHeight, xPos, yPos)) {
            value.set(Color.black);
            value.setRainbow(false);
        } else if(collides(x + (rectWidth + RECT_BORDER) * 2, secondLineY, sliderWidth, sliderHeight, xPos, yPos)) {
            final float sliderX = x + (rectWidth + RECT_BORDER) * 2;
            final float currentHue = 999 * (xPos - sliderX) / sliderWidth;
            value.set(Color.getHSBColor(currentHue / 1000f, 1, 1));
            value.setRainbow(false);
        } else if(collides(x + (rectWidth + RECT_BORDER) * 2 + sliderWidth + RECT_BORDER,
                secondLineY, rectWidth, sliderHeight, xPos, yPos)) {
            value.setRainbow(true);
        }
    }

    private static boolean collides(float minX, float minY, float width, float height, float actualX, float actualY) {
        return minX <= actualX && actualX <= minX + width && minY <= actualY && actualY <= minY + height;
    }

    private static void drawBorderedRainbowRect(float left, float top, float right, float bottom, float radius) {
        if (left > right) {
            float swap = left;
            left = right;
            right = swap;
        }

        if (top > bottom) {
            float swap = top;
            top = bottom;
            bottom = swap;
        }

        GiocoPalla.getInstance().pushStyle();

        float lenght = right - left;
        float hueSkip = 1000f / (lenght * 10f);
        float hue = 0;

        GiocoPalla.getInstance().beginShape(LINES);
        for(int i = 0; i <= 90; i++) {
            GiocoPalla.getInstance().stroke(Color.getHSBColor(hue / 1000f, 1, 1).getRGB());
            GiocoPalla.getInstance().vertex(
                    (float) (left + radius + Math.sin((i + 180) * 3.141526 / 180.0) * radius),
                    (float) (top + radius + Math.cos((i + 180) * 3.141526 / 180.0) * radius)
            );
            GiocoPalla.getInstance().vertex(
                    (float) (left + radius + Math.sin((i + 180) * 3.141526 / 180.0) * radius),
                    (float) (bottom - (radius + Math.cos((i + 180) * 3.141526 / 180.0) * radius))
            );
            hue += (radius / 90f) * hueSkip;
        }

        for(float i = left + radius; i < right - radius; i += 0.1f) {
            GiocoPalla.getInstance().stroke(Color.getHSBColor(hue / 1000f, 1, 1).getRGB());
            GiocoPalla.getInstance().vertex(i, top);
            GiocoPalla.getInstance().vertex(i, bottom);
            hue += hueSkip;
        }

        for(int i = 0; i <= 90; i++) {
            GiocoPalla.getInstance().stroke(Color.getHSBColor(hue / 1000f, 1, 1).getRGB());
            GiocoPalla.getInstance().vertex(
                    (float) (right - radius - Math.sin((i + 180) * 3.141526 / 180.0) * radius),
                    (float) (top + radius + Math.cos((i + 180) * 3.141526 / 180.0) * radius)
            );
            GiocoPalla.getInstance().vertex(
                    (float) (right - radius - Math.sin((i + 180) * 3.141526 / 180.0) * radius),
                    (float) (bottom - (radius + Math.cos((i + 180) * 3.141526 / 180.0) * radius))
            );
            hue += (radius / 90) * hueSkip;
        }
        GiocoPalla.getInstance().endShape();

        GiocoPalla.getInstance().popStyle();
    }
}
