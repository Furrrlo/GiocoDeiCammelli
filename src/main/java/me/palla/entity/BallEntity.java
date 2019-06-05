package me.palla.entity;

/**
 * @brief Classe per il disegno e il controllo della pallina
 * @author Mattia Broch
 * @version 1.0
 */
public class BallEntity extends BaseEntity<BallEntity> {

    /** @brief Attributo che definisce il raggio della pallina */
    private float radius;
    /** @brief Attributo che definisce la velocità sull'asse X della pallina */
    private float xSpeed;
    /** @brief Attributo che definisce la velocità sull'asse Y della pallina */
    private float ySpeed;

    /** @brief Attributo che definisce la coordinata X dell'ultima vasca */
    private float maxXPos;
    /** @brief Attributo che definisce la coordinata X della prima vasca */
    private float minXPos;
    /** @brief Attributo che definisce la coordinata Y dell'ultima vasca */
    private float maxYPos;
    /** @brief Attributo che definisce la coordinata Y della prima vasca */
    private float minYPos;

    /**
     * @brief Costruttore della pallina, setta la posizione iniziale, il raggio, la velocità, inizializza e
     *         starta il thread per onTick e onRender
     *
     * @param minXPos la posizione più piccola che puo' assumere la X della pallina
     * @param minYPos la posizione più piccola che puo' assumere la Y della pallina
     * @param maxXPos la posizione più grande che puo' assumere la X della pallina
     * @param maxYPos la posizione più grande che puo' assumere la Y della pallina
     */
    public BallEntity(float xPos, float yPos,
                      float minXPos, float minYPos,
                      float maxXPos, float maxYPos) {
        super(xPos, yPos);

        radius = 50;
        xSpeed = 0f;
        ySpeed = 0f;

        this.maxXPos = maxXPos;
        this.maxYPos = maxYPos;
        this.minXPos = minXPos;
        this.minYPos = minYPos;
    }

    /**
     * @brief Metodo ripetuto continuamente che setta le nuove coordinate di X e Y della pallina in base alla
     *         velocità
     */
    @Override
    public void onTick() {
        xPos += xSpeed;
        yPos += ySpeed;
        controllaBordo();
    }

    /**
     * @brief Metodo che setta la velocità sull'asse X della pallina
     *
     * @param rotationX la nuova velocità sull'asse X della pallina
     */
    @Override
    public void rotateX(float rotationX) {
        this.xSpeed = rotationX / 30;
    }

    /**
     * @brief Metodo che setta la velocità sull'asse Y della pallina
     *
     * @param rotationY la nuova velocità sull'asse Y della pallina
     */
    @Override
    public void rotateY(float rotationY) {
        this.ySpeed = rotationY / 30;
    }

    /**
     * @brief Metodo che controlla se la pallina sta toccando un bordo, se lo sta toccanto setta la X e Y in
     *         modo che la pallina non esca
     */
    private void controllaBordo() {
        if (xPos < minXPos + radius / 2f)
            xPos = minXPos + radius / 2f;
        if (xPos > maxXPos - radius / 2f)
            xPos = maxXPos - radius / 2f;
        if (yPos < minYPos + radius / 2f)
            yPos = minYPos + radius / 2f;
        if (yPos > maxYPos - radius / 2f)
            yPos = maxYPos - radius / 2f;
    }

    // Getter

    public float getRadius() {
        return radius;
    }
}
