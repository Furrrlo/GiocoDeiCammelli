package me.palla.gui;

import me.palla.GiocoPalla;
import me.palla.entity.EntityManager;
import me.palla.gui.components.PauseButton;
import me.palla.value.ColorValue;

public class GameGui extends BaseGui {

    private final EntityManager entityManager;
    private final ColorValue backgroundColor;

    public GameGui() {
        entityManager = GiocoPalla.getInstance().getEntityManager();
        this.backgroundColor = new ColorValue("Colore sfondo", 255,255,255);        
        PauseButton pauseButton = new PauseButton();
        pauseButton.setX(200);
        pauseButton.setY(300);
        pauseButton.setWidth(200);
        pauseButton.setHeight(50);
        components.add(pauseButton);        
        //classe che estende Runnable e dentro l'override del run dire di cambiare la 
        //schermata con displayGui passando come parametro la nuova schermata (pausa)
    }

    @Override
    public void onRender() {
        entityManager.render();
        super.onRender();
    }
}
