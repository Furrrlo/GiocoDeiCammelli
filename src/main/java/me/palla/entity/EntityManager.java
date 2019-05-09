package me.palla.entity;

import java.util.ArrayList;
import java.util.List;

/*
@author Mattia Broch
@version 1.0
@brief classe per la gestione delle Entity
*/
public class EntityManager {
    
    /*
    @brief la lista di tutte le Entity 
    */
    private final List<Entity> entities;
    /*
    @brief la lista delle vasche 
    */

    public EntityManager(int poolNumberX, int poolNumberY) {
        final PoolList pools;
        this.entities = new ArrayList<>();

        pools = new PoolList(poolNumberX, poolNumberY);
        for(int i = 0; i < pools.getList().size(); i++) {
            this.addEntity(pools.getList().get(i));
        }
        this.addEntity(new BallEntity());
    }

    public void render() {
        for(int i = 0; i < entities.size(); i++) {
            entities.get(i).onRender();
        }
    }

    private void addEntity(Entity e) {
        entities.add(e);
    }
}
