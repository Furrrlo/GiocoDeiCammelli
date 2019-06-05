package renderer.processing.entity;

import me.palla.entity.BallEntity;
import me.palla.renderer.RenderContext;
import processing.core.PApplet;
import renderer.processing.ProcessingRenderManager;
import renderer.processing.ProcessingRenderer;

public class BallRenderer extends ProcessingRenderer<BallEntity> {

    public BallRenderer(ProcessingRenderManager renderManager, PApplet applet) {
        super(renderManager, applet);
    }

    @Override
    public void onRender(RenderContext ctx, BallEntity toRender) {
        applet.pushStyle();
        applet.fill(255, 0, 0);
        applet.ellipse(
                toRender.getPosX(), toRender.getPosY(),
                toRender.getRadius(), toRender.getRadius());
        applet.popStyle();
    }
}
