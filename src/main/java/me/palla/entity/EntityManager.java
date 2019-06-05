package me.palla.entity;

import me.palla.Game;
import me.palla.GameReferencer;
import me.palla.renderer.RenderContext;
import me.palla.renderer.RenderManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @brief Classe per la gestione delle Entity
 * @author Mattia Broch
 * @version 1.0
 */
public class EntityManager implements GameReferencer {

    private Game game;
    /** @brief La lista di tutte le Entity */
    private final List<Entity> entities;

    /**
     * @brief Costruttore di EntityManager
     */
    public EntityManager() {
        this.entities = new ArrayList<>();
    }

    /**
     *
     * @param poolNumberX il numero di vasche sull'asse X (il primo valore della matrice di vasche)
     * @param poolNumberY il numero di vasche sull'asse Y (il secondo valore della matrice di vasche)
     */
    public void spawnEntities(int poolNumberX, int poolNumberY) {
        final RenderManager renderManager = game.renderManager();

        // Pools

        final PoolList pools = new PoolList(renderManager, poolNumberX, poolNumberY);
        for (int i = 0; i < pools.getList().size(); i++)
            this.addEntity(pools.getList().get(i));

        // Balls

        final PoolEntity firstPool = pools.getList().get(0);
        final float startXPos = firstPool.getPosX();
        final float startYPos = firstPool.getPosY();

        final PoolEntity lastPool = pools.getList().get(pools.getList().size() - 1);
        final float lastXPos = lastPool.getPosX() + lastPool.getWidth();
        final float lastYPos = lastPool.getPosY() + lastPool.getLength();

        this.addEntity(new BallEntity(
                renderManager.getScaledResolution().getScaledWidth() / 2f + 5,
                renderManager.getScaledResolution().getScaledHeight() / 2f,
                startXPos, startYPos,
                lastXPos, lastYPos));
    }

    /** @brief Metodo che, ogni volta richiamato, chiama il render di ogni Entity */
    public void render(RenderContext ctx) {
        entities.forEach(e -> e.onRender(ctx));
    }

    /**
     * @brief Metodo che, aggiunge un Entity alla lista
     *
     * @param e l'Entity da aggiungere
     */
    private void addEntity(Entity e) {
        if(game != null && e instanceof GameReferencer)
            ((GameReferencer) e).setGame(game);
        entities.add(e);
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
        entities.stream()
                .filter(GameReferencer.class::isInstance)
                .map(GameReferencer.class::cast)
                .forEach(e -> e.setGame(game));
    }
}
