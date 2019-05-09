package me.palla.input;

public class InputGyroscope implements InputData {

    private float xAxis;
    private float yAxis;

    public InputGyroscope(float xAxis, float yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }
    
    //get e set
    public float getxAxis() {
        return xAxis;
    }

    public void setxAxis(float xAxis) {
        this.xAxis = xAxis;
    }

    public float getyAxis() {
        return yAxis;
    }

    public void setyAxis(float yAxis) {
        this.yAxis = yAxis;
    }
}
