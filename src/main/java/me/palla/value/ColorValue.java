package me.palla.value;

import java.awt.*;

public class ColorValue extends Value<Color> {

    private boolean isRainbow;

    public ColorValue(String name, Color defaultValue) {
        super(name, defaultValue);
        isRainbow = false;
    }

    public ColorValue(String name, int r, int g, int b) {
        this(name, new Color(r, g, b));
    }

    @Override
    public Color get() {
        if (isRainbow)
            return getRainbowColor();
        return super.get();
    }

    public int getRGB() {
        return get().getRGB();
    }

    public boolean isRainbow() {
        return isRainbow;
    }

    public void setRainbow(boolean rainbow) {
        isRainbow = rainbow;
    }

    public static Color getRainbowColor() {
        return Color.getHSBColor(System.nanoTime() / 10000000000f, 0.8f, 1);
    }
}
