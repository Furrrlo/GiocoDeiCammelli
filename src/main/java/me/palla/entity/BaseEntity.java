package me.palla.entity;

import me.palla.Game;
import me.palla.GameReferencer;
import me.palla.norender.NoRenderGame;
import me.palla.renderer.RenderContext;
import me.palla.renderer.RenderManager;
import me.palla.renderer.Renderer;

public abstract class BaseEntity<T extends BaseEntity> implements Entity, GameReferencer {

    protected Game game;
    protected RenderManager renderManager;
    protected Renderer<? super T> renderer;

    /** Thread che gestisce l'input e la fisica dell'entity */
    protected final PhysicsThread physicsThread;

    /** Posizione x su schermo */
    protected float xPos;
    /** Posizione y su schermo */
    protected float yPos;

    protected BaseEntity(float xPos, float yPos) {
        this.xPos = xPos;
        this.yPos = yPos;

        this.physicsThread = new PhysicsThread(this);
        this.physicsThread.start();

        setGame(NoRenderGame.instance());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onRender(RenderContext ctx) {
        renderer.onRender(ctx, (T) this);
    }

    // Getters and setters

    @Override
    @SuppressWarnings("unchecked")
    public void setGame(Game game) {
        this.game = game;
        this.physicsThread.setGame(game);
        this.renderManager = game.renderManager();
        this.renderer = (Renderer<? super T>) renderManager.getRendererFor(getClass());
    }

    public float getPosX() {
        return xPos;
    }

    public float getPosY() {
        return yPos;
    }
}
