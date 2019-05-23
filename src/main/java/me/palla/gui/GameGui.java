package me.palla.gui;

import me.palla.GiocoPalla;
import me.palla.entity.EntityManager;
import me.palla.gui.components.PauseButton;
import me.palla.util.ScaledResolution;
import me.palla.value.ColorValue;

public class GameGui extends BaseGui {

    private static ColorValue backgroundColor;
    
    private final EntityManager entityManager;
    private PauseButton pauseButton;

    public GameGui() {
        if(backgroundColor == null)
            backgroundColor = new ColorValue("Colore sfondo", 255,255,255);

        entityManager = GiocoPalla.getInstance().getEntityManager();
        
        pauseButton = new PauseButton(new PauseButtonClickPerformed());       
        components.add(pauseButton);
    }
    
    public class PauseButtonClickPerformed implements Runnable {
        @Override 
        public void run() {
            GiocoPalla.getInstance().displayGui(new PauseMenuGui());
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
    public void onRender() {
        final ScaledResolution res = GiocoPalla.getInstance().getScaledResolution();
        GiocoPalla.getInstance().fill(backgroundColor.getRGB());
        GiocoPalla.getInstance().rect(0, 0, res.getScaledWidth(), res.getScaledHeight());

        entityManager.render();
        super.onRender();
    }
    
}
