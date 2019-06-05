package me.palla.gui;

import me.palla.Game;
import me.palla.gui.components.PauseButton;
import me.palla.renderer.RenderContext;
import me.palla.value.ColorValue;

import java.awt.*;

public class GameGui extends BaseGui {

    private PauseButton pauseButton;
    private ColorValue backgroundValue;

    public GameGui() {
        pauseButton = new PauseButton(new PauseButtonClickPerformed());
        addComponent(pauseButton);
    }

    public class PauseButtonClickPerformed implements Runnable {
        @Override
        public void run() {
            game.displayGui(new PauseMenuGui());
        }
    }

    @Override
    public void onResize() {
        pauseButton.setWidth(60);
        pauseButton.setHeight(70);
        pauseButton.setX(width - pauseButton.getWidth() - 10);
        pauseButton.setY(10);
    }

    @Override
    public void onRender(RenderContext ctx) {
        super.onRender(ctx);
        game.entityManager().render(ctx);
    }

    @Override
    public void setGame(Game game) {
        super.setGame(game);
        backgroundValue = game.valueManager().getValueByName("Colore sfondo");
    }

    @Override
    public Color getBackgroundColor() {
        return backgroundValue.get();
    }
}
