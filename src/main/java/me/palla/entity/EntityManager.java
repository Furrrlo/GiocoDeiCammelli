package me.palla.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @brief Classe per la gestione delle Entity
 * @author Mattia Broch
 * @version 1.0
 */
public class EntityManager {

    /**
     * @brief La lista di tutte le Entity
     */
    private final List<Entity> entities;

    /**
     * @brief Costruttore di EntityManager, inserisce nella lista entities la pallina e tutte le vasche della
     *         matrice con il metodo addEntity
     *
     * @param poolNumberX il numero di vasche sull'asse X (il primo valore della matrice di vasche)
     * @param poolNumberY il numero di vasche sull'asse Y (il secondo valore della matrice di vasche)
     */
    public EntityManager(int poolNumberX, int poolNumberY) {
        final PoolList pools;
        this.entities = new ArrayList<>();

        pools = new PoolList(poolNumberX, poolNumberY);
        for (int i = 0; i < pools.getList().size(); i++) {
            this.addEntity(pools.getList().get(i));
        }
        float xPos=pools.getList().get(0).getPosX();
        float yPos=pools.getList().get(0).getPosY();
        PoolEntity lastPool=pools.getList().get(pools.getList().size()-1);
        float lastYPos=lastPool.getPosY()+lastPool.getLength();
        float lastXPos=lastPool.getPosX()+lastPool.getWidth();


        this.addEntity(new BallEntity(xPos,yPos,lastYPos,lastXPos));
    }

    /** @brief Metodo che, ogni volta richiamato, chiama il render di ogni Entity */
    public void render() {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).onRender();
        }
    }

    /**
     * @brief Metodo che, aggiunge un Entity alla lista
     *
     * @param e l'Entity da aggiungere
     */
    private void addEntity(Entity e) {
        entities.add(e);
    }
}
