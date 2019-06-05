package renderer.composite;

import me.palla.renderer.BaseRenderer;
import me.palla.renderer.RenderContext;
import me.palla.renderer.Renderer;

import java.util.Map;

public class CompositeRenderer<T> extends BaseRenderer<T, CompositeRenderManager> {

    private final Map<Class<?>, Renderer<T>> actualRenderers;

    public CompositeRenderer(CompositeRenderManager renderManager,
                             Map<Class<?>, Renderer<T>> actualRenderers) {
        super(renderManager);
        this.actualRenderers = actualRenderers;
    }

    @Override
    public void onRender(RenderContext ctx, T toRender) {
        final Renderer<T> actualRenderer = actualRenderers.get(ctx.getClass());
        if(actualRenderer != null)
            actualRenderer.onRender(ctx, toRender);
    }

    @Override
    public void onResize(T toRender) {
        actualRenderers.values().forEach(renderer -> renderer.onResize(toRender));
    }
}
