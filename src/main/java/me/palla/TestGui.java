package me.palla;

import me.palla.gui.BaseGui;
import me.palla.gui.components.ColorSlider;
import me.palla.value.ColorValue;

import java.awt.*;

public class TestGui extends BaseGui {

    public TestGui() {
        components.add(new ColorSlider(new ColorValue("Test", Color.red)));
    }

    @Override
    public void onResize() {
    }
}
