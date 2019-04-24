package me.palla.gui.components;

import processing.core.PConstants;

public interface GuiComponent extends PConstants {
    void onRender();
    boolean intersects(float xPos, float yPos);
    void onClick(float xPos, float yPos);

    float getX();
    void setX(float x);

    default void setCenterX(float x) {
        setX(x - getWidth() / 2);
    }

    float getY();
    void setY(float y);

    default void setCenterY(float y) {
        setY(y - getHeight() / 2);
    }

    float getWidth();
    void setWidth(float width);

    float getHeight();
    void setHeight(float height);
}
