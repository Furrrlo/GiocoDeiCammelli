package renderer.swing.entity;

import me.palla.entity.BallEntity;
import renderer.swing.SwingContext;
import renderer.swing.SwingRenderManager;
import renderer.swing.SwingRenderer;

import java.awt.*;

public class BallRenderer extends SwingRenderer<BallEntity> {

    public BallRenderer(SwingRenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void onRender(SwingContext ctx, Graphics2D g2d, BallEntity toRender) {
        ctx.fillBorderedCircle(
                (int) toRender.getPosX(), (int) toRender.getPosY(),
                (int) toRender.getRadius(),
                Color.black, Color.red);
    }
}
