package me.palla.gui;

import me.palla.Game;
import me.palla.norender.NoRenderGame;
import me.palla.renderer.RenderContext;

import java.awt.*;

public class PauseGui extends BaseGui {

    private static final Color OVERLAY_COLOR = new Color(22, 22, 22, 175);

    private final GameGui backgroundGui;

    protected PauseGui() {
        backgroundGui = new GameGui();
        backgroundColor = OVERLAY_COLOR;
        setGame(NoRenderGame.instance());
    }

    @Override
    public void onRender(RenderContext ctx) {
        backgroundGui.onRender(ctx);
        super.onRender(ctx);
    }

    @Override
    public void onResize(float width, float height) {
        backgroundGui.onResize(width, height);
        super.onResize(width, height);
    }

    @Override
    public void setGame(Game game) {
        super.setGame(game);
        if(backgroundGui != null)
            backgroundGui.setGame(game);
    }
}
