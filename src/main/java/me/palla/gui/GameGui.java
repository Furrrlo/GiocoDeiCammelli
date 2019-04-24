package me.palla.gui;

import me.palla.GiocoPalla;
import me.palla.entity.EntityManager;

public class GameGui extends BaseGui {

    private final EntityManager entityManager;

    public GameGui() {
        entityManager = GiocoPalla.getInstance().getEntityManager();
    }

    @Override
    public void onRender() {

    }
}
