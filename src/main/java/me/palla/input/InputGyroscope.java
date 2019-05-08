package me.palla.input;

public class InputGyroscope implements InputData {

    private float xAxis;
    private float yAxis;

    public InputGyroscope(float xAxis, float yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }
}
