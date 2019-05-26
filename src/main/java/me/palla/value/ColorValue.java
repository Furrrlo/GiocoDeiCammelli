package me.palla.value;

import java.awt.*;

/**
 * @brief Gestore colori Classe che estende Value con un tipo Color, così da gestire in modo più efficiente i
 *         colori
 * @author Francesco Ferlin
 */
public class ColorValue extends Value<Color> {

    /** Booleana per creare sequenza di colori RGB */
    private boolean isRainbow;

    /**
     * @brief Costruttore con parametri Inizializza le variabili con i parametri
     *
     * @param defaultValue Il valore in esadecinale del colore
     * @param name         Il nome da attribuire al colore
     */
    public ColorValue(String name, Color defaultValue) {
        super(name, defaultValue);
        isRainbow = false;
    }

    /**
     * @brief Costruttore con parametri Inizializza le variabili con i parametri
     *
     * @param name Il nome da attribuire al colore
     * @param r    Valore della quantità di rosso
     * @param g    Valore della quantità di verde
     * @param b    Valore della quantità di blu
     */
    public ColorValue(String name, int r, int g, int b) {
        this(name, new Color(r, g, b));
    }

    @Override
    public Color get() {
        if (isRainbow)
            return getRainbowColor();
        return super.get();
    }

    public int getRGB() {
        return get().getRGB();
    }

    public boolean isRainbow() {
        return isRainbow;
    }

    public void setRainbow(boolean rainbow) {
        isRainbow = rainbow;
    }

    public static Color getRainbowColor() {
        return Color.getHSBColor(System.nanoTime() / 10000000000f, 0.8f, 1);
    }
}
