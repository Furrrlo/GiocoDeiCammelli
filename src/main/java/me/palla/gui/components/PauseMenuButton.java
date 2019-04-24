package me.palla.gui.components;

import me.palla.GiocoPalla;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PauseMenuButton extends BaseGuiComponent {

    private String content;

    private int color;
    private int focusedColor;

    private final Collection<Runnable> actionListeners;

    public PauseMenuButton(String content,
                           int color,
                           int focusedColor,
                           float size,
                           Runnable... actionListeners) {
        this.color = color;
        this.focusedColor = focusedColor;
        this.height = size;
        setContent(content);

        this.actionListeners = new ArrayList<>();
        Collections.addAll(this.actionListeners, actionListeners);
    }

    @Override
    public void onRender() {
        GiocoPalla.getInstance().pushStyle();

        // GiocoPalla.getInstance().rect(x, y, width, height);

        GiocoPalla.getInstance().textAlign(LEFT, TOP);
        GiocoPalla.getInstance().textSize(height);
        if(isHovered())
            GiocoPalla.getInstance().fill(focusedColor);
        else
            GiocoPalla.getInstance().fill(color);
        GiocoPalla.getInstance().text(content, x, y);

        GiocoPalla.getInstance().popStyle();
    }

    @Override
    public void onClick(float xPos, float yPos) {
        actionListeners.forEach(Runnable::run);
    }

    // Inherited setters

    @Override
    public void setWidth(float width) {
        throw new UnsupportedOperationException("Width is automatically calculated");
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        setContent(content);
    }

    // Action Listeners

    public void addActionListener(Runnable listener) {
        actionListeners.add(listener);
    }

    public void removeActionListener(Runnable listener) {
        actionListeners.remove(listener);
    }

    // Getters and setters

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        GiocoPalla.getInstance().pushStyle();
        GiocoPalla.getInstance().textSize(height);

        this.content = content;
        this.width = GiocoPalla.getInstance().textWidth(content);

        GiocoPalla.getInstance().popStyle();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getFocusedColor() {
        return focusedColor;
    }

    public void setFocusedColor(int focusedColor) {
        this.focusedColor = focusedColor;
    }
}
