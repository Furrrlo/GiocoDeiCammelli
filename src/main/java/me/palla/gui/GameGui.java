package me.palla.gui;

import me.palla.GiocoPalla;
import me.palla.entity.EntityManager;

public class GameGui extends BaseGui {

    private final EntityManager entityManager;
    private final ColorValue backgroundColor;

    public GameGui(ColorValue backgroundColor) {
        entityManager = GiocoPalla.getInstance().getEntityManager();
        this.backgroundColor = backGroundColor;
        pauseMenuButton pauseButton = new pauseMenuButton();
        pauseButton.setX(200);
        pauseButton.setY(300);
        components.add(pauseButton);
        
    }

    @Override
    public void onRender() {

    }
}
