package renderer.processing.gui;

import me.palla.gui.BaseGui;
import me.palla.renderer.RenderContext;
import processing.core.PApplet;
import renderer.processing.ProcessingRenderManager;
import renderer.processing.ProcessingRenderer;

public class BaseGuiRenderer extends ProcessingRenderer<BaseGui> {

    public BaseGuiRenderer(ProcessingRenderManager renderManager, PApplet applet) {
        super(renderManager, applet);
    }

    @Override
    public void onRender(RenderContext ctx, BaseGui toRender) {
        applet.pushStyle();

        applet.noStroke();
        applet.fill(toRender.getBackgroundColor().getRGB());
        applet.rect(0, 0, toRender.getWidth(), toRender.getHeight());

        applet.popStyle();
    }
}
