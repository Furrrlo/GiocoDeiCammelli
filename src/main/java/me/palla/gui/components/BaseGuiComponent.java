package me.palla.gui.components;

import me.palla.GiocoPalla;
import me.palla.util.ScaledResolution;

public abstract class BaseGuiComponent implements GuiComponent {

    protected float x;
    protected float y;
    protected float width;
    protected float height;

    @Override
    public boolean intersects(float xPos, float yPos) {
        return xPos >= x && xPos <= x + width && yPos >= y && yPos <= y + height;
    }

    protected boolean isHovered() {
        final GiocoPalla inst = GiocoPalla.getInstance();
        final ScaledResolution res = inst.getScaledResolution();
        return intersects(res.scaleX(inst.mouseX), res.scaleY(inst.mouseY));
    }

    protected void onResize() {}

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
        onResize();
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
        onResize();
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
        onResize();
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
        onResize();
    }
}
