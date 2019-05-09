package me.palla.entity;

import me.palla.GiocoPalla;
import me.palla.util.ScaledResolution;
import me.palla.value.Value;

/*
@author Mattia Broch
@version 1.0
@brief classe per il disegno e il controllo della pallina 
*/
public class BallEntity implements Entity {
    /*
    @brief attributo di classe PhysicsThread, farà startare nella pallina il thread
    che permetterà la ripetizione di onTick() e onRender()
    */
    private PhysicsThread th;
    
    
    /*
    @brief attributo che definisce la coordinata X della pallina,
    verrà aggiornato da onTick()
    */
    private float xPos;
    /*
    @brief attributo che definisce la coordinata Y della pallina,
    verrà aggiornato da onTick()
    */
    private float yPos;
    /*
    @brief attributo che definisce il raggio della pallina
    */
    private float radius;
    /*
    @brief attributo che definisce la velocità sull'asse X della pallina
    */
    private float xSpeed;
    /*
    @brief attributo che definisce la velocità sull'asse Y della pallina
    */
    private float ySpeed;
    
    /*
    @brief costruttore della pallina, setta la posizione iniziale, il raggio,
    la velocità, inizializza e starta il thread per onTick e onRender 
    */
    public BallEntity() {
        xPos=100;
        yPos=100;
        radius=50;
        th=new PhysicsThread(this);
        th.start();
        xSpeed=1f;
        ySpeed=1f;
    }

    /*
    @brief metodo ripetuto continuamente che setta le nuove coordinate
    di X e Y della pallina in base alla velocità
    */
    @Override
    public void onTick() {
        xPos+=xSpeed;
        yPos+=ySpeed;
    }
    
    /*
    @brief metodo ripetuto continuamente che ogni volta richiama il metodo per
    il disegno della pallina
    */
    @Override
    public void onRender() {
        draw(xPos,yPos,radius);
    }
    /*
    @brief metodo che disegna la pallina
    @param xPos la coordinata X della pallina
    @param yPos la coordinata Y della pallina
    @param radius il raggio della pallina
    */
    private void draw(float xPos,float yPos,float radius){
        GiocoPalla.getInstance().pushStyle();
        GiocoPalla.getInstance().fill(255,0,0);
        GiocoPalla.getInstance().ellipse(xPos,yPos,radius,radius);
        GiocoPalla.getInstance().popStyle();
    }
    
    /*
    @brief metodo che setta la velocità sull'asse X della pallina
    @param xSpeed la nuova velocità sull'asse X della pallina
    */
    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    /*
    @brief metodo che setta la velocità sull'asse Y della pallina
    @param ySpeed la nuova velocità sull'asse Y della pallina
    */
    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }
    
    
}
