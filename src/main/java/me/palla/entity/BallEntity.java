package me.palla.entity;

import me.palla.GiocoPalla;
import me.palla.value.Value;


public class BallEntity implements Entity {
    
    private PhysicsThread th;
    private float xPos;
    private float yPos;
    private float radius;

    private float xSpeed;
    private float ySpeed;

    public BallEntity() {
        xPos=100;
        yPos=100;
        radius=50;
        th=new PhysicsThread(this);
        th.start();
        xSpeed=1f;
        ySpeed=1f;
    }

    @Override
    public void onTick() {
        xPos+=xSpeed;
        yPos+=ySpeed;
    }

    @Override
    public void onRender() {
        draw(xPos,yPos,radius);
    }
    
    private void draw(float xPos,float yPos,float radius){
        GiocoPalla.getInstance().pushStyle();
        GiocoPalla.getInstance().fill(255,0,0);
        GiocoPalla.getInstance().ellipse(xPos,yPos,radius,radius);
        GiocoPalla.getInstance().popStyle();
    }

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }
    
    
}
