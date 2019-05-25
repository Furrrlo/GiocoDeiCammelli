package me.palla.entity;

import me.palla.GiocoPalla;
import me.palla.input.InputData;
import me.palla.input.InputGyroscope;
import me.palla.input.InputSubscription;

/**
 * @author Mattia Broch
 * @version 1.0
 * @brief thread contenuto in ogni Entity, che continua a runnare e richiama onTick
 */
public class PhysicsThread extends Thread {
    /**
     * @brief l'Entity in cui è contenuto il thread
     */
    private Entity entity;

    private InputSubscription in;

    /**
     * @brief costruttore che inizializza l'attributo entity con la Entity che
     * richiama il costruttore del thread
     */
    public PhysicsThread(Entity entity) {
        this.entity = entity;
        this.in = GiocoPalla.getInstance().getInputManager().subscribe(this);
    }

    /**
     * @brief metodo run che controlla se il gioco non è in pausa, se non è
     * in pausa richiamo il metodo onTick dell'attributo entity
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            InputData data = in.poll();

            if (!GiocoPalla.getInstance().isPaused()) {
                if (data instanceof InputGyroscope) {
                    InputGyroscope dataGy = (InputGyroscope) data;

                    entity.rotateX(dataGy.getAxisX());
                    entity.rotateY(dataGy.getAxisY());
                    entity.onTick();
                }
            }
        }
    }
}
