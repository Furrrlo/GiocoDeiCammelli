package me.palla.gui;
import me.palla.GiocoPalla;
import me.palla.gui.components.ColorSlider;
import me.palla.value.ColorValue;
import me.palla.value.Value;

import java.awt.*;
import java.util.List;

/**
 * @author Borzì Davide
 * @brief gestisce le opzioni di gioco modificabili dall'utente
 * @version 1.0
 */

public class OptionsGui extends BaseGui {
    private final List<Value<?>> temp;
    
    public OptionsGui(){
        //inizializzazione nel costruttore
        //in base al tipo di opzioni, genero un tipo diversi di componente (tot: 2 funzioni, 1 componente)
        
        //prendo valori
        temp = GiocoPalla.getInstance().getValueManager().getValues();
        
        for (Integer i = 0; i < temp.size(); i++) {
            final Value<?> value = temp.get(i);

            //per ogni elemento controllo se getType è quello che mi serve (per ora solo Color)
            if (temp.get(i).getValueType().equals(Color.class)) {        //E' GIUSTO?
                //se è di tipo Color, creo un nuovo ColorSlider e lo aggiungo ai componenti
                components.add(new ColorSlider((ColorValue) value));
            }
        }
    }
    
    public void resize(){ //aiutino?
        //sposto alla cdc (ma con controlli immagino)
        //loop nei componenti e decido come metterli
        
    }
}