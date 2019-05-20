package me.palla.input;

import java.util.HashMap;
import java.util.Map;

public class InputManager {

    private Map<Object, InputSubscription> subscriptions;

    public InputManager()
    {
        subscriptions = new HashMap<Object, InputSubscription>();
    }
    public InputSubscription subscribe(Object obj) {
        InputSubscription temp = new InputSubscription();
        subscriptions.put(obj, temp);
        return temp;
    }

    public void unsubscribe(Object obj) {
        subscriptions.remove(obj);
    }

    public void post(InputData letto) {
        for (InputSubscription subscription : subscriptions.values())
            subscription.post(letto);
    }
}
