package accelerometer;

import me.palla.GiocoPalla;
import me.palla.input.InputGyroscope;

class SensorRunnable implements Runnable {

    private final FakeAccellerometer gui;

    SensorRunnable(FakeAccellerometer gui) {
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            run0();
        } catch (InterruptedException ex) {
            System.err.println("Sensor thread got unexpectedly interrupted");
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void run0() throws InterruptedException {
        while (true) {
            GiocoPalla.getInstance().getInputManager().post(new InputGyroscope(
                    gui.sliderX.getValue() / 10f,
                    gui.sliderY.getValue() / 10f
            ));
            Thread.sleep(50);
        }
    }
}
