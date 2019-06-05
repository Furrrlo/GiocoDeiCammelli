package me.palla.renderer;

import me.palla.util.ScaledResolution;

public interface RenderManager {

    int DEFAULT_WIDTH = 800;
    int DEFAULT_HEIGHT = 800;

    ScaledResolution getScaledResolution();

    float getMouseX();

    float getMouseY();

    <T> Renderer<? super T> getRendererFor(Class<T> classType);
}
