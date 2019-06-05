package me.palla.norender;

import me.palla.renderer.RenderManager;
import me.palla.renderer.Renderer;
import me.palla.util.ScaledResolution;

public class NoRenderManager implements RenderManager {

    // Constants

    private static final NoRenderManager INSTANCE = new NoRenderManager();

    // Attributes

    private final ScaledResolution scaledResolution;
    private final NoRenderer renderer;

    private NoRenderManager() {
        scaledResolution = new ScaledResolution(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        renderer = NoRenderer.instance();
    }

    public static NoRenderManager instance() {
        return INSTANCE;
    }

    @Override
    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

    @Override
    public float getMouseX() {
        return -1;
    }

    @Override
    public float getMouseY() {
        return -1;
    }

    @Override
    public <T> Renderer<? super T> getRendererFor(Class<T> classType) {
        return renderer;
    }
}
