package me.palla.entity;

import me.palla.GiocoPalla;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
@author Mattia Broch
@version 1.0
@brief thread contenuto in ogni Entity, che continua a runnare e richiama onTick
*/
public class PhysicsThread extends Thread {
    /*
    @brief l'Entity in cui è contenuto il thread
    */
    private Entity entity;
    
    /*
    @brief costruttore che inizializza l'attributo entity con la Entity che
    richiama il costruttore del thread  
    */
    public PhysicsThread(Entity entity){
        this.entity = entity;
    }

    /*
    @brief metodo run che controlla se il gioco non è in pausa, se non è 
    in pausa richiamo il metodo onTick dell'attributo entity
    */
    public void run() {
        while(true) {
            if(!GiocoPalla.getInstance().isPaused()) {
                entity.onTick();
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(PhysicsThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
