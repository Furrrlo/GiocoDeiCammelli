package renderer.swing.component;

import me.palla.gui.components.ColorSlider;
import me.palla.value.ColorValue;
import renderer.swing.SwingContext;
import renderer.swing.SwingRenderManager;
import renderer.swing.SwingRenderer;

import java.awt.*;
import java.awt.geom.Line2D;

import static me.palla.gui.components.ColorSlider.*;

public class ColorSliderRenderer extends SwingRenderer<ColorSlider> {

    public ColorSliderRenderer(SwingRenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void onResize(ColorSlider toRender) {

        Graphics2D g2d = (Graphics2D) getGraphics();

        final Font smallFont = g2d.getFont().deriveFont(SMALL_FONT_HEIGHT);
        final float maxHexTextWidth = g2d.getFontMetrics(smallFont).stringWidth("#FFFFFF");

        final Font normalFont = g2d.getFont().deriveFont(FONT_HEIGHT);
        final float textWidth = g2d.getFontMetrics(normalFont).stringWidth(toRender.getValue().getName());

        toRender.setMaxHexTextWidth(maxHexTextWidth);
        toRender.setTextWidth(textWidth);
    }

    @Override
    public void onRender(SwingContext ctx, Graphics2D g2d, ColorSlider toRender) {

        // Style

        ctx.textAlign(SwingContext.HorizontalAlignment.LEFT, SwingContext.VerticalAlignment.TOP);

        // Calculate hex

        ctx.textSize(SMALL_FONT_HEIGHT);
        final String hex = toRender.getHex();
        final float hexTextWidth = g2d.getFontMetrics().stringWidth(hex);

        g2d.translate(toRender.getX(), toRender.getY());

        // First line

        g2d.setColor(Color.white);
        ctx.textSize(FONT_HEIGHT);
        ctx.drawString(toRender.getValue().getName(), 0, (toRender.getFirstLineHeight() - FONT_HEIGHT) / 2F);

        g2d.translate(toRender.getTextWidth() + TEXT_BORDER, 0);

        ctx.fillBorderedRoundRect(0,
                (int) (toRender.getFirstLineHeight() - toRender.getSliderHeight()),
                (int) toRender.getRectWidth(),
                (int) toRender.getSliderHeight(),
                (int) toRender.getRadius() * 2,
                Color.black, toRender.getValue().get());
        g2d.translate(-(toRender.getTextWidth() + TEXT_BORDER), 0);

        // Second line

        g2d.translate(0, toRender.getFirstLineHeight() + VERTICAL_BORDER);
        ctx.fillBorderedRoundRect(
                0, -1,
                (int) toRender.getRectWidth(),
                (int) toRender.getSliderHeight(),
                (int) toRender.getRadius() * 2,
                Color.black, Color.white);
        g2d.translate(toRender.getRectWidth() + RECT_BORDER, 0);

        g2d.setColor(Color.black);
        g2d.fillRoundRect(0, -1,
                (int) toRender.getRectWidth(),
                (int) toRender.getSliderHeight(),
                (int) toRender.getRadius() * 2, (int) toRender.getRadius() * 2);
        g2d.translate(toRender.getRectWidth() + RECT_BORDER, 0);

        drawBorderedRainbowRect(ctx, g2d, 0, -1, toRender.getSliderWidth(), toRender.getSliderHeight() + 1, toRender.getRadius());

        g2d.setColor(Color.white);
        ctx.textSize(SMALL_FONT_HEIGHT);
        ctx.drawString(hex, (toRender.getSliderWidth() - hexTextWidth) / 2, 0);

        g2d.translate(toRender.getSliderWidth() + RECT_BORDER, 0);
        ctx.fillBorderedRoundRect(0, -1,
                (int) toRender.getRectWidth(),
                (int) toRender.getSliderHeight(),
                (int) toRender.getRadius() * 2,
                Color.black, ColorValue.getRainbowColor());

        g2d.translate(-(toRender.getSliderWidth() + RECT_BORDER), 0);
        g2d.translate(-(toRender.getRectWidth() + RECT_BORDER), 0);
        g2d.translate(-(toRender.getRectWidth() + RECT_BORDER), 0);
        g2d.translate(0, -(toRender.getFirstLineHeight() + VERTICAL_BORDER));

        // End

        g2d.translate(-toRender.getX(), -toRender.getY());
    }

    private void drawBorderedRainbowRect(SwingContext ctx,
                                         Graphics2D g2d,
                                         float left, float top,
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

        float lenght = right - left;
        float hueSkip = 1000f / (lenght * 10f);
        float hue = 0;

        for (int i = 0; i <= 90; i++) {
            g2d.setColor(Color.getHSBColor(hue / 1000f, 1, 1));
            g2d.draw(new Line2D.Float(
                    (float) (left + radius + Math.sin((i + 180) * 3.141526 / 180.0) * radius),
                    (float) (top + radius + Math.cos((i + 180) * 3.141526 / 180.0) * radius),
                    (float) (left + radius + Math.sin((i + 180) * 3.141526 / 180.0) * radius),
                    (float) (bottom - (radius + Math.cos((i + 180) * 3.141526 / 180.0) * radius))
            ));
            hue += (radius / 90f) * hueSkip;
        }

        for (float i = left + radius; i < right - radius; i += 0.1f) {
            g2d.setColor(Color.getHSBColor(hue / 1000f, 1, 1));
            g2d.draw(new Line2D.Float(i, top, i, bottom));
            hue += hueSkip;
        }

        for (int i = 0; i <= 90; i++) {
            g2d.setColor(Color.getHSBColor(hue / 1000f, 1, 1));
            g2d.draw(new Line2D.Float(
                    (float) (right - radius - Math.sin((i + 180) * 3.141526 / 180.0) * radius),
                    (float) (top + radius + Math.cos((i + 180) * 3.141526 / 180.0) * radius),
                    (float) (right - radius - Math.sin((i + 180) * 3.141526 / 180.0) * radius),
                    (float) (bottom - (radius + Math.cos((i + 180) * 3.141526 / 180.0) * radius))
            ));
            hue += (radius / 90) * hueSkip;
        }
    }
}
