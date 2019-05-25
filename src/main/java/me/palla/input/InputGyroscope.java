package me.palla.input;

public class InputGyroscope implements InputData {

    private float xAxis;
    private float yAxis;

    public InputGyroscope(float xAxis, float yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public InputGyroscope() {
        xAxis = 0;
        yAxis = 0;
    }

    // Get e set

    public float getAxisX() {
        return xAxis;
    }

    public void setAxisX(float xAxis) {
        this.xAxis = xAxis;
    }

    public float getAxisY() {
        return yAxis;
    }

    public void setAxisY(float yAxis) {
        this.yAxis = yAxis;
    }
}
