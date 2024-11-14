package domain;

import java.awt.Color;


/**
 * Class of CasillaNormal
 * @author Camilo Murcia & Jeisson Casallas
 * Version 1.0 (16/12/23)
 */
public class CasillaNormal extends Casilla {
	
	
	/**
     * Constructor de la clase CasillaNormal.
     * 
     * @param row La fila de la casilla.
     * @param col La columna de la casilla.
     */
	public CasillaNormal(int row, int col) {
		super(row,col);
	}
	
	
	 /**
     * Método que realiza la acción de la casilla normal. Coloca una piedra.
     * 
     * @param row   La fila en la que se encuentra la casilla.
     * @param col   La columna en la que se encuentra la casilla.
     * @param color El color de la piedra a colocar encima de la casilla.
     * @param g     El objeto Gomoku que contiene el estado del juego.
     * @return      La piedra que estaba en la casilla antes de la acción.
     */
	
	@Override
	public Piedra ActCasilla(int row, int col, Color color, Gomoku g) {
		Piedra p = g.getPiedraRowCol(row, col);
		p.ponerPiedraEncima(color);
		this.setOcupada(true);this.setFondo(color);
		return p;
	}

	

}
