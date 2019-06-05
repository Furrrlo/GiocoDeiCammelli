package me.palla.renderer;

public abstract class BaseRenderer<T, R extends RenderManager> implements Renderer<T> {

    protected final R renderManager;

    public BaseRenderer(R renderManager) {
        this.renderManager = renderManager;
    }
}
