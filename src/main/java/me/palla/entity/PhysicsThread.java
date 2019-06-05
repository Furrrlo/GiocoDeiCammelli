package me.palla.entity;

import me.palla.Game;
import me.palla.GameReferencer;
import me.palla.input.InputData;
import me.palla.input.InputGyroscope;
import me.palla.input.InputSubscription;
import me.palla.norender.NoRenderGame;

/**
 * @brief Thread contenuto in ogni Entity, che continua a runnare e richiama onTick
 * @author Mattia Broch
 * @version 1.0
 */
public class PhysicsThread extends Thread implements GameReferencer {

    private final Object lock = new Object();

    private Game game;
    private InputSubscription subscription;
    /** @brief L'Entity in cui è contenuto il thread */
    private Entity entity;

    /**
     * @brief costruttore che inizializza l'attributo entity con la Entity che richiama il costruttore del
     *         thread
     */
    public PhysicsThread(Entity entity) {
        this.entity = entity;
        setGame(NoRenderGame.instance());
    }

    /**
     * @brief Metodo run che controlla se il gioco non � in pausa, se non � in pausa richiamo il metodo onTick
     *         dell'attributo entity
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {

            InputSubscription subscription;
            synchronized (lock) {
                subscription = this.subscription;
            }

            final InputData data = subscription.poll();

            boolean isPaused;
            synchronized (lock) {
                isPaused = game.isPaused();
            }

            if (!isPaused && data instanceof InputGyroscope) {
                InputGyroscope dataGy = (InputGyroscope) data;

                entity.rotateX(dataGy.getAxisX());
                entity.rotateY(dataGy.getAxisY());
                entity.onTick();
            }
        }
    }

    @Override
    public void setGame(Game game) {
        synchronized (lock) {

            final Game oldGame = this.game;
            final InputSubscription oldSubscription = this.subscription;

            this.subscription = game.inputManager().subscribe(this);
            if(oldGame != null) {
                oldSubscription.release();
                oldGame.inputManager().unsubscribe(oldSubscription);
            }

            this.game = game;
        }
    }
}
