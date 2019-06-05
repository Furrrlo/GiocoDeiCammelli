package renderer.swing.component;

import me.palla.gui.components.PauseMenuButton;
import renderer.swing.SwingContext;
import renderer.swing.SwingRenderManager;
import renderer.swing.SwingRenderer;

import java.awt.*;

public class PauseMenuButtonRenderer extends SwingRenderer<PauseMenuButton> {

    public PauseMenuButtonRenderer(SwingRenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void onResize(PauseMenuButton toRender) {

        final Graphics2D g2d = (Graphics2D) getGraphics();

        final Font newFont = g2d.getFont().deriveFont(toRender.getHeight());
        final float textWidth = g2d.getFontMetrics(newFont).stringWidth(toRender.getContent());
        toRender.setTextWidth(textWidth);
    }

    @Override
    public void onRender(SwingContext ctx, Graphics2D g2d, PauseMenuButton toRender) {

//        g2d.fillRect((int) toRender.getX(), (int) toRender.getY(), (int) toRender.getWidth(), (int) toRender.getHeight());

        ctx.textAlign(SwingContext.HorizontalAlignment.LEFT, SwingContext.VerticalAlignment.TOP);
        ctx.textSize(toRender.getHeight());

        if (toRender.isHovered())
            g2d.setColor(toRender.getFocusedColor());
        else
            g2d.setColor(toRender.getColor());
        ctx.drawString(toRender.getContent(), toRender.getX(), toRender.getY());
    }
}
