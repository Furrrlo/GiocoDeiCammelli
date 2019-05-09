package me.palla.input;

import java.util.Map;

public class InputManager {

    private Map<Object, InputSubscription> subscriptions;

    public InputSubscription subscribe(Object obj) {
        //
        return null;
    }

    public void unsubscribe(Object obj) {

    }

    public void post(InputData letto) {
        // per ogni subscription, richiamare il metodo post
        for (InputSubscription subscription : subscriptions.values())
            subscription.post(letto);
    }
}
