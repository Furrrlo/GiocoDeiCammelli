package me.palla.entity;

import me.palla.GiocoPalla;
import me.palla.util.FastMath;
import processing.core.PConstants;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.DoubleConsumer;

/**
 * @brief Entity che rappresenta una vasca e gestisce la sabbia al suo interno
 * @author Francesco Ferlin
 */
public class PoolEntity implements Entity {

    // Constants

    /** Colore della vasca */
    private static final int POOL_COLOR = new Color(33, 33, 33).getRGB();
    /** Colore della sabbia */
    private static final int SAND_COLOR = new Color(244, 217, 66).getRGB();

    /** Indica se disegnare le informazioni di debug (vedi {@link #drawDebug()}) */
    private static final boolean DEBUG = false;

    // Attributes

    /** Posizione x su schermo */
    private float xPos;
    /** Posizione y su schermo */
    private float yPos;

    /** Lunghezza delle vasce in pixel */
    private float width;
    /** Altezza delle vasche in pixel (corrisponde anche alla profondità) */
    private float length;

    /** Altezza del bordo di sinistra */
    private float leftBorderHeight;
    /** Altezza del bordo di destra */
    private float rightBorderHeight;
    /** Altezza del bordo sopra */
    private float topBorderHeight;
    /** Altezza del bordo sotto */
    private float bottomBorderHeight;

    /** Volume di sabbia presente nella vasca */
    private float sandVolume;

    /** Thread che gestisce l'input e la fisica della vasca */
    private final Thread physicsThread;

    // Pools

    /** Vasca a destra di questa, o null se non presente */
    private PoolEntity rightPool;
    /** Vasca a sinistra di questa, o null se non presente */
    private PoolEntity leftPool;
    /** Vasca sopra di questa, o null se non presente */
    private PoolEntity topPool;
    /** Vasca sotto di questa, o null se non presente */
    private PoolEntity bottomPool;

    // Rotation

    /**
     * Indica se i dati dipendenti dalla rotazione X sono stati modificati e, quindi, la rotazione deve essere
     * ricalcolata. Variabile atomica in quanto può essere modificata da altre vasche, gestite da altri thread.
     */
    private final AtomicBoolean invalidateRotationX = new AtomicBoolean();
    /** Rotazione x corrente usata per animare il movimento della sabbia */
    private float currRotationX;
    /** Rotazione x dell'oscilloscopio da raggiungere */
    private float targetRotationX;

    /**
     * Indica se i dati dipendenti dalla rotazione Y sono stati modificati e, quindi, la rotazione deve essere
     * ricalcolata. Variabile atomica in quanto può essere modificata da altre vasche, gestite da altri thread.
     */
    private final AtomicBoolean invalidateRotationY = new AtomicBoolean();
    /** Rotazione y corrente usata per animare il movimento della sabbia */
    private float currRotationY;
    /** Rotazione y dell'oscilloscopio da raggiungere */
    private float targetRotationY;

    /** Posizione delle x con origine lo spigolo in alto a sinistra della vasca dove iniziare a disegnare la sabbia */
    private float startSandX;
    /** Lunghezza per disegnare la sabbia */
    private float sandXWidth;

    /** Posizione delle x con origine lo spigolo in alto a sinistra della vasca dove iniziare a disegnare la sabbia */
    private float startSandY;
    /** Altezza per disegnare la sabbia */
    private float sandYWidth;

    /**
     * @brief Crea una nuova vasca con i dati passati e un volume di sabbia adatto
     *          e starta il thread della fisica
     *
     * @param xPos               posizione x su schermo
     * @param yPos               posizione y su schermo
     * @param width              lunghezza delle vasce in pixel
     * @param length             altezza delle vasche in pixel (corrisponde anche alla profondità)
     * @param topBorderHeight    altezza del bordo sopra
     * @param bottomBorderHeight altezza del bordo sotto
     * @param leftBorderHeight   altezza del bordo di sinistra
     * @param rightBorderHeight  altezza del bordo di destra
     */
    public PoolEntity(float xPos, float yPos,
                      float width, float length,
                      float topBorderHeight, float bottomBorderHeight,
                      float leftBorderHeight, float rightBorderHeight) {
        this(
                xPos, yPos,
                width, length,
                topBorderHeight, bottomBorderHeight,
                leftBorderHeight, rightBorderHeight,
                (width * length * 10) / 2F
        );
    }

