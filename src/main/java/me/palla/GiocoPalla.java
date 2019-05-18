package me.palla;

import me.palla.entity.EntityManager;
import me.palla.gui.GameGui;
import me.palla.gui.Gui;
import me.palla.input.InputManager;
import me.palla.util.ScaledResolution;
import me.palla.value.ValueManager;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class GiocoPalla extends PApplet {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 800;

    private static GiocoPalla INSTANCE;

    private ValueManager valueManager;
    private InputManager inputManager;
    private EntityManager entityManager;
    private Gui currentGui;

    private ScaledResolution scaledResolution;
    private float oldWidth;
    private float oldHeight;
    
    private boolean isPaused;

    public GiocoPalla() {}

    public static GiocoPalla getInstance() {
        return INSTANCE;
    }

    @Override
    public void settings() {
        size(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        scaledResolution = new ScaledResolution(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    public void setup() {
        INSTANCE = this;
        surface.setResizable(true);

        valueManager = new ValueManager();
        inputManager = new InputManager();
        entityManager = new EntityManager(7, 6);
        displayGui(new GameGui());
    }

    @Override
    public void draw() {

        if(width != oldWidth || height != oldHeight) {
            scaledResolution = new ScaledResolution(width, height, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            currentGui.onResize(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
        }
        oldWidth = width;
        oldHeight = height;

        clear();
        background(33, 33, 33);
        scaledResolution.setupScaling(true);
        currentGui.onRender();
        scaledResolution.setupScaling(false);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        currentGui.onClick(scaledResolution.scaleX(event.getX()), scaledResolution.scaleY(event.getY()));
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if(event.getKeyCode() == ESC) {
            key = 0;
            keyCode = 0;
        }
    }

    public void displayGui(Gui newGui) {
        if(currentGui != null)
            currentGui.onGuiClose();

        isPaused = !(newGui instanceof GameGui);
        currentGui = newGui;
        currentGui.onResize(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
    }

    // Getters

    public ValueManager getValueManager() {
        return valueManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Gui getCurrentGui() {
        return currentGui;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }
}
