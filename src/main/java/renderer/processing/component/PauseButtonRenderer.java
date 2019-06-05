package renderer.processing.component;

import me.palla.gui.components.PauseButton;
import me.palla.renderer.RenderContext;
import processing.core.PApplet;
import renderer.processing.ProcessingRenderManager;
import renderer.processing.ProcessingRenderer;

public class PauseButtonRenderer extends ProcessingRenderer<PauseButton> {

    public PauseButtonRenderer(ProcessingRenderManager renderManager, PApplet applet) {
        super(renderManager, applet);
    }

    @Override
    public void onRender(RenderContext ctx, PauseButton toRender) {
        applet.pushStyle();

        if (toRender.isHovered()) {
            applet.fill(toRender.getFocusedColor().getRGB());
            applet.stroke(toRender.getFocusedStrokeColor().getRGB());
        } else {
            applet.fill(toRender.getRectColor().getRGB());
            applet.stroke(toRender.getStrokeColor().getRGB());

            applet.strokeWeight(2.5F);
            applet.rect(toRender.getX(), toRender.getY(), toRender.getWidth(), toRender.getHeight(), 10);
        }

        applet.strokeWeight(2.5F);
        applet.rect(toRender.getX(), toRender.getY(), toRender.getWidth(), toRender.getHeight(), 10);

        applet.strokeWeight(1);
        applet.fill(toRender.getStrokeColor().getRGB());
        applet.stroke(toRender.getFocusedStrokeColor().getRGB());


        final float topPadding = toRender.getHeight() / 4;
        final float strokeWidth = toRender.getWidth() / 6;
        final float leftPadding = toRender.getHeight() / 4;

        applet.rect(
                toRender.getX() + leftPadding,
                toRender.getY() + topPadding,
                strokeWidth,
                toRender.getHeight() - topPadding * 2);
        applet.rect(
                toRender.getX() + toRender.getWidth() - strokeWidth - leftPadding,
                toRender.getY() + topPadding,
                strokeWidth,
                toRender.getHeight() - topPadding * 2);

        applet.popStyle();
    }
}
