package me.palla.gui;

import me.palla.Game;
import me.palla.GameReferencer;
import me.palla.gui.components.GuiComponent;
import me.palla.norender.NoRenderGame;
import me.palla.renderer.RenderContext;
import me.palla.renderer.Renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseGui implements Gui, GameReferencer {

    protected Game game;
    protected final List<GuiComponent> components;

    protected float width;
    protected float height;

    protected Color backgroundColor;

    protected Renderer<BaseGui> renderer;

    protected BaseGui() {
        components = new ArrayList<>();
        backgroundColor = new Color(0, 0, 0, 0);
        setGame(NoRenderGame.instance());
    }

    @Override
    public void onRender(RenderContext ctx) {
        renderer.onRender(ctx, this);
        components.forEach(c -> c.onRender(ctx));
    }

    @Override
    public void onClick(float xPos, float yPos) {

        GuiComponent clicked = null;
        for (int i = components.size() - 1; i >= 0; i--) {
            final GuiComponent component = components.get(i);

            if (component.intersects(xPos, yPos)) {
                component.onClick(xPos, yPos);
                clicked = component;
                break;
            }
        }
        // Bring the clicked component up so it gets
        // rendered before others
        if (clicked != null) {
            components.remove(clicked);
            components.add(clicked);
        }
    }

    @Override
    public void onGuiClose() {
    }

    @Override
    public void onResize(float width, float height) {
        this.width = width;
        this.height = height;
        onResize();
    }

    public void onResize() {
    }

    protected void addComponent(GuiComponent component) {

        if(component == null)
            return;

        if(component instanceof GameReferencer)
            ((GameReferencer)component).setGame(game);
        this.components.add(component);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setGame(Game game) {
        this.game = game;
        renderer = (Renderer<BaseGui>) game.renderManager().getRendererFor(BaseGui.class);
        components.stream()
                .filter(GameReferencer.class::isInstance)
                .map(GameReferencer.class::cast)
                .forEach(o -> o.setGame(game));
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }
}
