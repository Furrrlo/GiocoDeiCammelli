package me.palla.input;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputSubscription {

    private Queue<InputData> queue;
    private boolean isBlocking;

    protected InputSubscription(boolean isBlocking) {
        // Se isBlocking è vero, inizializza queue a LinkedBlockingQueue
        // altrimenti a ConcurrentLinkedQueue
        if (isBlocking){
            queue = new LinkedBlockingQueue();
        }
        else {
            queue = new ConcurrentLinkedQueue();
        }
    }

    public InputData poll() {
        // Se isBlocking è vero, la queue deve essere castata a BlockingQueue
        // e bisogna chiamare il metodo take()
        // altrimenti il poll normale

        // Se poll o take ritornano null, ritornare NoData#instance()
        Object temp = null;
        if (isBlocking) {
            try {
                temp = ((BlockingQueue)queue).take();
            } catch (InterruptedException ex) {
                Logger.getLogger(InputSubscription.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (temp == null)
            return NoInput.instance();
        else
            return null;
    }

    public <T extends InputData> T poll(Class<T> type) {
        // Richiama l'altro poll fino a quando
        // type.isAssignableFrom(value.getType()), dove value
        // è quello ritornato dal metodo poll
        //
        // Se value è instanza di NoInput, ritornare null
        InputData value = null;
        do
        {
            value = poll();
        } while (!type.isAssignableFrom(value.getClass()));
        
        if (value == NoInput.instance())
           return null;
        return (T)value;
    }

    protected void post(InputData letto) {
        // Aggiunge il valore alla queue 
        queue.add(letto); //ok
    }
}
