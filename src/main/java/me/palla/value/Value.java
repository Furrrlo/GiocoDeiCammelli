package me.palla.value;

/**
 * @brief Record per ogni elemento grafico e non.
 * @author Davide Borzì
 */
public class Value<T> {

    /** Il nome identificativo */
    private final String name;
    /** A quale classe appartiene */
    private final Class<T> valueType;

    /** Il valore vero e proprio */
    private T value;

    /**
     * @brief Costruttore con parametri Prende in input due parametri e li salva.
     *
     * @param name         Il nome da affiliare all'elemento
     * @param defaultValue Il valore dell'elemento
     */
    @SuppressWarnings("unchecked")
    public Value(String name, T defaultValue) {
        this.name = name;
        this.valueType = (Class<T>) defaultValue.getClass();
        this.value = defaultValue;
    }

    /**
     * @brief Imposta un nuovo oggetto passato come parametro
     * 
     * @param obj Il nuovo oggetto
     */
    public void set(T obj) {
        this.value = obj;
    }

    /**
     * @brief Ritorna il valore dell'oggetto
     * 
     * @return Ritorna il valore vero e proprio di questa istanza
     */
    public T get() {
        return value;
    }

    /**
     * @brief Ritorna il nome
     * 
     * @return Indica, a chi chiama questo metodo, come si chiama l'oggetto
     */
    public String getName() {
        return name;
    }

    /**
     * @brief Ritorna il tipo di oggetto
     * 
     * @return Ritorna la classe a cui appartiene questo oggetto
     */
    public Class<T> getValueType() {
        return valueType;
    }
}
