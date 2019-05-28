package me.palla.util;

/**
 * @brief Libreria per calcolare velocemente funzioni goniometriche (seno, coseno e conversione da radianti a
 *         gradi).
 * @author Francesco Ferlin
 */
public class FastMath {

    /**
     * Numero di valori del seno calcolati compresi in un grado.
     *
     * Maggiore il valore, più alta sarà la precisione con la quale il seno viene calcolato
     * e minore sarà l'arrotondamento
     */
    private final static int PRECISION_PER_DEGREE = 10; // Should be high enough
    
    /** Valore del pigreco in float */
    private final static float PI = (float) Math.PI;
    /** Vettore usato per tenere il valore dei seni precalcolato */
    private final static float[] SIN_TABLE = new float[360 * PRECISION_PER_DEGREE];

    static {
        for (int i = 0; i < SIN_TABLE.length; i++) {
            final float actualDegrees = (float) i / PRECISION_PER_DEGREE;
            final float radians = actualDegrees * PI / 180F;
            SIN_TABLE[i] = (float) Math.sin(radians);
        }
    }

    /** @brief Costruttore vuoto, senza parametri e privato perchè la classe ha solo metodi statici */
    private FastMath() {
    }

    /**
     * @brief Cerca il valore del seno per i gradi dati
     *
     * @param degrees gradi
     * @return seno
     */
    private static float lookup(float degrees) {
        degrees = degrees % 360;
        if (degrees < 0)
            return -SIN_TABLE[(int) (-degrees * PRECISION_PER_DEGREE)];
        return SIN_TABLE[(int) (degrees * PRECISION_PER_DEGREE)];
    }

    /**
     * @brief Ritorna il seno dell'angolo in radianti dato
     *         Meno preciso ma più veloce di {@link Math#sin(double)}
     *
     * @param radians angolo in radianti
     * @return seno dell'angolo
     */
    public static float sin(float radians) {
        return lookup(toDegrees(radians));
    }

    /**
     * @brief Ritorna il coseno dell'angolo in radianti dato.
     *          Meno preciso ma più veloce di {@link Math#cos(double)}
     *
     * @param radians angolo in radianti
     * @return coseno dell'angolo
     */
    public static float cos(float radians) {
        return lookup(toDegrees(radians) + 90);
    }

    /**
     * @brief Converte l'angolo da radianti a gradi, senza dover effettuare passaggi da double a float
     *
     * @param radians angolo in radianti
     * @return angolo in gradi
     */
    private static float toDegrees(float radians) {
        return radians * 180.0F / PI;
    }
}
