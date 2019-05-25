package accelerometer;

import javax.swing.*;

class AutoChangeRunnable implements Runnable {

    private final FakeAccellerometer gui;

    AutoChangeRunnable(FakeAccellerometer gui) {
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
        int[] xDir = {0};
        int[] yDir = {0};

        while (true) {
            changeValue(gui.changeX, gui.sliderX, gui.spinnerX, xDir);
            changeValue(gui.changeY, gui.sliderY, gui.spinnerY, yDir);
            Thread.sleep(1000 / 20);
        }
    }

    private void changeValue(JCheckBox checkBox,
                             JSlider slider,
                             JSpinner spinner,
                             int[] dir) {
        if (checkBox.isSelected())
            slider.setValue(getNewValue(
                    slider.getValue(),
                    (Integer) spinner.getValue(),
                    slider.getMaximum(), slider.getMinimum(),
                    dir
            ));
    }

    private int getNewValue(int currentValue,
                            int step,
                            int max, int min,
                            int[] dir) {

        if(dir.length < 1) // Using an array just so the values can be passed by reference
            throw new UnsupportedOperationException("Dir array is supposed to hold 1 value");

        int newValue = currentValue;
        switch (dir[0]) {
            case 0:
                newValue += step;
                if (newValue >= max)
                    dir[0] = 1;
                break;
            case 1:
                newValue -= step;
                if (newValue <= min)
                    dir[0] = 0;
                break;
        }
        return newValue;
    }
}
