package renderer.processing.entity;

import me.palla.entity.PoolEntity;
import me.palla.renderer.RenderContext;
import processing.core.PApplet;
import processing.core.PConstants;
import renderer.processing.ProcessingRenderManager;
import renderer.processing.ProcessingRenderer;

import java.awt.*;

public class PoolRenderer extends ProcessingRenderer<PoolEntity> {

    /** Colore della vasca */
    private static final int POOL_COLOR = new Color(33, 33, 33).getRGB();
    /** Colore della sabbia */
    private static final int SAND_COLOR = new Color(244, 217, 66).getRGB();

    /** Indica se disegnare le informazioni di debug (vedi {@link #drawDebug(PoolEntity)}) */
    private static final boolean DEBUG = false;

    public PoolRenderer(ProcessingRenderManager renderManager, PApplet applet) {
        super(renderManager, applet);
    }

    @Override
    public void onRender(RenderContext ctx, PoolEntity pool) {
        applet.pushStyle();

        applet.stroke(POOL_COLOR);
        applet.fill(POOL_COLOR);
        applet.rect(pool.getPosX(), pool.getPosY(), pool.getWidth(), pool.getLength(), 20);

        applet.fill(SAND_COLOR);
        if(Math.abs(pool.getSandWidthX()) > 1 && Math.abs(pool.getSandWidthY()) > 1) // Kind of fixes horrible rendering
            applet.rect(
                    pool.getPosX() + pool.getStartSandX(),
                    pool.getPosY() + pool.getStartSandY(),
                    pool.getSandWidthX(), pool.getSandWidthY(), 20);

        applet.popStyle();

        if(DEBUG)
            drawDebug(pool);
    }

    /**
     * @brief Disegna per ogni direzione se è presente una vasca (Pool) e l'altezza utilizzata (h), per rendere
     *         più semplice la ricerca di bug.
     */
    private void drawDebug(PoolEntity pool) {
        applet.pushStyle();

        final float textSize = 10;
        applet.textSize(textSize);

        // Make the color reverse based on background
        applet.blendMode(PConstants.DIFFERENCE);
        applet.fill(Color.white.getRGB());

        applet.textAlign(PConstants.CENTER, PConstants.TOP);
        applet.text("Pool: " + (pool.getTopPool() != null),
                pool.getPosX() + pool.getWidth() / 2f,
                pool.getPosY());
        applet.text("h: " + String.format("%1$.2g", pool.getTopBorderHeight()),
                pool.getPosX() + pool.getWidth() / 2f,
                pool.getPosY() + textSize + 0.25f);

        applet.text("Pool: " + (pool.getBottomPool() != null),
                pool.getPosX() + pool.getWidth() / 2f,
                pool.getPosY() + pool.getLength() - textSize);
        applet.text("h: " + String.format("%1$.2g", pool.getBottomBorderHeight()),
                pool.getPosX() + pool.getWidth() / 2f,
                pool.getPosY() + pool.getLength() - textSize - textSize - 0.25f);

        applet.textAlign(PConstants.LEFT, PConstants.TOP);
        applet.text("Pool: " + (pool.getLeftPool() != null),
                pool.getPosX(),
                pool.getPosY() + pool.getLength() / 2 - (0.125f + textSize) * 2);
        applet.text("h: " + String.format("%1$.2g", pool.getLeftBorderHeight()),
                pool.getPosX(),
                pool.getPosY() + pool.getLength() / 2 - 0.125f - textSize);

        applet.textAlign(PConstants.RIGHT, PConstants.TOP);
        applet.text("Pool: " + (pool.getRightPool() != null),
                pool.getPosX() + pool.getWidth(),
                pool.getPosY() + pool.getLength() / 2 + 0.125f);
        applet.text("h: " + String.format("%1$.2g", pool.getRightBorderHeight()),
                pool.getPosX() + pool.getWidth(),
                pool.getPosY() + pool.getLength() / 2 + 0.25f + textSize);

        applet.popStyle();
    }
}
