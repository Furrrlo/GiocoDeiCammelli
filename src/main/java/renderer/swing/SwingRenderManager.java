package renderer.swing;

import me.palla.Game;
import me.palla.GameReferencer;
import me.palla.entity.BallEntity;
import me.palla.entity.PoolEntity;
import me.palla.gui.BaseGui;
import me.palla.gui.components.ColorSlider;
import me.palla.gui.components.PauseButton;
import me.palla.gui.components.PauseMenuButton;
import me.palla.norender.NoRenderGame;
import me.palla.norender.NoRenderer;
import me.palla.renderer.RenderManager;
import me.palla.renderer.Renderer;
import me.palla.util.ScaledResolution;
import renderer.swing.component.ColorSliderRenderer;
import renderer.swing.component.PauseButtonRenderer;
import renderer.swing.component.PauseMenuButtonRenderer;
import renderer.swing.entity.BallRenderer;
import renderer.swing.entity.PoolRenderer;
import renderer.swing.gui.BaseGuiRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class SwingRenderManager extends JPanel implements RenderManager, GameReferencer {

    // Attributes

    private final JFrame frame;
    private Game game;

    private ScaledResolution scaledResolution;

    private HashMap<Class<?>, Renderer<?>> rendererHashMap;

    public SwingRenderManager() {

        setGame(NoRenderGame.instance());

        frame = new JFrame("SwingGame") {
            @Override
            public void dispose() {
                if(game != null)
                    SwingRenderManager.this.game.shutdown();
            }
        };
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(this);

        scaledResolution = new ScaledResolution(getWidth(), getHeight(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
        addComponentListener(new SwingRenderManager.ResizeHandler());
        addMouseListener(new SwingRenderManager.MouseHandler());

        rendererHashMap = new HashMap<>();
        rendererHashMap.put(BallEntity.class, new BallRenderer(this));
        rendererHashMap.put(PoolEntity.class, new PoolRenderer(this));
        rendererHashMap.put(ColorSlider.class, new ColorSliderRenderer(this));
        rendererHashMap.put(PauseButton.class, new PauseButtonRenderer(this));
        rendererHashMap.put(PauseMenuButton.class, new PauseMenuButtonRenderer(this));
        rendererHashMap.put(BaseGui.class, new BaseGuiRenderer(this));

        frame.setVisible(true);
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setBackground(new Color(33, 33, 33));
        g2d.clearRect(0, 0, getWidth(), getHeight());

        setupScaling(g2d, true);
        game.getCurrentGui().onRender(new SwingContext(g2d));
        setupScaling(g2d, false);

        repaint();
    }

    /**
     * @brief Attiva o disattiva il calcolo della risoluzione
     *
     * @param enable Booleana per attivare o disattivare il metodo
     */
    public void setupScaling(Graphics2D g2d, boolean enable) {
        if (enable) {
            g2d.translate(scaledResolution.getWidthDifference() / 2, scaledResolution.getHeightDifference() / 2);
            g2d.scale(scaledResolution.getWidthScaleFactor(), scaledResolution.getHeightScaleFactor());
        } else {
            g2d.scale(1 / scaledResolution.getWidthScaleFactor(), 1 / scaledResolution.getHeightScaleFactor());
            g2d.translate(-scaledResolution.getWidthDifference() / 2, -scaledResolution.getHeightDifference() / 2);
        }
    }

    private class ResizeHandler extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            scaledResolution = new ScaledResolution(getWidth(), getHeight(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
            game.getCurrentGui().onResize(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
        }
    }

    private final class MouseHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            game.getCurrentGui().onClick(scaledResolution.scaleX(event.getX()), scaledResolution.scaleY(event.getY()));
        }
    }

    @Override
    public float getMouseX() {
        final Point mousePos = getMousePosition();
        if(mousePos == null)
            return -1;
        return (float) mousePos.getX();
    }

    @Override
    public float getMouseY() {
        final Point mousePos = getMousePosition();
        if(mousePos == null)
            return -1;
        return (float) mousePos.getY();
    }

    @Override
    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Renderer<? super T> getRendererFor(Class<T> classType) {
        final Renderer<? super T> renderer = (Renderer<? super T>) rendererHashMap.get(classType);
        return renderer != null ? renderer : NoRenderer.instance();
    }
}
