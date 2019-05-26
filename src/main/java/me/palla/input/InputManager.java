package me.palla.input;

import java.util.HashMap;
import java.util.Map;

/**
 * @brief Gestore dell'input Questa classe gestisce a chi inoltrare e a chi no il dato ricevuto in input
 * @author Christian Ferrareis
 * @version 1.0
 */
public class InputManager {

    /** Mappa di oggetti che riceveranno il dato in input */
    private Map<Object, InputSubscription> subscriptions;

    /**
     * @brief Costruttore Inizializza la mappa di sottoscrizioni
     */
    public InputManager() {
        subscriptions = new HashMap<>();
    }

    /**
     * @brief Sottoscrizione Con questo metodo, si aggiunge l'oggetto obj nella lista delle sottoscrizioni
     *
     * @param obj L'oggetto da sottoscrivere
     * @return Ritorna l'oggetto rappresentante l'iscrizione
     */
    public InputSubscription subscribe(Object obj) {
        InputSubscription temp = new InputSubscription();
        subscriptions.put(obj, temp);
        return temp;
    }

    /**
     * @brief Cancella l'iscrizione Con questo metodo, l'elemento obj viene rimosso dalla lista delle classi
     *         sottoscritte
     *
     * @param obj L'oggetto che non riceverà più l'input
     */
    public void unsubscribe(Object obj) {
        subscriptions.remove(obj);
    }

    /**
     * @brief Inoltro nuovo valore Segnala ed inoltra a tutte gli elementi nella lista il nuovo valore in input
     *
     * @param letto Il valore da inoltrare
     */
    public void post(InputData letto) {
        for (InputSubscription subscription : subscriptions.values())
            subscription.post(letto);
    }
}
