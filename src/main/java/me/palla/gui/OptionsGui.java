package me.palla.gui;
import java.awt.Color;
import me.palla.*;
import me.palla.value.*;
import me.palla.gui.components.*;
import java.util.ArrayList;
import java.util.Collections;
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
            //per ogni elemento controllo se getType è quello che mi serve (per ora solo Color)
            if (temp.get(i).getValueType().toString().equals("Color")) {        //E' GIUSTO?
                //se è di tipo Color, creo un nuovo ColorSlider e lo aggiungo ai componenti
                String name = "Color" + i.toString();
                components.add(new ColorSlider(new ColorValue(name, Color.cyan)));
            }
        }
    }
    
    public void resize(){ //aiutino?
        //sposto alla cdc (ma con controlli immagino)
        //loop nei componenti e decido come metterli
        
    }
}