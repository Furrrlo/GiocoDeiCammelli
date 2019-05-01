package me.palla.gui;

import me.palla.GiocoPalla;
import me.palla.entity.EntityManager;

public class GameGui extends BaseGui {

    private final EntityManager entityManager;
    private final ColorValue backgroundColor;
    private PauseMenuButton pauseButton;

    public GameGui(ColorValue backgroundColor, PauseMenuButton pauseButton) {
        entityManager = GiocoPalla.getInstance().getEntityManager();
        this.backgroundColor = backGroundColor;
        this.pauseButton = pauseButton;
    }

    @Override
    public void onRender() {
        pauseButton.onRender();
    }
}
