package accelerometer;

import me.palla.Game;
import me.palla.input.InputGyroscope;

class SensorRunnable implements Runnable {

    private final FakeAccellerometer gui;
    private final Game[] games;

    SensorRunnable(FakeAccellerometer gui, Game... games) {
        this.gui = gui;
        this.games = games;
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
            for (Game game : games)
                game.inputManager().post(new InputGyroscope(
                        gui.sliderX.getValue() / 10f,
                        gui.sliderY.getValue() / 10f
                ));
            Thread.sleep(50);
        }
    }
}
