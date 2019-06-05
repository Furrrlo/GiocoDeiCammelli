package renderer.swing;

import me.palla.renderer.RenderContext;

import java.awt.*;

public class SwingContext implements RenderContext {

    private final Graphics2D g2d;

    private HorizontalAlignment horizontalAlignment = HorizontalAlignment.LEFT;
    private VerticalAlignment verticalAlignment = VerticalAlignment.BOTTOM;

    public SwingContext(Graphics2D g2d) {
        this.g2d = g2d;
    }

    public Graphics2D getGraphics() {
        return g2d;
    }

    public void fillBorderedRect(int x, int y,
                                 int width, int height,
                                 Color border,
                                 Color inside) {
        g2d.setColor(inside);
        g2d.fillRect(x, y, width, height);
        g2d.setColor(border);
        g2d.drawRect(x, y, width, height);
    }

    public void fillBorderedRoundRect(int x, int y,
                                      int width, int height,
                                      int diameter,
                                      Color border,
                                      Color inside) {
        g2d.setColor(inside);
        g2d.fillRoundRect(x, y, width, height, diameter, diameter);
        g2d.setColor(border);
        g2d.drawRoundRect(x, y, width, height, diameter, diameter);
    }

    public void fillBorderedCircle(int centerX, int centerY, int radius,
                                   Color border, Color inside) {
        g2d.setColor(inside);
        g2d.fillOval(centerX - radius / 2, centerY - radius / 2, radius, radius);
        g2d.setColor(border);
        g2d.drawOval(centerX - radius / 2, centerY - radius / 2, radius, radius);
    }

    public void drawString(String str, float x, float y) {

        final FontMetrics fontMetrics = g2d.getFontMetrics();

        switch (horizontalAlignment) {
            case LEFT:
                break;
            case RIGHT:
                x -= fontMetrics.stringWidth(str);
                break;
            case CENTER:
                x -= (fontMetrics.stringWidth(str) / 2F);
                break;
        }

        switch (verticalAlignment) {
            case BOTTOM:
                break;
            case TOP:
                y = y - fontMetrics.getLeading() + fontMetrics.getAscent();
                break;
            case CENTER:
                y = y - fontMetrics.getHeight() / 2F + fontMetrics.getAscent();
                break;
        }

        g2d.drawString(str, x, y);
    }

    public void textAlign(HorizontalAlignment horizontal, VerticalAlignment vertical) {
        this.horizontalAlignment = horizontal;
        this.verticalAlignment = vertical;
    }

    public void textSize(float textSize) {
        g2d.setFont(g2d.getFont().deriveFont(textSize));
    }

    public enum HorizontalAlignment {
        LEFT, RIGHT, CENTER
    }

    public enum VerticalAlignment {
        TOP, BOTTOM, CENTER
    }
}
