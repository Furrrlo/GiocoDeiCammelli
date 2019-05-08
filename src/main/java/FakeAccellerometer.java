/*
 * Created by JFormDesigner on Sat May 04 17:45:55 CEST 2019
 */

import me.palla.GiocoPalla;
import me.palla.input.InputGyroscope;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;

public class FakeAccellerometer extends JFrame {

    public FakeAccellerometer() {
        initComponents();
        add(panel1);

        pack();
        setSize(1000, getHeight());

        new Thread(() -> {
            try {
                int xDir = 0, yDir = 0;

                while(true) {
                    int newValue = sliderX.getValue();

                    if(changeX.isSelected()) {
                        switch (xDir) {
                            case 0:
                                newValue += (Integer)spinnerX.getValue();
                                if(newValue >= sliderX.getMaximum())
                                    xDir = 1;
                                break;
                            case 1:
                                newValue -= (Integer)spinnerX.getValue();
                                if(newValue <= sliderX.getMinimum())
                                    xDir = 0;
                                break;
                        }
                    }
                    sliderX.setValue(newValue);

                    newValue = sliderY.getValue();
                    if(changeY.isSelected()) {
                        switch (yDir) {
                            case 0:
                                newValue += (Integer)spinnerY.getValue();
                                if(newValue >= sliderY.getMaximum())
                                    yDir = 1;
                                break;
                            case 1:
                                newValue -= (Integer)spinnerY.getValue();
                                if(newValue <= sliderY.getMinimum())
                                    yDir = 0;
                                break;
                        }
                    }
                    sliderY.setValue(newValue);

                    Thread.sleep(1000 / 20);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sliderStateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();

        if (source.getValueIsAdjusting())
            return;

        GiocoPalla.getInstance().getInputManager().post(new InputGyroscope(
                sliderX.getValue() / 10f,
                sliderY.getValue() / 10f
        ));
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        panel1 = new JPanel();
        JLabel label1 = new JLabel();
        changeX = new JCheckBox();
        panel3 = new JPanel();
        label4 = new JLabel();
        spinnerX = new JSpinner();
        sliderX = new JSlider();
        JLabel label2 = new JLabel();
        changeY = new JCheckBox();
        panel2 = new JPanel();
        label3 = new JLabel();
        spinnerY = new JSpinner();
        sliderY = new JSlider();

        //======== panel1 ========
        {
            panel1.setLayout(new VerticalLayout(15));

            //---- label1 ----
            label1.setText("X Axis:");
            panel1.add(label1);

            //---- changeX ----
            changeX.setText("Auto change");
            panel1.add(changeX);

            //======== panel3 ========
            {
                panel3.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- label4 ----
                label4.setText("Change Step:");
                panel3.add(label4);
                panel3.add(spinnerX);
            }
            panel1.add(panel3);

            //---- sliderX ----
            sliderX.setMajorTickSpacing(100);
            sliderX.setMaximum(850);
            sliderX.setMinimum(-850);
            sliderX.setMinorTickSpacing(10);
            sliderX.setPaintLabels(true);
            sliderX.setPaintTicks(true);
            sliderX.setValue(0);
            sliderX.addChangeListener(e -> sliderStateChanged(e));
            panel1.add(sliderX);

            //---- label2 ----
            label2.setText("Y Axis:");
            panel1.add(label2);

            //---- changeY ----
            changeY.setText("Auto change");
            panel1.add(changeY);

            //======== panel2 ========
            {
                panel2.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- label3 ----
                label3.setText("Change Step:");
                panel2.add(label3);
                panel2.add(spinnerY);
            }
            panel1.add(panel2);

            //---- sliderY ----
            sliderY.setMajorTickSpacing(100);
            sliderY.setMaximum(850);
            sliderY.setMinimum(-850);
            sliderY.setMinorTickSpacing(10);
            sliderY.setPaintLabels(true);
            sliderY.setPaintTicks(true);
            sliderY.setValue(0);
            sliderY.addChangeListener(e -> sliderStateChanged(e));
            panel1.add(sliderY);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel panel1;
    private JCheckBox changeX;
    private JPanel panel3;
    private JLabel label4;
    private JSpinner spinnerX;
    private JSlider sliderX;
    private JCheckBox changeY;
    private JPanel panel2;
    private JLabel label3;
    private JSpinner spinnerY;
    private JSlider sliderY;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
