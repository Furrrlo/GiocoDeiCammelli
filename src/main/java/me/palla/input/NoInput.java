package me.palla.input;

public final class NoInput implements InputData {

    private static final NoInput INSTANCE = new NoInput();

    private NoInput() {}

    public static NoInput instance() {
        return INSTANCE;
    }
}
