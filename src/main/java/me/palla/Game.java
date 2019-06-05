package me.palla;

import me.palla.entity.EntityManager;
import me.palla.gui.Gui;
import me.palla.input.InputManager;
import me.palla.renderer.RenderManager;
import me.palla.value.ValueManager;

public interface Game {

    void displayGui(Gui newGui);

    void shutdown();

    // Getters

    ValueManager valueManager();

    InputManager inputManager();

    EntityManager entityManager();

    RenderManager renderManager();

    Gui getCurrentGui();

    boolean isPaused();
}
