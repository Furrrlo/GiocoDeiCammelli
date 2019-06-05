package renderer.swing;

import me.palla.renderer.BaseRenderer;
import me.palla.renderer.RenderContext;

import javax.swing.*;
import java.awt.*;

public abstract class SwingRenderer<T> extends BaseRenderer<T, SwingRenderManager> implements SwingConstants {

    public SwingRenderer(SwingRenderManager renderManager) {
        super(renderManager);
    }

    public void onRender(RenderContext ctx, T toRender) {
        if(!(ctx instanceof SwingContext))
            throw new UnsupportedOperationException("Invalid context, need a SwingContext");
        onRender((SwingContext) ctx, ((SwingContext) ctx).getGraphics(), toRender);
    }

    protected abstract void onRender(SwingContext ctx, Graphics2D g2d, T toRender);

    public Graphics getGraphics() {
        return renderManager.getGraphics();
    }
}
