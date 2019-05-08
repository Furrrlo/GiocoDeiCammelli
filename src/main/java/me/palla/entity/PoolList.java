/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.palla.entity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tiabr
 */
public class PoolList {
    
    private List<PoolEntity> list;
    private final int poolNumber;
     
    public PoolList(int poolNumber){
        this.poolNumber=poolNumber;
        this.list = new ArrayList<>();
        for(int i=0;i<poolNumber;i++){
            //addPool(new PoolEntity(100,100,200,200,10,10,5));
        }
    }
    
    public void addPool(PoolEntity pool){
        if(list.size()<poolNumber){
            list.add(pool);
        } 
    }

    public int getPoolNumber() {
        return poolNumber;
    }

    public List<PoolEntity> getList() {
        return list;
    }
    
    
    
    
}
