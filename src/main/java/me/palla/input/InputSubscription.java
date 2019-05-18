package me.palla.input;

// TODO: rewrite this class using semaphores or whatever you prefer as long as you can explain it

public class InputSubscription {

    protected InputSubscription() {
    }

    public InputData poll() {
        return null;
    }

    protected void post(InputData letto) {
        // Aggiunge il valore alla queue 
//        queue.add(letto); //ok
    }
}
