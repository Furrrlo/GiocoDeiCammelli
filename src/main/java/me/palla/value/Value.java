package me.palla.value;

import me.palla.GiocoPalla;

/** @author Davide Borzì 
 * @brief Record per ogni elemento grafico e non.
 */
public class Value<T> {

    /**Il nome identificativo*/
    private final String name;
    /**A quale classe appartiene*/
    private final Class<T> valueType;

    /**Il valore vero e proprio*/
    private T value;

    /** @brief Costruttore con parametri
     * Prende in input due parametri e li salva.
     * @param name Il nome da affiliare all'elemento
     * @param defaultValue Il valore dell'elemento
     */
    @SuppressWarnings("unchecked")
    public Value(String name, T defaultValue) {
        this.name = name;
        this.valueType = (Class<T>) defaultValue.getClass();
        this.value = defaultValue;

        GiocoPalla.getInstance().getValueManager().registerValue(this);
    }

    public void set(T obj) {
        this.value = obj;
    }

    public T get() {
        return value;
    }

    public String getName() {
        return name;
    }

    public Class<T> getValueType() {
        return valueType;
    }
}
