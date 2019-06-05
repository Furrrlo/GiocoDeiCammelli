package me.palla.gui;

import me.palla.renderer.RenderContext;

public interface Gui {
    void onRender(RenderContext ctx);

    void onClick(float xPos, float yPos);

    void onResize(float newWidth, float newHeight);

    void onGuiClose();
}
