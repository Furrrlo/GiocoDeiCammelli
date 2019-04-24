package me.palla.gui;

public interface Gui {
    void onRender();
    void onClick(float xPos, float yPos);
    void onResize();
    void onGuiClose();
}
