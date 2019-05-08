package me.palla.input;

import java.util.Queue;

public class InputSubscription {

    private Queue<InputData> queue;
    private boolean isBlocking;

    protected InputSubscription(boolean isBlocking) {
        // Se isBlocking è vero, inizializza queue a LinkedBlockingQueue
        // altrimenti a ConcurrentLinkedQueue
    }

    public InputData poll() {
        // Se isBlocking è vero, la queue deve essere castata a BlockingQueue
        // e bisogna chiamare il metodo take()
        // altrimenti il poll normale

        // Se poll o take ritornano null, ritornare NoData#instance()
        return null;
    }

    public <T extends InputData> T poll(Class<T> type) {
        return null;
    }

    protected void post(InputData letto) {

    }
}
