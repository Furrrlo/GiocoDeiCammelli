package renderer.swing.entity;

import me.palla.entity.PoolEntity;
import org.jdesktop.swingx.graphics.BlendComposite;
import renderer.swing.SwingContext;
import renderer.swing.SwingRenderManager;
import renderer.swing.SwingRenderer;

import java.awt.*;

public class PoolRenderer extends SwingRenderer<PoolEntity> {

    /** Colore della vasca */
    private static final Color POOL_COLOR = new Color(33, 33, 33);
    /** Colore della sabbia */
    private static final Color SAND_COLOR = new Color(244, 217, 66);

    /** Indica se disegnare le informazioni di debug (vedi {@link #drawDebug(SwingContext, Graphics2D, PoolEntity)}) */
    private static final boolean DEBUG = false;

    public PoolRenderer(SwingRenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void onRender(SwingContext ctx, Graphics2D g2d, PoolEntity pool) {

        ctx.fillBorderedRoundRect(
                (int) pool.getPosX(), (int) pool.getPosY(),
                (int) pool.getWidth(), (int) pool.getLength(), 40, POOL_COLOR, POOL_COLOR);

        if(Math.abs(pool.getSandWidthX()) > 1 && Math.abs(pool.getSandWidthY()) > 1) // Kind of fixes horrible rendering
            ctx.fillBorderedRoundRect(
                    (int) (pool.getPosX() + pool.getStartSandX()),
                    (int) (pool.getPosY() + pool.getStartSandY()),
                    (int) pool.getSandWidthX(), (int) pool.getSandWidthY(), 40, POOL_COLOR, SAND_COLOR);

        if(DEBUG)
            drawDebug(ctx, g2d, pool);
    }

    /**
     * @brief Disegna per ogni direzione se è presente una vasca (Pool) e l'altezza utilizzata (h), per rendere
     *         più semplice la ricerca di bug.
     */
    private void drawDebug(SwingContext ctx, Graphics2D g2d, PoolEntity pool) {

        final float textSize = 10;
        ctx.textSize(textSize);

        // Make the color reverse based on background
        Composite oldComposite = g2d.getComposite();
        g2d.setComposite(BlendComposite.getInstance(BlendComposite.BlendingMode.DIFFERENCE));
        g2d.setColor(Color.white);

        ctx.textAlign(SwingContext.HorizontalAlignment.CENTER, SwingContext.VerticalAlignment.TOP);
        ctx.drawString("Pool: " + (pool.getTopPool() != null),
                pool.getPosX() + pool.getWidth() / 2f,
                pool.getPosY());
        ctx.drawString("h: " + String.format("%1$.2g", pool.getTopBorderHeight()),
                pool.getPosX() + pool.getWidth() / 2f,
                pool.getPosY() + textSize + 0.25f);

        ctx.drawString("Pool: " + (pool.getBottomPool() != null),
                pool.getPosX() + pool.getWidth() / 2f,
                pool.getPosY() + pool.getLength() - textSize);
        ctx.drawString("h: " + String.format("%1$.2g", pool.getBottomBorderHeight()),
                pool.getPosX() + pool.getWidth() / 2f,
                pool.getPosY() + pool.getLength() - textSize - textSize - 0.25f);

        ctx.textAlign(SwingContext.HorizontalAlignment.LEFT, SwingContext.VerticalAlignment.TOP);
        ctx.drawString("Pool: " + (pool.getLeftPool() != null),
                pool.getPosX(),
                pool.getPosY() + pool.getLength() / 2 - (0.125f + textSize) * 2);
        ctx.drawString("h: " + String.format("%1$.2g", pool.getLeftBorderHeight()),
                pool.getPosX(),
                pool.getPosY() + pool.getLength() / 2 - 0.125f - textSize);

        ctx.textAlign(SwingContext.HorizontalAlignment.RIGHT, SwingContext.VerticalAlignment.TOP);
        ctx.drawString("Pool: " + (pool.getRightPool() != null),
                pool.getPosX() + pool.getWidth(),
                pool.getPosY() + pool.getLength() / 2 + 0.125f);
        ctx.drawString("h: " + String.format("%1$.2g", pool.getRightBorderHeight()),
                pool.getPosX() + pool.getWidth(),
                pool.getPosY() + pool.getLength() / 2 + 0.25f + textSize);
        g2d.setComposite(oldComposite);
    }
}
