package me.palla.input;

// TODO: rewrite this class using semaphores or whatever you prefer as long as you can explain it

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
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

    protected void post(InputData letto) {
        lastData = letto; //ok
        sem.release();
    }
}
