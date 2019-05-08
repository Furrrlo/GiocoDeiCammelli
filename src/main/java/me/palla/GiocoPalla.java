package me.palla;

import me.palla.entity.EntityManager;
import me.palla.gui.GameGui;
import me.palla.gui.Gui;
import me.palla.value.ValueManager;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class GiocoPalla extends PApplet {

    private static GiocoPalla INSTANCE;

    private ValueManager valueManager;
    private EntityManager entityManager;
    private Gui currentGui;

    private float oldWidth;
    private float oldHeight;
    
    private boolean isPaused;
    
    private int sizeX=800;
    private int sizeY=800;

    public GiocoPalla() {}

    public static GiocoPalla getInstance() {
        return INSTANCE;
    }

    @Override
    public void settings() {
        size(sizeX, sizeY);
    }

    @Override
    public void setup() {
        INSTANCE = this;
        surface.setResizable(true);

        valueManager = new ValueManager();
        entityManager = new EntityManager(7,7);
        displayGui(new GameGui());
    }

    @Override
    public void draw() {

        if(width != oldWidth || height != oldHeight)
            currentGui.onResize();
        oldWidth = width;
        oldHeight = height;

        clear();
        background(33, 33, 33);
        currentGui.onRender();
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        currentGui.onClick(event.getX(), event.getY());
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

        currentGui = newGui;
        currentGui.onResize();
    }

    // Getters

    public ValueManager getValueManager() {
        return valueManager;
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

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
    
    

}
