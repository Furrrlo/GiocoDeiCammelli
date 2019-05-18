package me.palla.gui;

import me.palla.GiocoPalla;
import me.palla.gui.components.ColorSlider;
import me.palla.gui.components.PauseMenuButton;
import me.palla.util.ScaledResolution;
import me.palla.value.ColorValue;
import me.palla.value.Value;

import java.awt.*;
import java.util.List;

public class OptionsGui extends PauseGui {
    private final List<Value<?>> temp;
    private PauseMenuButton button;
    
    private static final int BUTTON_SIZE = 50;
    private static final int NORMAL_COLOR = new Color(0xFF000000, true).getRGB();
    private static final int FOCUSED_COLOR = new Color(0xFFf4c842, true).getRGB();
    
    public OptionsGui(){
        temp = GiocoPalla.getInstance().getValueManager().getValues();
        // TODO: should probably write something like "Back" instead of "Options"
        button = new PauseMenuButton("Options", NORMAL_COLOR, FOCUSED_COLOR, BUTTON_SIZE,
                // TODO: change the GameGui to the PauseMenuGui. Also do you even know how this works?
                () -> GiocoPalla.getInstance().displayGui(new GameGui()));
        components.add(button);

        for (Integer i = 0; i < temp.size(); i++) {
            final Value<?> value = temp.get(i);
            if (temp.get(i).getValueType().equals(Color.class)) {        
                components.add(new ColorSlider((ColorValue) value));
            }
        }
    }
    
    public void onResize(){        
        int totHeight = 0, inizioY = 0;
        for (int i = 0; i < components.size(); i++) {
            totHeight += components.get(i).getHeight();
        }
        
        inizioY = (int) (height / 2 - totHeight / 2);
        for (int i = 0; i < components.size(); i++) {
            int temp = 0;

            // TODO: Fix this
            //       Instead of checking if the object has a specific class, just check if the object is
            //       our attribute this.button

            for (int j = 0; j < i; j++) {
                if(!components.get(j).equals(PauseMenuButton.class))
                    temp += components.get(j).getHeight();
            }

            if(!components.get(i).equals(PauseMenuButton.class)){
                components.get(i).setY(inizioY + temp);
                components.get(i).setCenterX(width / 2);
            }

            if(components.get(i).equals(PauseMenuButton.class)) {
                // TODO: You don't have to do this inside the for loop, just do it outside
                final ScaledResolution res = GiocoPalla.getInstance().getScaledResolution();
                button.setHeight(50);
                button.setY(10); // TODO: should probably put this at the bottom, like screenHeight - button height - 10
                                 //       To get the screenHeight just do res.getScaledHeight()
                button.setWidth(250); // TODO: Don't set its width, it's automatically calculated cause I was lazy
                button.setX(button.getWidth() + 10); // TODO: center this coordinate
            }
        }
    }
}