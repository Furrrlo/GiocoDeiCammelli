package renderer.processing.component;

import me.palla.gui.components.PauseMenuButton;
import me.palla.renderer.RenderContext;
import processing.core.PApplet;
import renderer.processing.ProcessingRenderManager;
import renderer.processing.ProcessingRenderer;

public class PauseMenuButtonRenderer extends ProcessingRenderer<PauseMenuButton> {
    
    public PauseMenuButtonRenderer(ProcessingRenderManager renderManager, PApplet applet) {
        super(renderManager, applet);
    }

    @Override
    public void onResize(PauseMenuButton toRender) {
        applet.pushStyle();
        applet.textSize(toRender.getHeight());
        toRender.setTextWidth(applet.textWidth(toRender.getContent()));
        applet.popStyle();
    }

    @Override
    public void onRender(RenderContext ctx, PauseMenuButton toRender) {

        applet.pushStyle();

//        applet.rect(toRender.getX(), toRender.getY(), toRender.getWidth(), toRender.getHeight());

        applet.textAlign(LEFT, TOP);
        applet.textSize(toRender.getHeight());
        if (toRender.isHovered())
            applet.fill(toRender.getFocusedColor().getRGB());
        else
            applet.fill(toRender.getColor().getRGB());
        applet.text(toRender.getContent(), toRender.getX(), toRender.getY());

        applet.popStyle();
    }
}
