package renderer.processing.component;

import me.palla.gui.components.ColorSlider;
import me.palla.renderer.RenderContext;
import me.palla.value.ColorValue;
import processing.core.PApplet;
import renderer.processing.ProcessingRenderManager;
import renderer.processing.ProcessingRenderer;

import java.awt.*;

import static me.palla.gui.components.ColorSlider.*;

public class ColorSliderRenderer extends ProcessingRenderer<ColorSlider> {

    public ColorSliderRenderer(ProcessingRenderManager renderManager, PApplet applet) {
        super(renderManager, applet);
    }

    @Override
    public void onResize(ColorSlider toRender) {
        applet.pushStyle();

        applet.textSize(SMALL_FONT_HEIGHT);
        final float maxHexTextWidth = applet.textWidth("#FFFFFF");
        applet.textSize(FONT_HEIGHT);
        final float textWidth = applet.textWidth(toRender.getValue().getName());

        applet.popStyle();

        toRender.setMaxHexTextWidth(maxHexTextWidth);
        toRender.setTextWidth(textWidth);
    }

    @Override
    public void onRender(RenderContext ctx, ColorSlider toRender) {

        applet.pushStyle();

        // Style

        applet.textAlign(LEFT, TOP);
        applet.stroke(0, 0, 0);

        // Calculate hex

        applet.textSize(SMALL_FONT_HEIGHT);
        final String hex = toRender.getHex();
        final float hexTextWidth = applet.textWidth(hex);

        applet.translate(toRender.getX(), toRender.getY());

        // First line

        applet.rectMode(CORNER);

        applet.fill(0xFFFFFFFF);
        applet.textSize(FONT_HEIGHT);
        applet.text(toRender.getValue().getName(), 0, (toRender.getFirstLineHeight() - FONT_HEIGHT) / 2F);

        applet.translate(toRender.getTextWidth() + TEXT_BORDER, 0);

        applet.fill(toRender.getValue().getRGB());
        applet.rect(0, toRender.getFirstLineHeight() - toRender.getSliderHeight(),
                toRender.getRectWidth(), toRender.getSliderHeight(), toRender.getRadius());
        applet.translate(-(toRender.getTextWidth() + TEXT_BORDER), 0);

        // Second line

        applet.rectMode(CORNERS);

        applet.translate(0, toRender.getFirstLineHeight() + VERTICAL_BORDER);
        applet.fill(0xFFFFFFFF);
        applet.rect(0, -1, toRender.getRectWidth(), toRender.getSliderHeight() + 1, toRender.getRadius());
        applet.translate(toRender.getRectWidth() + RECT_BORDER, 0);

        applet.fill(0xFF000000);
        applet.rect(0, -1, toRender.getRectWidth(), toRender.getSliderHeight() + 1, toRender.getRadius());
        applet.translate(toRender.getRectWidth() + RECT_BORDER, 0);

        drawBorderedRainbowRect(0, -1, toRender.getSliderWidth(), toRender.getSliderHeight() + 1, toRender.getRadius());

        applet.fill(0xFFFFFFFF);
        applet.textSize(SMALL_FONT_HEIGHT);
        applet.text(hex, (toRender.getSliderWidth() - hexTextWidth) / 2, 0);

        applet.translate(toRender.getSliderWidth() + RECT_BORDER, 0);
        applet.fill(ColorValue.getRainbowColor().getRGB());
        applet.rect(0, -1, toRender.getRectWidth(), toRender.getSliderHeight() + 1, toRender.getRadius());

        applet.translate(-(toRender.getSliderWidth() + RECT_BORDER), 0);
        applet.translate(-(toRender.getRectWidth() + RECT_BORDER), 0);
        applet.translate(-(toRender.getRectWidth() + RECT_BORDER), 0);
        applet.translate(0, -(toRender.getFirstLineHeight() + VERTICAL_BORDER));

        // End

        applet.translate(-toRender.getX(), -toRender.getY());

        applet.popStyle();
    }

    private void drawBorderedRainbowRect(float left, float top,
                                         float right, float bottom,
                                         float radius) {

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

        applet.pushStyle();

        float lenght = right - left;
        float hueSkip = 1000f / (lenght * 10f);
        float hue = 0;

        applet.beginShape(LINES);
        for (int i = 0; i <= 90; i++) {
            applet.stroke(Color.getHSBColor(hue / 1000f, 1, 1).getRGB());
            applet.vertex(
                    (float) (left + radius + Math.sin((i + 180) * 3.141526 / 180.0) * radius),
                    (float) (top + radius + Math.cos((i + 180) * 3.141526 / 180.0) * radius)
            );
            applet.vertex(
                    (float) (left + radius + Math.sin((i + 180) * 3.141526 / 180.0) * radius),
                    (float) (bottom - (radius + Math.cos((i + 180) * 3.141526 / 180.0) * radius))
            );
            hue += (radius / 90f) * hueSkip;
        }

        for (float i = left + radius; i < right - radius; i += 0.1f) {
            applet.stroke(Color.getHSBColor(hue / 1000f, 1, 1).getRGB());
            applet.vertex(i, top);
            applet.vertex(i, bottom);
            hue += hueSkip;
        }

        for (int i = 0; i <= 90; i++) {
            applet.stroke(Color.getHSBColor(hue / 1000f, 1, 1).getRGB());
            applet.vertex(
                    (float) (right - radius - Math.sin((i + 180) * 3.141526 / 180.0) * radius),
                    (float) (top + radius + Math.cos((i + 180) * 3.141526 / 180.0) * radius)
            );
            applet.vertex(
                    (float) (right - radius - Math.sin((i + 180) * 3.141526 / 180.0) * radius),
                    (float) (bottom - (radius + Math.cos((i + 180) * 3.141526 / 180.0) * radius))
            );
            hue += (radius / 90) * hueSkip;
        }
        applet.endShape();

        applet.popStyle();
    }
}
