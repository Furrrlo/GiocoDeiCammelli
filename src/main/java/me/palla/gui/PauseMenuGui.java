package me.palla.gui;

import me.palla.GiocoPalla;
import me.palla.gui.components.PauseMenuButton;

import java.awt.*;

public class PauseMenuGui extends PauseGui {

    private static final int BUTTON_SIZE = 50;
    private static final int BUTTON_MARGIN = 10;

    private static final int NORMAL_COLOR = new Color(0xFF000000, true).getRGB();
    private static final int FOCUSED_COLOR = new Color(0xFFf4c842, true).getRGB();

    public PauseMenuGui() {
        components.add(new PauseMenuButton("Resume", NORMAL_COLOR, FOCUSED_COLOR, BUTTON_SIZE,
                () -> GiocoPalla.getInstance().displayGui(new GameGui())));
        components.add(new PauseMenuButton("Options", NORMAL_COLOR, FOCUSED_COLOR, BUTTON_SIZE,
                () -> GiocoPalla.getInstance().displayGui(new OptionsGui())));
        components.add(new PauseMenuButton("Exit", NORMAL_COLOR, FOCUSED_COLOR, BUTTON_SIZE,
                () -> GiocoPalla.getInstance().exit()));
    }

    @Override
    public void onResize() {

        final int buttonsN = (int) components.stream()
                .filter(PauseMenuButton.class::isInstance)
                .map(PauseMenuButton.class::cast)
                .count();
        final int totalHeight = buttonsN * (BUTTON_SIZE + BUTTON_MARGIN) - BUTTON_MARGIN;

        final float x = width / 2;
        final float[] y = new float[] { (height - totalHeight) / 2 };

        components.stream()
                .filter(PauseMenuButton.class::isInstance)
                .map(PauseMenuButton.class::cast)
                .forEach(c -> {
                    c.setCenterX(x);
                    c.setCenterY(y[0]);

                    y[0] += c.getHeight() + BUTTON_MARGIN;
                });
    }
}
