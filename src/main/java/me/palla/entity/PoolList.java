
package me.palla.entity;
import me.palla.GiocoPalla;

import java.util.ArrayList;
import java.util.List;

public class PoolList {
    
    private List<PoolEntity> list;
    
    private final int poolNumberX;
    private final int poolNumberY;
    
    private int lastX;  
    private int lastY;
    private int lenghtX;
    private int lenghtY;
    private final int border = 50;
    
    
     
    public PoolList(int poolNumberX,int poolNumberY){
        this.poolNumberX = poolNumberX;
        this.poolNumberY = poolNumberY;
        lastX = border;
        lastY = border;
        lenghtX = (GiocoPalla.getInstance().getSizeX() - (border * 2)) / poolNumberX;
        lenghtY = (GiocoPalla.getInstance().getSizeY() - (border * 2)) / poolNumberY;
        this.list = new ArrayList<>();
        for(int i = 0; i < poolNumberX; i++){
            for(int j = 0; j < poolNumberY; j++){
                addPool(new PoolEntity(lastX, lastY, lenghtX, lenghtY,10,10,5));
                lastX += lenghtX + 2;
            }
            lastX = border;
            lastY += lenghtY + 2;
        }
    }
    
    private void addPool(PoolEntity pool){
        if(list.size() < poolNumberX * poolNumberY){
            list.add(pool);
        }
    }

    public int getPoolNumberX() {
        return poolNumberX;
    }

    public int getPoolNumberY() {
        return poolNumberY;
    }

    public List<PoolEntity> getList() {
        return list;
    }
}

