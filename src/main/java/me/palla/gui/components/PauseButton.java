package me.palla.gui.components;

import me.palla.renderer.RenderContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @brief Bottone di pausa. Classe per renderizzare un tasto di pausa per fermare l'esecuzione del gioco.
 * @author Christian Ferrareis
 */
public class PauseButton extends BaseGuiComponent<PauseButton> {

    /** Colore interno */
    private Color rectColor;
    /** Colore del bordo */
    private Color strokeColor;
    /** Colore interno con mouseOver */
    private Color focusedColor;
    /** Colore del bordo con mouseOver */
    private Color focusedStrokeColor;

    /** Listener per click del bottone */
    private final Collection<Runnable> actionListeners;

    /**
     * @brief Costruttore con parametro Prende un actionListener e inizializza le variabili e i colori
     *
     * @param actionListeners Elemento per click bottone
     */
    public PauseButton(Runnable... actionListeners) {
        this.actionListeners = new ArrayList<>();
        Collections.addAll(this.actionListeners, actionListeners);

        rectColor = new Color(0xFF42a4f4, true);
        strokeColor = new Color(0xFFD9D9D9, true);
        focusedColor = new Color(0xffffaa23, true);
        focusedStrokeColor = new Color(0xFF000000, true);
    }

    /**
     * @brief Renderizzazione Metodo che disegna il bottone di pausa e lo toglie dopo averlo cliccato e cambia i
     *         colori quando ci si passa sopra con il mouse
     */
    @Override
    public void onRender(RenderContext ctx) {
        if (game.isPaused())
            return;
        super.onRender(ctx);
    }

    @Override
    public void onClick(float xPos, float yPos) {
        actionListeners.forEach(Runnable::run);
    }

    // Action Listeners

    public void addActionListener(Runnable listener) {
        actionListeners.add(listener);
    }

    public void removeActionListener(Runnable listener) {
        actionListeners.remove(listener);
    }

    // Getters

    public Color getRectColor() {
        return rectColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public Color getFocusedColor() {
        return focusedColor;
    }

    public Color getFocusedStrokeColor() {
        return focusedStrokeColor;
    }
}
