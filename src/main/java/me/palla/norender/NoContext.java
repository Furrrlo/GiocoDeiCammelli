package me.palla.norender;

import me.palla.renderer.RenderContext;

public class NoContext implements RenderContext {

    private static final NoContext INSTANCE = new NoContext();

    private NoContext() {
    }

    public static NoContext instance() {
        return INSTANCE;
    }
}
