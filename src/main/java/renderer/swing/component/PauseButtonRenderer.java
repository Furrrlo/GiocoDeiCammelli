package renderer.swing.component;

import me.palla.gui.components.PauseButton;
import renderer.swing.SwingContext;
import renderer.swing.SwingRenderManager;
import renderer.swing.SwingRenderer;

import java.awt.*;

public class PauseButtonRenderer extends SwingRenderer<PauseButton> {

    private static final Stroke BIGGER_STROKE = new BasicStroke(2.5F);

    public PauseButtonRenderer(SwingRenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void onRender(SwingContext ctx, Graphics2D g2d, PauseButton toRender) {

        Color inside, border;
        if (toRender.isHovered()) {
            inside = toRender.getFocusedColor();
            border = toRender.getFocusedStrokeColor();
        } else {
            inside = toRender.getRectColor();
            border = toRender.getStrokeColor();
        }

        final Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(BIGGER_STROKE);
        ctx.fillBorderedRoundRect(
                (int) toRender.getX(), (int) toRender.getY(),
                (int) toRender.getWidth(), (int) toRender.getHeight(), 20,
                border, inside);

        g2d.setStroke(oldStroke);
        inside = toRender.getStrokeColor();
        border = toRender.getFocusedStrokeColor();

        final float topPadding = toRender.getHeight() / 4;
        final float strokeWidth = toRender.getWidth() / 6;
        final float leftPadding = toRender.getHeight() / 4;

        ctx.fillBorderedRect(
                (int) (toRender.getX() + leftPadding),
                (int) (toRender.getY() + topPadding),
                (int) strokeWidth,
                (int) (toRender.getHeight() - topPadding * 2),
                border, inside);
        ctx.fillBorderedRect(
                (int) (toRender.getX() + toRender.getWidth() - strokeWidth - leftPadding),
                (int) (toRender.getY() + topPadding),
                (int) strokeWidth,
                (int) (toRender.getHeight() - topPadding * 2),
                border, inside);
    }
}
