
package me.palla.entity;

import me.palla.GiocoPalla;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PhysicsThread extends Thread {

    private Entity entity;

    public PhysicsThread(Entity entity){
        this.entity = entity;
    }

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
