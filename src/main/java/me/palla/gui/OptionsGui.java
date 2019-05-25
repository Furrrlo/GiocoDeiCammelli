package me.palla.gui;

import me.palla.GiocoPalla;
import me.palla.gui.components.ColorSlider;
import me.palla.gui.components.PauseMenuButton;
import me.palla.value.ColorValue;
import me.palla.value.Value;

import java.awt.*;
import java.util.List;

public class OptionsGui extends PauseGui {

    private static final int BUTTON_SIZE = 50;
    private static final int NORMAL_COLOR = new Color(0xFFFFFFFF, true).getRGB();
    private static final int FOCUSED_COLOR = new Color(0xFFf4c842, true).getRGB();

    private final List<Value<?>> temp;
    private PauseMenuButton button;

    public OptionsGui() {
        temp = GiocoPalla.getInstance().getValueManager().getValues();
        button = new PauseMenuButton("Back", NORMAL_COLOR, FOCUSED_COLOR, BUTTON_SIZE, new OnClickListener());
        components.add(button);

        for (Integer i = 0; i < temp.size(); i++) {
            final Value<?> value = temp.get(i);
            if (temp.get(i).getValueType().equals(Color.class)) {
                components.add(new ColorSlider((ColorValue) value));
            }
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

        button.setHeight(50);
        button.setY(height - button.getHeight() - 10);
        button.setCenterX(width / 2);
    }

    private class OnClickListener implements Runnable {
        @Override
        public void run() {
            GiocoPalla.getInstance().displayGui(new PauseMenuGui());
        }
    }
}
