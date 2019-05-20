package me.palla.input;

// TODO: rewrite this class using semaphores or whatever you prefer as long as you can explain it

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


public class InputSubscription {

    private LinkedBlockingQueue<InputData> queue;
    private Semaphore sem;
    protected InputSubscription() {
    }

    public InputData poll() {
        try {
            sem.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(InputSubscription.class.getName()).log(Level.SEVERE, null, ex);
        }
        InputData ret = queue.poll();        
        return ret;
    }

    protected void post(InputData letto) {
        // Aggiunge il valore alla queue 
        queue.add(letto); //ok
        sem.release();
    }
}