    /**
     * @brief Crea una nuova vasca con i dati passati e starta il thread della fisica
     *
     * @param xPos               posizione x su schermo
     * @param yPos               posizione y su schermo
     * @param width              lunghezza delle vasce in pixel
     * @param length             altezza delle vasche in pixel (corrisponde anche alla profondità)
     * @param topBorderHeight    altezza del bordo sopra
     * @param bottomBorderHeight altezza del bordo sotto
     * @param leftBorderHeight   altezza del bordo di sinistra
     * @param rightBorderHeight  altezza del bordo di destra
     * @param sandVolume         volume di sabbia
     */
    public PoolEntity(float xPos, float yPos,
                      float width, float length,
                      float topBorderHeight, float bottomBorderHeight,
                      float leftBorderHeight, float rightBorderHeight,
                      float sandVolume) {

        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.length = length;
        this.topBorderHeight = topBorderHeight;
        this.bottomBorderHeight = bottomBorderHeight;
        this.leftBorderHeight = leftBorderHeight;
        this.rightBorderHeight = rightBorderHeight;
        this.sandVolume = sandVolume;

        this.physicsThread = new PhysicsThread(this);
        this.physicsThread.start();

        invalidateRotationX.set(true);
        targetRotationX = 0;
        currRotationX = 0;
        applyRotationX(currRotationX);

        invalidateRotationY.set(true);
        targetRotationY = 0;
        currRotationY = 0;
        applyRotationY(currRotationY);
    }

    /** @brief Aggiorna la fisica della vasca (posizione della sabbia, sabbia che straborda, etc) */
    @Override
    public void onTick() {

        // Rotate X

        if (invalidateRotationX.getAndSet(false))
            applyRotationX(currRotationX);
        if(targetRotationX != currRotationX)
            invalidateRotationX.set(true);

        // Change the x rotation towards the target one
        final float rotationStepX = Math.abs(currRotationX - targetRotationX) / 10f;
        if (currRotationX < targetRotationX)
            currRotationX = Math.min(currRotationX + rotationStepX, targetRotationX);
        else if (currRotationX > targetRotationX)
            currRotationX = Math.max(currRotationX - rotationStepX, targetRotationX);

        // Rotate Y

        if (invalidateRotationY.getAndSet(false))
            applyRotationY(currRotationY);
        if(targetRotationY != currRotationY)
            invalidateRotationY.set(true);

        // Change the y rotation towards the target one
        final float rotationStepY = Math.abs(currRotationY - targetRotationY) / 10f;
        if (currRotationY < targetRotationY)
            currRotationY = Math.min(currRotationY + rotationStepY, targetRotationY);
        else if (currRotationY > targetRotationY)
            currRotationY = Math.max(currRotationY - rotationStepY, targetRotationY);
    }

    @Override
    public void onRender() {
        GiocoPalla.getInstance().pushStyle();

        GiocoPalla.getInstance().stroke(POOL_COLOR);
        GiocoPalla.getInstance().fill(POOL_COLOR);
        GiocoPalla.getInstance().rect(xPos, yPos, width, length, 20);

        GiocoPalla.getInstance().fill(SAND_COLOR);
        if(Math.abs(sandXWidth) > 1 && Math.abs(sandYWidth) > 1) // Kind of fixes horrible rendering
            GiocoPalla.getInstance().rect(xPos + startSandX, yPos + startSandY, sandXWidth, sandYWidth, 20);

        GiocoPalla.getInstance().popStyle();

        if(DEBUG)
            drawDebug();
    }

    @Override
    public void rotateX(float rotationX) {
        this.targetRotationX = rotationX;
        invalidateRotationX.set(true);
    }

    @Override
    public void rotateY(float rotationY) {
        this.targetRotationY = rotationY;
        invalidateRotationY.set(true);
    }

