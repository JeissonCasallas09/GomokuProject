package domain;

import java.awt.Color;
import javax.swing.JButton;

/**
 * Abstract class of Piedra
 * @author Camilo Murcia Jeisson Casallas
 * Version 1.0 (16/12/23)
 */
public abstract class Piedra extends JButton{
    protected int posX;
    protected int posY;
    protected Color emptyPiedraColor = new Color(153,76,0);
    protected Color color;
    protected boolean puesta;
    
    /**
     * Constructor de la clase Piedra
     * 
     * @param posiX posicion en X de la piedra
     * @param posiY posicion en Y de la piedra
     * @param turnos turno del jugador.
     */
    public Piedra(int posiX, int posiY, int turnos) {
        super();
        this.posX = posiX;
        this.posY = posiY;
        this.color = emptyPiedraColor;
        this.setBackground(emptyPiedraColor);
    }

    /**
     * Obtiene la posición X de la piedra.
     * 
     * @return La posición X.
     */
    public int getPositionX() {
        return posX;
    }

    /**
     * Obtiene la posición Y de la piedra.
     * 
     * @return La posición Y.
     */
    public int getPositionY() {
        return posY;
    }

    /**
     * Coloca una piedra encima con el color especificado.
     * 
     * @param color El color de la piedra.
     */
    protected void ponerPiedraEncima(Color color) {
        this.color = color;
        this.setBackground(color);
        this.puesta = true;
    }

    /**
     * Limpia la piedra, estableciendo su color al color vacío.
     * 
     */
    public void limpiarPiedra() {
        this.color = emptyPiedraColor;
        this.setBackground(emptyPiedraColor);
        this.puesta = false;
    }

    /**
     * Obtiene el color de la piedra.
     * 
     * @return El color de la piedra.
     */
    public Color getStoneColor() {
        return color;
    }

    /**
     * Actualiza la posición de la piedra.
     * 
     * @param newRow La nueva fila.
     * @param newCol La nueva columna.
     */
    public void actualizarPosicion(int newRow, int newCol) {
        this.posX = newRow;
        this.posY = newCol;
    }
    
    /**
     * Verifica si la piedra está puesta en el tablero.
     * 
     * @return true si la piedra está puesta, false de lo contrario.
     */
    public boolean getPuesta() {
        return this.puesta;
    }

    /**
     * Obtiene la puntuación asociada a la piedra.
     * 
     * @return La puntuación de la piedra.
     */
    public abstract int getPuntuacion();

    /**
     * Aumenta el turno asociado a la piedra.
     * 
     * @return true si se aumentó el turno, false de lo contrario.
     */
    public abstract boolean aumentarTurno();

    
}
