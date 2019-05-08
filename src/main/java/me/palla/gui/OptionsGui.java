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
    
    public void onResize(){
        //posizionamento al centro
        
        int totHeight = 0, inizioY = 0;
        //calcolo altezza totale dei componenti
        for (int i = 0; i < components.size(); i++) {
            totHeight += components.get(i).getHeight();
        }
        //altezzaSchermo/2 - altezzaComponenti/2 --> punto iniziale
        inizioY = GiocoPalla.getInstance().height/2 - totHeight/2;
        
        //1 componente: quella y; 2 componente: quella y + altezza 1 e cos� via
        for (int i = 0; i < components.size(); i++) {
            int temp = 0;
            for (int j = 0; j < i; j++) {
                temp += components.get(j).getHeight();
            }
            components.get(i).setY(inizioY + temp);
            //per la x:
            //lunghezzaSchermo/2 e lo butto in setCenterX()
            components.get(i).setCenterX(GiocoPalla.getInstance().width);
        }
    }
}