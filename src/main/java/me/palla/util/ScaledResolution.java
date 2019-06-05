package me.palla.util;

/**
 * @brief Gestisce le dimensioni della schermata di gioco Questa classe ha il compito di gestire le dimensioni
 *         della schermata di gioco: nel caso in cui la finestra venga ridimensionata, i componenti vengono
 *         ridimensionati e spostati a loro volta
 * @author Francesco Ferlin
 */

public class ScaledResolution {

    /** Lunghezza schermo */
    private final float width;
    /** Altezza schermo */
    private final float height;

    /** Lunghezza schermo ridimensionato */
    private final float scaledWidth;
    /** Altezza schermo ridimensionato */
    private final float scaledHeight;

    /** Grado di ridimensionamento in larghezza */
    private final float widthScaleFactor;
    /** Grado di ridimensionamento in altezza */
    private final float heightScaleFactor;

    /**
     * @brief Costruttore con parametri Inizializza le variabili secondi i parametri e calcola gradi di
     *         ridimensionamento
     *
     * @param width        Lunghezza schermo
     * @param height       Altezza schermo
     * @param scaledHeight Altezza schermo ridimensionato
     * @param scaledWidth  Lunghezza schermo ridimensionato
     */
    @SuppressWarnings("unused")
    public ScaledResolution(float width,
                            float height,
                            float scaledWidth,
                            float scaledHeight) {
        this.width = width;
        this.height = height;
        this.scaledWidth = scaledWidth;
        this.scaledHeight = scaledHeight;

        this.heightScaleFactor = this.widthScaleFactor = Math.min(height / scaledHeight, width / scaledWidth);
    }

    /**
     * @brief Costruttore con parametri Inizializza le variabili secondi i parametri e calcola gradi di
     *         ridimensionamento
     *
     * @param width       Lunghezza schermo
     * @param height      Altezza schermo
     * @param scaleFactor Scala di ridimensionamento
     */
    public ScaledResolution(float width,
                            float height,
                            float scaleFactor) {
        this.width = width;
        this.height = height;
        this.widthScaleFactor = scaleFactor;
        this.heightScaleFactor = scaleFactor;

        this.scaledWidth = width * scaleFactor;
        this.scaledHeight = height * scaleFactor;
    }

    /**
     * @brief Calcolo posizione sulla X con valore da scalare
     *
     * @param toScale Il valore da adattare allo schermo
     * @return Ritorna la posizione sulla X secondo la dimensione della schermo
     */
    public float scaleX(float toScale) {
        return (toScale - getWidthDifference() / 2) / getWidthScaleFactor();
    }

    /**
     * @brief Calcolo posizione sulla Y con valore da scalare
     *
     * @param toScale Il valore da adattare allo schermo
     * @return Ritorna la posizione sulla Y secondo la dimensione della schermo
     */
    public float scaleY(float toScale) {
        return (toScale - getHeightDifference() / 2) / getHeightScaleFactor();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getScaledWidth() {
        return scaledWidth;
    }

    public float getScaledHeight() {
        return scaledHeight;
    }

    public float getWidthScaleFactor() {
        return widthScaleFactor;
    }

    public float getHeightScaleFactor() {
        return heightScaleFactor;
    }

    public float getWidthDifference() {
        return width - scaledWidth * widthScaleFactor;
    }

    public float getHeightDifference() {
        return height - scaledHeight * heightScaleFactor;
    }
}
