package me.palla.input;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


public class InputSubscription {

    private InputData lastData;
    private Semaphore sem;

    protected InputSubscription() {
        sem = new Semaphore(0);
    }

    public InputData poll() {
        try {
            sem.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(InputSubscription.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lastData;
    }

    void post(InputData letto) {
        lastData = letto;
        sem.release();
    }
}
