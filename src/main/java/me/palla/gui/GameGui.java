package me.palla.gui;

import me.palla.GiocoPalla;
import me.palla.entity.EntityManager;
import me.palla.gui.components.PauseButton;
import me.palla.util.ScaledResolution;
import me.palla.value.ColorValue;

public class GameGui extends BaseGui {

    private final EntityManager entityManager;
    private static ColorValue backgroundColor;

    public GameGui() {
        if(backgroundColor == null)
            backgroundColor = new ColorValue("Colore sfondo", 255,255,255);

        entityManager = GiocoPalla.getInstance().getEntityManager();
        PauseButton pauseButton = new PauseButton(new PauseButtonClickPerformed());        
        final ScaledResolution res = GiocoPalla.getInstance().getScaledResolution();
        pauseButton.setWidth(60);
        pauseButton.setHeight(70);
        pauseButton.setX(res.getWidth() - pauseButton.getWidth() - 10);
        pauseButton.setY(10);         
        components.add(pauseButton);        
        //classe che estende Runnable e dentro l'override del run dire di cambiare la 
        //schermata con displayGui passando come parametro la nuova schermata (pausa)
    }
    public class PauseButtonClickPerformed implements Runnable
    {
        @Override 
        public void run()
        {
            GiocoPalla.getInstance().displayGui(new PauseMenuGui());
        }
    }

    @Override
    public void onRender() {
        final ScaledResolution res = GiocoPalla.getInstance().getScaledResolution();
        GiocoPalla.getInstance().rect(0, 0, res.getScaledWidth(), res.getScaledHeight(), backgroundColor.getRGB());

        entityManager.render();
        super.onRender();
    }
    
}
