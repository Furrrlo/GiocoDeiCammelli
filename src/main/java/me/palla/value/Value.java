package me.palla.value;

import me.palla.GiocoPalla;

public class Value<T> {

    private final String name;
    private final Class<T> valueType;

    private T value;

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