    /**
     * @brief Aggiorna la posizione e il volume della sabbia sull'asse delle x
     *
     * @param delta angolo di rotazione delle x
     */
    private void applyRotationX(float delta) {
        rotate(delta, sandVolume / length, width, rightBorderHeight, leftBorderHeight,
                (sandStart, sandWidth) -> {
                    startSandX = sandStart;
                    sandXWidth = sandWidth;
                },
                (overflowingArea) -> {
                    final float overflowingVolume = (float) (overflowingArea * sandYWidth);
                    final PoolEntity pool = delta > 0 ? rightPool : leftPool;

                    if(pool != null) {
                        sandVolume -= overflowingVolume;
                        pool.addSandVolume(overflowingVolume);
                    }
                });
    }

    /**
     * @brief Aggiorna la posizione e il volume della sabbia sull'asse delle y
     *
     * @param delta angolo di rotazione delle y
     */
    private void applyRotationY(float delta) {
        rotate(delta, sandVolume / width, length, bottomBorderHeight, topBorderHeight,
                (sandStart, sandWidth) -> {
                    startSandY = sandStart;
                    sandYWidth = sandWidth;
                },
                (overflowingArea) -> {
                    final float overflowingVolume = (float) (overflowingArea * sandXWidth);
                    final PoolEntity pool = delta > 0 ? bottomPool : topPool;

                    if(pool != null) {
                        sandVolume -= overflowingVolume;
                        pool.addSandVolume(overflowingVolume);
                    }
                });
    }

    /**
     * @brief Calcola la rotazione su un asse in base ai dati ottenuti e restituisce la posizione della sabbia e
     *         l'area della sabbia che sta uscendo attraverso i listener passati come parametri
     *
     * @param delta            angolo di rotazione dell'asse
     * @param area             area della sabbia
     * @param width            lunghezza della sabbia
     * @param borderHeight1    primo bordo
     * @param borderHeight2    secondo bordo
     * @param onWaterBounds    listener su cui sono ritornati l'inizio e la lunchezza della sabbia
     * @param overflowingWater listener con cui si ritorna l'area della sabbia in eccesso
     */
    private static void rotate(float delta,
                               float area,
                               float width,
                               float borderHeight1, float borderHeight2,
                               BiConsumer<Float, Float> onWaterBounds,
                               DoubleConsumer overflowingWater) {

        // Prevent divisons by 0

        if (delta == 0) {
            onWaterBounds.accept(0f, width);
            return;
        }

        // For comments on how it works refer to
        // the Paint.NET file /docs/PAINT_POOL.pdn
        // or to the exported /docs/PAINT_POOL.png

        float border = borderHeight1;

        boolean reversed = false;
        if (delta < 0) {
            // If the angle is on the other side I can use the same exact
            // method to calculate stuff and then just change the point
            // where I start to draw sand
            delta = -delta;
            border = borderHeight2;
            reversed = true;
        }

        // Find the 3 angles of the triangle
        // alpha is always 90 degrees as it's the border of the boxes and never changes
        // gamma is the same as the inclination based on the Thales' theorem (https://en.wikipedia.org/wiki/Intercept_theorem)
        // beta is just the difference

        float alpha = 90F;
        float gamma = delta;
        float beta = 180F - alpha - gamma;

        // Convert all of them to radians to calculate sine and cosine

        // Unused
//        delta = (float) Math.toRadians(delta);
//        epsilon = (float) Math.toRadians(epsilon);
        alpha = (float) Math.toRadians(alpha);
        gamma = (float) Math.toRadians(gamma);
        beta = (float) Math.toRadians(beta);

        // Find the length of the sides based on area + angles
        // A = (b * h) / 2
        // In this case our base is a
        // Based on the law of sines (https://en.wikipedia.org/wiki/Law_of_sines)
        // h : sin(gamma) = b : sin(90) -> h = b * sin(gamma)
        // and
        // a : sin(alpha) = b : sin(beta) -> b = a * sin(beta) / sin(alpha)
        // So
        // A = (a * h) / 2 = (a * (b * sin(gamma))) / 2 = (a * ((a * sin(beta) / sin(alpha)) * sin(gamma))) / 2 =
        //   = (a * a * sin(beta) * sin(gamma)) / (2sin(alpha))
        // -> a = sqrt((2A * sin(alpha)) / (sin(beta) * sin(gamma)))
        // Then:
        // b = a * sin(beta)
        // c = a * sin(gamma)

        float a = (float) Math.sqrt((2 * area * FastMath.sin(alpha)) / (FastMath.sin(beta) * FastMath.sin(gamma)));
        float b = a * FastMath.sin(beta);
        float c = a * FastMath.sin(gamma);

        // Find area of the sand which got out of the box

        // Refer to
        // the Paint.NET file /docs/PAINT_OVERFLOWING_POOL.pdn
        // or to the exported /docs/PAINT_OVERFLOWING_POOL.png

        if (c > border) {
            final float toRemoveHeight = c - border;
            final float toRemoveBase = toRemoveHeight * FastMath.sin(beta); // Still using the law of sines
            final float toRemoveArea = toRemoveBase * toRemoveHeight / 2f;
            overflowingWater.accept(toRemoveArea);
        }

        // Make the last difference and find where the sand starts

        b = Math.min(width, b);
        // Unused
//        c = Math.min(border, c);

        if (!reversed)
            onWaterBounds.accept(width - b, b);
        else
            onWaterBounds.accept(0f, b);
    }

