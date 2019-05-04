package me.palla.entity;

import me.palla.GiocoPalla;
import me.palla.value.Value;


public class BallEntity implements Entity {
    
    private PhysicsThread th;
    private float xPos;
    private float yPos;
    private float radius;

    private Value<Float> xSpeed;
    private Value<Float> ySpeed;

    public BallEntity() {
        xPos=10;
        yPos=10;
        radius=50;
        th=new PhysicsThread(this);
        th.start();
    }

    @Override
    public void onTick() {
        
        
    }

    @Override
    public void onRender() {
        draw(xPos,yPos,radius);
    }
    
    private void draw(float xPos,float yPos,float radius){
        GiocoPalla.getInstance().ellipse(xPos,yPos,radius,50);
    }
}
