package me.palla.norender;

import me.palla.gui.Gui;
import me.palla.renderer.RenderContext;

public class NoGui implements Gui {

    private static final NoGui INSTANCE = new NoGui();

    private NoGui() {
    }

    public static NoGui instance() {
        return INSTANCE;
    }

    @Override
    public void onRender(RenderContext ctx) {
    }

    @Override
    public void onClick(float xPos, float yPos) {
    }

    @Override
    public void onResize(float newWidth, float newHeight) {
    }

    @Override
    public void onGuiClose() {
    }
}
