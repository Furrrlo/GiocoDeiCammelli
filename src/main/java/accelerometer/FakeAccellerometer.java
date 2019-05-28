package accelerometer;

import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import java.awt.*;

public class FakeAccellerometer extends JFrame {

    public FakeAccellerometer() {
        initComponents();
        add(contentPanel);

        pack();
        setSize(700, getHeight());
        setLocationRelativeTo(null);

        final Thread th1 = new Thread(new SensorRunnable(this));
        final Thread th2 = new Thread(new AutoChangeRunnable(this));

        th1.setDaemon(true);
        th2.setDaemon(true);

        th1.start();
        th2.start();
    }

    @SuppressWarnings("ALL")
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        contentPanel = new JPanel();
        JLabel xAxisLabel = new JLabel();
        changeX = new JCheckBox();
        JPanel xStepPanel = new JPanel();
        JLabel xStepLabel = new JLabel();
        spinnerX = new JSpinner();
        sliderX = new JSlider();
        JLabel yAxisLabel = new JLabel();
        changeY = new JCheckBox();
        JPanel yStepPanel = new JPanel();
        JLabel yStepLabel = new JLabel();
        spinnerY = new JSpinner();
        sliderY = new JSlider();

        //======== contentPanel ========
        {
            contentPanel.setLayout(new VerticalLayout(15));

            //---- xAxisLabel ----
            xAxisLabel.setText("X Axis:");
            contentPanel.add(xAxisLabel);

            //---- changeX ----
            changeX.setText("Auto change");
            contentPanel.add(changeX);

            //======== xStepPanel ========
            {
                xStepPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- xStepLabel ----
                xStepLabel.setText("Change Step:");
                xStepPanel.add(xStepLabel);

                //---- spinnerX ----
                spinnerX.setPreferredSize(new Dimension(80, 32));
                xStepPanel.add(spinnerX);
            }
            contentPanel.add(xStepPanel);

            //---- sliderX ----
            sliderX.setMajorTickSpacing(100);
            sliderX.setMaximum(850);
            sliderX.setMinimum(-850);
            sliderX.setMinorTickSpacing(10);
            sliderX.setPaintLabels(true);
            sliderX.setPaintTicks(true);
            sliderX.setValue(0);
            contentPanel.add(sliderX);

            //---- yAxisLabel ----
            yAxisLabel.setText("Y Axis:");
            contentPanel.add(yAxisLabel);

            //---- changeY ----
            changeY.setText("Auto change");
            contentPanel.add(changeY);

            //======== yStepPanel ========
            {
                yStepPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

                //---- yStepLabel ----
                yStepLabel.setText("Change Step:");
                yStepPanel.add(yStepLabel);

                //---- spinnerY ----
                spinnerY.setPreferredSize(new Dimension(80, 32));
                yStepPanel.add(spinnerY);
            }
            contentPanel.add(yStepPanel);

            //---- sliderY ----
            sliderY.setMajorTickSpacing(100);
            sliderY.setMaximum(850);
            sliderY.setMinimum(-850);
            sliderY.setMinorTickSpacing(10);
            sliderY.setPaintLabels(true);
            sliderY.setPaintTicks(true);
            sliderY.setValue(0);
            contentPanel.add(sliderY);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel contentPanel;
    JCheckBox changeX;
    JSpinner spinnerX;
    JSlider sliderX;
    JCheckBox changeY;
    JSpinner spinnerY;
    JSlider sliderY;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
