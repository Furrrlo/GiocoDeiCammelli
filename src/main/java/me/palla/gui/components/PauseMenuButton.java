package me.palla.gui.components;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @brief Tasti nel menù di pausa. Classe per gestire (render, colori e click) i bottoni all'interno del menù di
 *         pausa
 * @author Francesco Ferlin
 */
public class PauseMenuButton extends BaseGuiComponent<PauseMenuButton> {

    /** Parola all'interno del bottone */
    private String content;

    /** Colore normale */
    private Color color;
    /** Colore con mouseOver */
    private Color focusedColor;

    private final Collection<Runnable> actionListeners;

    /**
     * @brief Costruttore con parametri Inizializza le variabili con i valori passati come parametri
     *
     * @param actionListeners Listener per i bottoni
     * @param color           Colore del bottone
     * @param content         La stringa all'interno del bottone
     * @param focusedColor    Colore del bottone quando il mouse si trova sopra
     * @param size            Dimensione del bottone in altezza
     */
    public PauseMenuButton(String content,
                           Color color,
                           Color focusedColor,
                           float size,
                           Runnable... actionListeners) {
        this.color = color;
        this.focusedColor = focusedColor;
        this.height = size;
        this.content = content;

        this.actionListeners = new ArrayList<>();
        Collections.addAll(this.actionListeners, actionListeners);
    }

    @Override
    public void onClick(float xPos, float yPos) {
        actionListeners.forEach(Runnable::run);
    }

    // Inherited setters

    @Override
    public void setWidth(float width) {
        throw new UnsupportedOperationException("Width is automatically calculated");
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        setContent(content);
    }

    // Action Listeners

    public void addActionListener(Runnable listener) {
        actionListeners.add(listener);
    }

    public void removeActionListener(Runnable listener) {
        actionListeners.remove(listener);
    }

    // Getters and setters

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        onResize();
    }

    public Color getColor() {
        return color;
    }

    public Color getFocusedColor() {
        return focusedColor;
    }

    public void setTextWidth(float textWidth) {
        this.width = textWidth;
    }
}
