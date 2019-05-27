package me.palla.entity;

import me.palla.GiocoPalla;
import me.palla.util.ScaledResolution;

import java.util.ArrayList;
import java.util.List;

/**
 * @brief Classe che gestisce la matrice di vasche
 * @author Mattia Broch
 * @version 1.0
 */
public class PoolList {
    /** @brief La lista che verrà gestita come una matrice di vasche */
    private List<PoolEntity> list;

    /** @brief L'indice X della matrice di vasche */
    private final int poolNumberX;
    /** @brief L'indice Y della matrice di vasche */
    private final int poolNumberY;

    /** @brief La coordinata X dell'ultima vasca disegnata, usata per calcolare dove disegnare la prossima vasca */
    private float lastX;
    /** @brief La coordinata Y dell'ultima vasca disegnata, usata per calcolare dove disegnare la prossima vasca */
    private float lastY;
    /** @brief La lunghezza di una vasca sull'asse X */
    private float lenghtX;
    /** @brief La lunghezza di una vasca sull'asse Y */
    private float lenghtY;
    /** @brief Il margine a sinistra, usato per disegnare le vasche */
    private final int border = 100;

    /**
     * @brief Costruttore che inizializza la classe e richiama i metodi per trovare le dimensioni di una vasca
     *         in base alla dimensione dello schermo e l'aggiunta delle vasche all lista con le giuste coordinate e
     *         dimensioni
     */
    public PoolList(int poolNumberX, int poolNumberY) {
        this.poolNumberX = poolNumberX;
        this.poolNumberY = poolNumberY;
        lastX = border;
        lastY = border;
        this.list = new ArrayList<>();

        setLenght();

        addAll();
    }

    /**
     * @brief Metodo che ricava le dimensioni di una vasca con le attuali dimensioni dello schermo e il numero
     *         delle vasche
     */
    public void setLenght() {
        final ScaledResolution res = GiocoPalla.getInstance().getScaledResolution();
        lenghtX = (res.getScaledWidth() - (border * 2)) / poolNumberX;
        lenghtY = (res.getScaledHeight() - (border * 2)) / poolNumberY;
    }

    /**
     * @brief Metodo che aggiunge tutte le vasche alla lista list
     */
    public void addAll() {

        final float standardBorder = 0;
        for (int i = 0; i < poolNumberY; i++) {

            float topBorder = standardBorder;
            float bottomBorder = standardBorder;

            if(i == 0)
                topBorder = Float.MAX_VALUE;
            else if(i + 1 == poolNumberY)
                bottomBorder = Float.MAX_VALUE;

            for (int j = 0; j < poolNumberX; j++) {

                float leftBorder = standardBorder;
                float rightBorder = standardBorder;

                if(j == 0)
                    leftBorder = Float.MAX_VALUE;
                else if(j + 1 == poolNumberX)
                    rightBorder = Float.MAX_VALUE;

                addPool(new PoolEntity(
                        lastX, lastY,
                        lenghtX, lenghtY,
                        topBorder, bottomBorder,
                        leftBorder, rightBorder));
                lastX += lenghtX + 2;
            }
            lastX = border;
            lastY += lenghtY + 2;
        }

        // For each pools set the nearby pools

        for (int i = 0; i < poolNumberY; i++) {
            for (int j = 0; j < poolNumberX; j++) {
                final PoolEntity currPool = getAt(i, j);

                if(i != 0)
                    currPool.setTopPool(getAt(i - 1, j));
                if(i + 1 != poolNumberY)
                    currPool.setBottomPool(getAt(i + 1, j));
                if(j != 0)
                    currPool.setLeftPool(getAt(i, j - 1));
                if(j + 1 != poolNumberX)
                    currPool.setRightPool(getAt(i, j + 1));
            }
        }
    }

    /**
     * @brief Metodo che aggiunge una vasca alla lista list
     *
     * @param pool la vasca da aggiungere alla lista list
     */
    private void addPool(PoolEntity pool) {
        if (list.size() < poolNumberX * poolNumberY) {
            list.add(pool);
        }
    }

    private PoolEntity getAt(int row, int column) {
        return list.get(row * poolNumberY + column);
    }

    /** @brief Metodo get che restituisce poolNumberX */
    public int getPoolNumberX() {
        return poolNumberX;
    }

    /** @brief Metodo get che restituisce poolNumberY */
    public int getPoolNumberY() {
        return poolNumberY;
    }

    /** @brief Metodo get che restituisce la lista list */
    public List<PoolEntity> getList() {
        return list;
    }
}

