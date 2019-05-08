package me.palla.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private final List<Entity> entities;
    private final PoolList pools;

    public EntityManager(int poolNumberX, int poolNumberY) {
        this.entities = new ArrayList<>();

        this.pools = new PoolList(poolNumberX, poolNumberY);
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
