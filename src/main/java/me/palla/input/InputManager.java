package me.palla.input;

import java.util.HashMap;
import java.util.Map;

public class InputManager {

    private Map<Object, InputSubscription> subscriptions;

    public InputManager()
    {
        subscriptions = new HashMap<Object, InputSubscription>();
    }
    public InputSubscription subscribe(Object obj, boolean isBlocking) {
        InputSubscription temp = new InputSubscription(isBlocking);
        subscriptions.put(obj, temp);
        return temp;
    }

    public void unsubscribe(Object obj) {
        subscriptions.remove(obj);
    }

    public void post(InputData letto) {
        // per ogni subscription, richiamare il metodo post
        for (InputSubscription subscription : subscriptions.values())
            subscription.post(letto);
    }
}
