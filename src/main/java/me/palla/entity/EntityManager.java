package me.palla.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private final List<Entity> entities;
    
    private final PoolList pools;
 
    public EntityManager(int poolNumberX,int poolNumberY) {
        this.entities = new ArrayList<>();
        pools=new PoolList(poolNumberX,poolNumberY);
        this.addEntity(new BallEntity());
        for(int i=0;i<pools.getList().size();i++){
            this.addEntity(pools.getList().get(i));
        }
    }

    public void render() {
        for(int i=0;i<entities.size();i++){
            entities.get(i).onRender();
        }
    }
    
    public void addEntity(Entity e){
        
        entities.add(e);
        
    }
}