    /**
     * @brief Disegna per ogni direzione se è presente una vasca (Pool) e l'altezza utilizzata (h), per rendere
     *         più semplice la ricerca di bug.
     */
    private void drawDebug() {
        GiocoPalla.getInstance().pushStyle();

        final float textSize = 10;
        GiocoPalla.getInstance().textSize(textSize);

        // Make the color reverse based on background
        GiocoPalla.getInstance().blendMode(PConstants.DIFFERENCE);
        GiocoPalla.getInstance().fill(Color.white.getRGB());

        GiocoPalla.getInstance().textAlign(PConstants.CENTER, PConstants.TOP);
        GiocoPalla.getInstance().text("Pool: " + (topPool != null),
                xPos + width / 2f,
                yPos);
        GiocoPalla.getInstance().text("h: " + String.format("%1$.2g", topBorderHeight),
                xPos + width / 2f,
                yPos + textSize + 0.25f);

        GiocoPalla.getInstance().text("Pool: " + (bottomPool != null),
                xPos + width / 2f,
                yPos + length - textSize);
        GiocoPalla.getInstance().text("h: " + String.format("%1$.2g", bottomBorderHeight),
                xPos + width / 2f,
                yPos + length - textSize - textSize - 0.25f);

        GiocoPalla.getInstance().textAlign(PConstants.LEFT, PConstants.TOP);
        GiocoPalla.getInstance().text("Pool: " + (leftPool != null),
                xPos,
                yPos + length / 2 - (0.125f + textSize) * 2);
        GiocoPalla.getInstance().text("h: " + String.format("%1$.2g", leftBorderHeight),
                xPos,
                yPos + length / 2 - 0.125f - textSize);

        GiocoPalla.getInstance().textAlign(PConstants.RIGHT, PConstants.TOP);
        GiocoPalla.getInstance().text("Pool: " + (rightPool != null),
                xPos + width,
                yPos + length / 2 + 0.125f);
        GiocoPalla.getInstance().text("h: " + String.format("%1$.2g", rightBorderHeight),
                xPos + width,
                yPos + length / 2 + 0.25f + textSize);

        GiocoPalla.getInstance().popStyle();
    }

    // Important getters and setters

    /**
     * @brief Setta la lunghezza delle vasce in pixel
     *
     * @param width lunghezza
     */
    public void setWidth(float width) {
        this.width = width;
        invalidateRotationX.set(true);
    }

    /**
     * @brief Setta l'altezza delle vasche in pixel (corrisponde anche alla profondità)
     *
     * @param length altezza
     */
    public void setLength(float length) {
        this.length = length;
        invalidateRotationY.set(true);
    }

    /**
     * Setta il volume di sabbia presente nella vasca
     *
     * @param sandVolume volume
     */
    private void setSandVolume(float sandVolume) {
        this.sandVolume = sandVolume;
        invalidateRotationX.set(true);
        invalidateRotationY.set(true);
    }

    /**
     * Aggiunge il volume di sabbia alla vasca
     *
     * @param sandVolume volume da aggiungere
     */
    private void addSandVolume(float sandVolume) {
        setSandVolume(this.sandVolume + sandVolume);
    }

