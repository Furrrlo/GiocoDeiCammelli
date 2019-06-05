package me.palla.norender;

import me.palla.renderer.RenderContext;
import me.palla.renderer.Renderer;

public class NoRenderer implements Renderer<Object> {

    private static final NoRenderer INSTANCE = new NoRenderer();

    private NoRenderer() {
    }

    public static NoRenderer instance() {
        return INSTANCE;
    }

    @Override
    public void onRender(RenderContext ctx, Object toRender) {
    }
}
