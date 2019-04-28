package me.palla.entity;

import me.palla.value.Value;

public class BallEntity implements Entity {
    
    private EntityThread th;
    private float xPos;
    private float yPos;
    private float radius;

    private Value<Float> xSpeed;
    private Value<Float> ySpeed;

    public BallEntity() {
        xPos=0;
        yPos=0;
        radius=0;
        th=new EntityThread(this);
        th.start();
    }

    @Override
    public void onTick() {
        
    }

    @Override
    public void onRender() {

    }
}
