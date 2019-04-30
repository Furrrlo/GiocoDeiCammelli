package me.palla.gui;

/**
 * @author Borzì Davide
 * @brief gestisce le opzioni di gioco modificabili dall'utente
 * @version 1.0
 */

public class OptionsGui extends BaseGui {
    ValueManager values;
    
    public OptionsGui(){
        //inizializzazione nel costruttore
        //in base al tipo di opzioni, genero un tipo diversi di componente (tot: 2 funzioni, 1 componente)
        
        //prendo valori da GiocoPalla.getInstance().valueManager.qualcosa
        //ciclo for e per ognuno (penso di dover usare for each) controllo se getType è quello che mi serve (per ora solo Color)
        //se è di tipo Color, creo un nuovo ColorSlider e lo aggiungo ai componenti
    }
    
    public void resize(){
        //sposto alla cdc (ma con controlli immagino)
        //loop nei componenti e decido come metterli
    }
}