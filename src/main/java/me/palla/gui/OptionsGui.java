package me.palla.gui;

import me.palla.Game;
import me.palla.gui.components.ColorSlider;
import me.palla.gui.components.PauseMenuButton;
import me.palla.value.ColorValue;

import java.awt.*;

public class OptionsGui extends PauseGui {

    private static final int BUTTON_SIZE = 50;
    private static final Color NORMAL_COLOR = new Color(0xFFFFFFFF, true);
    private static final Color FOCUSED_COLOR = new Color(0xFFf4c842, true);

    private PauseMenuButton button;

    public OptionsGui() {
        button = new PauseMenuButton("Back", NORMAL_COLOR, FOCUSED_COLOR, BUTTON_SIZE, new OnClickListener());
        addComponent(button);
    }

    private class OnClickListener implements Runnable {
        @Override
        public void run() {
            game.displayGui(new PauseMenuGui());
        }
    }

    public void onResize() {
        int totHeight = 0, inizioY = 0;
        for (int i = 0; i < components.size(); i++) {
            totHeight += components.get(i).getHeight();
        }

        inizioY = (int) (height / 2 - totHeight / 2);
        for (int i = 0; i < components.size(); i++) {
            int temp = 0;

            for (int j = 0; j < i; j++) {
                if (!components.get(j).equals(this.button)) {
                    temp += components.get(j).getHeight();
                }
            }

            if (!components.get(i).equals(this.button)) {
                components.get(i).setY(inizioY + temp);
                components.get(i).setCenterX(width / 2);
            }
        }

        button.setY(height - button.getHeight() - 10);
        button.setCenterX(width / 2);
    }

    @Override
    public void setGame(Game game) {
        super.setGame(game);

        components.clear();
        game.valueManager().getValues()
                .forEach(value -> {
                    if (value.getValueType().equals(Color.class))
                        addComponent(new ColorSlider((ColorValue) value));
                });
        addComponent(button);
    }
}