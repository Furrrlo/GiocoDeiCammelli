package renderer.swing.gui;

import me.palla.gui.BaseGui;
import renderer.swing.SwingContext;
import renderer.swing.SwingRenderManager;
import renderer.swing.SwingRenderer;

import java.awt.*;

public class BaseGuiRenderer extends SwingRenderer<BaseGui> {

    public BaseGuiRenderer(SwingRenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void onRender(SwingContext ctx, Graphics2D g2d, BaseGui toRender) {
        g2d.setColor(toRender.getBackgroundColor());
        g2d.fillRect(0, 0, (int) toRender.getWidth(), (int) toRender.getHeight());
    }
}
