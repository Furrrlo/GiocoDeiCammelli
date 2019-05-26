package me.palla.input;

/**
 * @brief Classe per input da giroscopio. Prende i valori in input da InputData
 * @author Davide Borzì
 */
public class InputGyroscope implements InputData {

    /** Asse X */
    private float xAxis;
    /** Asse Y */
    private float yAxis;

    /**
     * @brief Costruttore con parametri. Inizializza le variabili secondo i valori passati come parametri
     *
     * @param xAxis il valore sull'asse delle ascisse
     * @param yAxis il valore sull'asse delle ordinate
     */
    public InputGyroscope(float xAxis, float yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    /**
     * @brief Costruttore senza parametri Inizializza le variabili a 0
     */
    public InputGyroscope() {
        xAxis = 0;
        yAxis = 0;
    }

    /**
     * @brief Ritorna il valore delle X
     *
     * @return valore dell'asse delle ascisse
     */
    // Get e set
    public float getAxisX() {
        return xAxis;
    }

    /**
     * @brief Imposta il valore dell'asse X
     *
     * @param xAxis il nuovo valore delle X
     */
    public void setAxisX(float xAxis) {
        this.xAxis = xAxis;
    }

    /**
     * @brief Ritorna il valore delle Y
     *
     * @return valore dell'asse delle ordinate
     */
    public float getAxisY() {
        return yAxis;
    }

    /**
     * @brief Imposta il valore dell'asse X
     *
     * @param yAxis il nuovo valore delle X
     */
    public void setAxisY(float yAxis) {
        this.yAxis = yAxis;
    }
}