    // Package access so the entityManager can use them

    /**
     * @brief Setta la vasca soprastante
     *
     * @param topPool vasca o null se non presente
     */
    void setTopPool(PoolEntity topPool) {
        this.topPool = topPool;
    }

    /**
     * @brief Setta la vasca sottostante
     *
     * @param bottomPool vasca o null se non presente
     */
    void setBottomPool(PoolEntity bottomPool) {
        this.bottomPool = bottomPool;
    }

    /**
     * @brief Setta la vasca di destra
     *
     * @param rightPool vasca o null se non presente
     */
    void setRightPool(PoolEntity rightPool) {
        this.rightPool = rightPool;
    }

    /**
     * Setta la vasca di sinistra
     *
     * @param leftPool vasca o null se non presente
     */
    void setLeftPool(PoolEntity leftPool) {
        this.leftPool = leftPool;
    }

    // Getters and setters

    /**
     * Restituisce la posizione x su schermo
     *
     * @return posizione x
     */
    public float getPosX() {
        return xPos;
    }

    /**
     * Setta la posizione x su schermo
     *
     * @param xPos posizione x
     */
    public void setPosX(float xPos) {
        this.xPos = xPos;
    }

    /**
     * Restituisce la posizione y su schermo
     *
     * @return posizione y
     */
    public float getPosY() {
        return yPos;
    }

    /**
     * Setta la posizione y su schermo
     *
     * @param yPos posizione y
     */
    public void setPosY(float yPos) {
        this.yPos = yPos;
    }

    /**
     * Restituisce la lunghezza delle vasce in pixel
     *
     * @return lunghezza
     */
    public float getWidth() {
        return width;
    }

    /**
     * Restituisce la altezza delle vasche in pixel (corrisponde anche alla profondità)
     *
     * @return altezza
     */
    public float getLength() {
        return length;
    }

    /**
     * Restituisce l'altezza del bordo di sinistra
     *
     * @return altezza del bordo sx
     */
    public float getLeftBorderHeight() {
        return leftBorderHeight;
    }

    /**
     * Setta l'altezza del bordo di sinistra
     *
     * @param leftBorderHeight altezza del bordo sx
     */
    public void setLeftBorderHeight(float leftBorderHeight) {
        this.leftBorderHeight = leftBorderHeight;
    }

    /**
     * Restituisce l'altezza del bordo di destra
     *
     * @return altezza del bordo dx
     */
    public float getRightBorderHeight() {
        return rightBorderHeight;
    }

    /**
     * Setta l'altezza del bordo di destra
     *
     * @param rightBorderHeight altezza del bordo dx
     */
    public void setRightBorderHeight(float rightBorderHeight) {
        this.rightBorderHeight = rightBorderHeight;
    }

    /**
     * Restituisce l'altezza del bordo sopra
     *
     * @return altezza del bordo sopra
     */
    public float getTopBorderHeight() {
        return topBorderHeight;
    }

    /**
     * Setta l'altezza del bordo sopra
     *
     * @param topBorderHeight altezza del bordo sopra
     */
    public void setTopBorderHeight(float topBorderHeight) {
        this.topBorderHeight = topBorderHeight;
    }

    /**
     * Restituisce l'altezza del bordo sotto
     *
     * @return altezza del bordo sotto
     */
    public float getBottomBorderHeight() {
        return bottomBorderHeight;
    }

    /**
     * Setta l'altezza del bordo sotto
     *
     * @param bottomBorderHeight altezza del bordo sotto
     */
    public void setBottomBorderHeight(float bottomBorderHeight) {
        this.bottomBorderHeight = bottomBorderHeight;
    }

    /**
     * Restituisce il volume di sabbia presente nella vasca
     *
     * @return volume
     */
    public float getSandVolume() {
        return sandVolume;
    }

    /**
     * Restituisce la rotazione x dell'oscilloscopio da raggiungere
     *
     * @return rotazione x da raggiungere
     */
    public float getTargetRotationX() {
        return targetRotationX;
    }

    /**
     * Restituisce la rotazione y dell'oscilloscopio da raggiungere
     *
     * @return rotazione y da raggiungere
     */
    public float getTargetRotationY() {
        return targetRotationY;
    }
}
