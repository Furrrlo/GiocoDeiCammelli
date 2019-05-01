package me.palla.gui;

import java.awt.Color;
import me.palla.GiocoPalla;
import me.palla.entity.EntityManager;
import me.palla.gui.components.PauseMenuButton;

public class GameGui extends BaseGui {

    private final EntityManager entityManager;
    private final Color backgroundColor;

    public GameGui(Color backgroundColor) {
        entityManager = GiocoPalla.getInstance().getEntityManager();
        this.backgroundColor = backgroundColor;
        PauseMenuButton pauseButton = new PauseMenuButton();
        pauseButton.setX(200);
        pauseButton.setY(300);
        pauseButton.setWidth(200);
        pauseButton.setHeight(50);
        components.add(pauseButton);
        
    }

    @Override
    public void onRender() {

    }
}
