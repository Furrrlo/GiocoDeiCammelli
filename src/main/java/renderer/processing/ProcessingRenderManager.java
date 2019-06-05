package renderer.processing;

import me.palla.Game;
import me.palla.GameReferencer;
import me.palla.entity.BallEntity;
import me.palla.entity.PoolEntity;
import me.palla.gui.BaseGui;
import me.palla.gui.components.ColorSlider;
import me.palla.gui.components.PauseButton;
import me.palla.gui.components.PauseMenuButton;
import me.palla.norender.NoContext;
import me.palla.norender.NoRenderGame;
import me.palla.norender.NoRenderer;
import me.palla.renderer.RenderManager;
import me.palla.renderer.Renderer;
import me.palla.util.ScaledResolution;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import renderer.processing.component.ColorSliderRenderer;
import renderer.processing.component.PauseButtonRenderer;
import renderer.processing.component.PauseMenuButtonRenderer;
import renderer.processing.entity.BallRenderer;
import renderer.processing.entity.PoolRenderer;
import renderer.processing.gui.BaseGuiRenderer;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class ProcessingRenderManager extends PApplet implements RenderManager, GameReferencer {

    // Constants

    private static ProcessingRenderManager LAST_INSTANCE;

    private static final Object INSTANCE_LOCK = new Object();
    private static CountDownLatch INSTANCE_GATE;

    // Attributes

    private Game game;

    private ScaledResolution scaledResolution;
    private float oldWidth;
    private float oldHeight;

    private HashMap<Class<?>, Renderer<?>> rendererHashMap;

    @Deprecated
    public ProcessingRenderManager() {

        if(INSTANCE_GATE == null)
            throw new UnsupportedOperationException("Do not call the constructor. Use ProcessingRenderManager#createInstance(Game)");

        setGame(NoRenderGame.instance());
        scaledResolution = new ScaledResolution(
                RenderManager.DEFAULT_WIDTH, RenderManager.DEFAULT_HEIGHT,
                RenderManager.DEFAULT_WIDTH, RenderManager.DEFAULT_HEIGHT);

        rendererHashMap = new HashMap<>();
        rendererHashMap.put(BallEntity.class, new BallRenderer(this, this));
        rendererHashMap.put(PoolEntity.class, new PoolRenderer(this, this));
        rendererHashMap.put(ColorSlider.class, new ColorSliderRenderer(this, this));
        rendererHashMap.put(PauseButton.class, new PauseButtonRenderer(this, this));
        rendererHashMap.put(PauseMenuButton.class, new PauseMenuButtonRenderer(this, this));
        rendererHashMap.put(BaseGui.class, new BaseGuiRenderer(this, this));

        LAST_INSTANCE = this;
        INSTANCE_GATE.countDown();
    }

    public static ProcessingRenderManager createInstance() {
        synchronized (INSTANCE_LOCK) {
            try {
                INSTANCE_GATE = new CountDownLatch(1);

                PApplet.main(ProcessingRenderManager.class);
                INSTANCE_GATE.await();
                INSTANCE_GATE = null;

                return LAST_INSTANCE;
            } catch (InterruptedException e) {
                throw new ExceptionInInitializerError("Thread got unexpectedly interrupted");
            }
        }
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void settings() {
        size(RenderManager.DEFAULT_WIDTH, RenderManager.DEFAULT_HEIGHT);
    }

    @Override
    public void setup() {
        surface.setResizable(true);
    }

    @Override
    public void draw() {

        if (width != oldWidth || height != oldHeight) {
            scaledResolution = new ScaledResolution(width, height, RenderManager.DEFAULT_WIDTH, RenderManager.DEFAULT_HEIGHT);
            game.getCurrentGui().onResize(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
        }
        oldWidth = width;
        oldHeight = height;

        clear();
        background(33, 33, 33);

        setupScaling(true);
        game.getCurrentGui().onRender(NoContext.instance());
        setupScaling(false);
    }

    /**
     * @brief Attiva o disattiva il calcolo della risoluzione
     *
     * @param enable Booleana per attivare o disattivare il metodo
     */
    public void setupScaling(boolean enable) {
        if (enable) {
            translate(scaledResolution.getWidthDifference() / 2, scaledResolution.getHeightDifference() / 2);
            scale(scaledResolution.getWidthScaleFactor(), scaledResolution.getHeightScaleFactor());
        } else {
            scale(1 / scaledResolution.getWidthScaleFactor(), 1 / scaledResolution.getHeightScaleFactor());
            translate(-scaledResolution.getWidthDifference() / 2, -scaledResolution.getHeightDifference() / 2);
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        game.getCurrentGui().onClick(scaledResolution.scaleX(event.getX()), scaledResolution.scaleY(event.getY()));
    }

    @Override
    public float getMouseX() {
        return mouseX;
    }

    @Override
    public float getMouseY() {
        return mouseY;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == ESC) {
            key = 0;
            keyCode = 0;
        }
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
