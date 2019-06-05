package renderer.processing;

import me.palla.renderer.BaseRenderer;
import processing.core.PApplet;
import processing.core.PConstants;

public abstract class ProcessingRenderer<T> extends BaseRenderer<T, ProcessingRenderManager> implements PConstants {

    protected final PApplet applet;

    public ProcessingRenderer(ProcessingRenderManager renderManager,
                              PApplet applet) {
        super(renderManager);
        this.applet = applet;
    }
}
