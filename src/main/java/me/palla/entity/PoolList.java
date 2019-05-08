
package me.palla.entity;
import me.palla.GiocoPalla;
import me.palla.util.ScaledResolution;

import java.util.ArrayList;
import java.util.List;

public class PoolList {
    
    private List<PoolEntity> list;
    
    private final int poolNumberX;
    private final int poolNumberY;
    
    private float lastX;
    private float lastY;
    private float lenghtX;
    private float lenghtY;

    private final int border = 100;

    public PoolList(int poolNumberX, int poolNumberY){
        this.poolNumberX = poolNumberX;
        this.poolNumberY = poolNumberY;

        final ScaledResolution res = GiocoPalla.getInstance().getScaledResolution();
        lenghtX = (res.getScaledWidth() - (border * 2)) / poolNumberX;
        lenghtY = (res.getScaledHeight() - (border * 2)) / poolNumberY;
        lastX = border;
        lastY = border;

        this.list = new ArrayList<>();
        for(int i = 0; i < poolNumberY; i++) {
            for(int j = 0; j < poolNumberX; j++) {
                addPool(new PoolEntity(
                        lastX, lastY,
                        lenghtX, lenghtY,
                        10,10,
                        5));
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

