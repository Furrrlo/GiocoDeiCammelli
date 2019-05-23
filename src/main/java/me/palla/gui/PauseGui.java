package me.palla.gui;

import me.palla.GiocoPalla;
import me.palla.util.ScaledResolution;

import java.awt.*;

public class PauseGui extends BaseGui {

    private static final GameGui BACKGROUND_GUI = new GameGui();
    private static final int OVERLAY_COLOR = new Color(22, 22, 22, 175).getRGB();

    @Override
    public void onRender() {
        BACKGROUND_GUI.onRender();

        GiocoPalla.getInstance().pushStyle();
        final ScaledResolution res = GiocoPalla.getInstance().getScaledResolution();
        GiocoPalla.getInstance().fill(OVERLAY_COLOR);
        GiocoPalla.getInstance().rect(0, 0, res.getScaledWidth(), res.getScaledHeight());
        GiocoPalla.getInstance().popStyle();

        super.onRender();
    }

    @Override
    public void onResize(float width, float height) {
        BACKGROUND_GUI.onResize(width, height);
        super.onResize(width, height);
    }
}
