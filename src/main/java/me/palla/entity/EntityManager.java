package me.palla.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private final List<Entity> entities;

    public EntityManager() {
        this.entities = new ArrayList<>();
    }

    public void render() {
        for(int i=0;i<entities.size();i++){
            entities.get(i).onRender();
        }
    }
}
