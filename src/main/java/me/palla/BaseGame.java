package me.palla;

import me.palla.entity.EntityManager;
import me.palla.gui.GameGui;
import me.palla.gui.Gui;
import me.palla.input.InputManager;
import me.palla.renderer.RenderManager;
import me.palla.value.ColorValue;
import me.palla.value.ValueManager;

public class BaseGame<T extends RenderManager> implements Game {

    protected final ValueManager valueManager;
    protected final InputManager inputManager;
    protected final EntityManager entityManager;
    protected final T renderManager;

    protected Gui currentGui;
    protected boolean isPaused;

    public BaseGame(T renderManager) {
        this(
                new ValueManager(),
                new InputManager(),
                new EntityManager(),
                renderManager,
                new GameGui()
        );

        entityManager().spawnEntities(7, 6);
    }

    protected BaseGame(ValueManager valueManager,
                       InputManager inputManager,
                       EntityManager entityManager,
                       T renderManager,
                       Gui startGui) {

        this.valueManager = valueManager;
        this.inputManager = inputManager;
        this.entityManager = entityManager;
        this.renderManager = renderManager;

        entityManager.setGame(this);
        if(renderManager instanceof GameReferencer)
            ((GameReferencer) renderManager).setGame(this);

        valueManager.registerValue(new ColorValue("Colore sfondo", 255, 255, 255));
        displayGui(startGui);
    }

    @Override
    public void displayGui(Gui newGui) {

        if(newGui instanceof GameReferencer)
            ((GameReferencer)newGui).setGame(this);

        if (currentGui != null)
            currentGui.onGuiClose();

        isPaused = !(newGui instanceof GameGui);
        currentGui = newGui;
        currentGui.onResize(
                renderManager.getScaledResolution().getScaledWidth(),
                renderManager.getScaledResolution().getScaledHeight()
        );
    }

    @Override
    public void shutdown() {
        System.exit(0);
    }

    // Getters

    @Override
    public ValueManager valueManager() {
        return valueManager;
    }

    @Override
    public InputManager inputManager() {
        return inputManager;
    }

    @Override
    public EntityManager entityManager() {
        return entityManager;
    }

    public RenderManager renderManager() {
        return renderManager;
    }

    @Override
    public Gui getCurrentGui() {
        return currentGui;
    }

    @Override
    public boolean isPaused() {
        return isPaused;
    }
}
