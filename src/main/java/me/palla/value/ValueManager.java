package me.palla.value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValueManager {

    private final List<Value<?>> values;
    private final List<Value<?>> immutableValues;

    public ValueManager() {
        this.values = new ArrayList<>();
        this.immutableValues = Collections.unmodifiableList(values);
    }

    public void registerValue(Value<?> v) {
        values.add(v);
    }

    public List<Value<?>> getValues() {
        return immutableValues;
    }
}
