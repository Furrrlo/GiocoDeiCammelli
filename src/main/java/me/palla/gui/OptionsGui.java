package me.palla.gui;
import me.palla.GiocoPalla;
import me.palla.gui.components.ColorSlider;
import me.palla.value.ColorValue;
import me.palla.value.Value;
import me.palla.gui.components.PauseMenuButton;
import java.awt.*;
import java.util.List;
import me.palla.util.ScaledResolution;

public class OptionsGui extends BaseGui {
    private final List<Value<?>> temp;
    private PauseMenuButton button;
    
    private static final int BUTTON_SIZE = 50;
    private static final int BUTTON_MARGIN = 10;
    private static final int NORMAL_COLOR = new Color(0xFF000000, true).getRGB();
    private static final int FOCUSED_COLOR = new Color(0xFFf4c842, true).getRGB();
    
    public OptionsGui(){
        temp = GiocoPalla.getInstance().getValueManager().getValues();
        button = new PauseMenuButton("Options", NORMAL_COLOR, FOCUSED_COLOR, BUTTON_SIZE,
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
            for (int j = 0; j < i; j++) {
                if(!components.get(j).equals(PauseMenuButton.class))
                    temp += components.get(j).getHeight();
            }
            
            if(!components.get(i).equals(PauseMenuButton.class)){
                components.get(i).setY(inizioY + temp);
                components.get(i).setCenterX(width / 2);
            }
            
            if(components.get(i).equals(PauseMenuButton.class)){
                final ScaledResolution res = GiocoPalla.getInstance().getScaledResolution();
                button.setHeight(50);
                button.setWidth(250);
                button.setX(button.getWidth() + 10);
                button.setY(10);
            }
                
        }
    }
}