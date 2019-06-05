package me.palla.value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @brief Classe per gestire tutti gli elementi grafici tramite liste
 * 
 * @author Davide Borzì
 */
public class ValueManager {

    /** Lista degli elementi */
    private final List<Value<?>> values;
    /** Lista di elementi che non cambiano */
    private final List<Value<?>> immutableValues;

    /** @brief Costruttore. Inizializza le due liste */
    public ValueManager() {
        this.values = new ArrayList<>();
        this.immutableValues = Collections.unmodifiableList(values);
    }

    /** @brief Registra un nuovo valore passato come parametro */
    public void registerValue(Value<?> v) {
        values.add(v);
    }

    @SuppressWarnings("unchecked")
    public <T> T getValueByName(String name) {
        return (T) values.stream()
                .filter(v -> v.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * @brief Ritorna la lista dei valori standard
     * 
     * @return Ritorna la lista di valori che non cambiano
     */
    public List<Value<?>> getValues() {
        return immutableValues;
    }
}
