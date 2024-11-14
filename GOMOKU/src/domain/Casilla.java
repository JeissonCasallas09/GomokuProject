package domain;

import javax.swing.JButton;
import java.awt.Color;

/**
 * Abstract class of Casilla
 * @author Camilo Murcia Jeisson Casallas
 * Version 1.0 (16/12/23)
 */
public abstract class Casilla extends JButton {

    protected int posX; 
    protected int posY;
    protected Color emptyPiedraColor = new Color(153, 76, 0);
    protected boolean ocupada; 
    protected Gomoku gomoku;

    /**
     * Constructor de la clase Casilla.
     *
     * @param posiX Posición X de la casilla en el tablero.
     * @param posiY Posición Y de la casilla en el tablero.
     */
    public Casilla(int posiX, int posiY) {
        this.posX = posiX;
        this.posY = posiY;
        this.setBackground(emptyPiedraColor);
        this.ocupada = false;
    }

    /**
     * Obtiene la posición X de la casilla.
     *
     * @return La posición X de la casilla.
     */
    public int getPositionX() {
        return posX;
    }

    /**
     * Obtiene la posición Y de la casilla.
     *
     * @return La posición Y de la casilla.
     */
    public int getPositionY() {
        return posY;
    }

    /**
     * Establece la posición X de la casilla.
     *
     * @param positionX Nueva posición X de la casilla.
     */
    public void setPositionX(int positionX) {
        this.posX = positionX;
    }

    /**
     * Establece la posición Y de la casilla.
     *
     * @param positionY Nueva posición Y de la casilla.
     */
    public void setPositionY(int positionY) {
        this.posY = positionY;
    }

    /**
     * Obtiene el estado de ocupación de la casilla.
     *
     * @return true si la casilla está ocupada, false de lo contrario.
     */
    public boolean getOcupada() {
        return this.ocupada;
    }

    /**
     * Establece el estado de ocupación de la casilla.
     *
     * @param aux Nuevo estado de ocupación.
     */
    public void setOcupada(boolean aux) {
        this.ocupada = aux;
    }


    /**
     * Establece el color de fondo de la casilla.
     *
     * @param color Nuevo color de fondo.
     */
    public void setFondo(Color color) {
        this.setBackground(color);
    }

    /**
     * Restaura el estado de la casilla a su estado inicial.
     *
     */
    public void limpiarCasilla() {
        this.setBackground(emptyPiedraColor);
        this.ocupada = false;
    }

    /**
     * Restaura el estado de una casilla en una posición específica en el tablero.
     *
     * @param row Fila de la casilla en el tablero.
     * @param col Columna de la casilla en el tablero.
     */
    public void limpiarCasilla(int row, int col) {
        this.setBackground(emptyPiedraColor);
        this.ocupada = false;
    }

    /**
     * Método abstracto que representa la acción que realiza una casilla cuando es activada.
     *
     * @param row   Fila de la casilla en el tablero.
     * @param col   Columna de la casilla en el tablero.
     * @param color Color de la piedra que activa la casilla.
     * @param g     Instancia del juego Gomoku al que pertenece la casilla.
     * @return Una instancia de la clase Piedra que representa la piedra colocada en la casilla.
     */
    public abstract Piedra ActCasilla(int row, int col, Color color, Gomoku g);
}
