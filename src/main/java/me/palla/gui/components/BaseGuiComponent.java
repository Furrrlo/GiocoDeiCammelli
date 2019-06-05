package me.palla.gui.components;

import me.palla.Game;
import me.palla.GameReferencer;
import me.palla.renderer.RenderContext;
import me.palla.renderer.RenderManager;
import me.palla.renderer.Renderer;
import me.palla.util.ScaledResolution;

public abstract class BaseGuiComponent<T extends BaseGuiComponent> implements GuiComponent, GameReferencer {

    protected Game game;
    protected Renderer<? super T> renderer;

    protected float x;
    protected float y;
    protected float width;
    protected float height;

    @Override
    @SuppressWarnings("unchecked")
    public void setGame(Game game) {
        this.game = game;
        this.renderer = (Renderer<? super T>) game.renderManager().getRendererFor(getClass());
        onResize();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onRender(RenderContext ctx) {
        renderer.onRender(ctx, (T) this);
    }

    @Override
    public boolean intersects(float xPos, float yPos) {
        return xPos >= x && xPos <= x + width && yPos >= y && yPos <= y + height;
    }

    public boolean isHovered() {
        final RenderManager renderManager = game.renderManager();
        final ScaledResolution res = renderManager.getScaledResolution();
        return intersects(res.scaleX(renderManager.getMouseX()), res.scaleY(renderManager.getMouseY()));
    }

    @SuppressWarnings("unchecked")
    protected void onResize() {
        renderer.onResize((T) this);
    }

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
