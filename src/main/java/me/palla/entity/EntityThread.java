
package me.palla.entity;

import java.util.logging.Level;
import java.util.logging.Logger;
import me.palla.GiocoPalla;

public class EntityThread extends Thread{
    private BallEntity ball;
    
    public EntityThread(BallEntity ball){
        this.ball=ball;
    }
    
    public void run(){
        while(!GiocoPalla.getInstance().isPaused()){
            ball.onTick();
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(EntityThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
