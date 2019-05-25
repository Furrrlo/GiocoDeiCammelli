package me.palla.entity;

/*
@author Mattia Broch
@version 1.0
@brief interfaccia per ogni elemento che dovrà essere disegnato (Es Pallina, Vasche) 
*/
public interface Entity {
    /*
    @brief metodo che aggiorna i dati della Entity
    */
    void onTick();
    /*
    @brief metodo che ridisegna la Entity
    */
    void onRender();
    
    void rotateX(float rotationX);
    
    void rotateY(float rotationY);
}
