package domain;

import java.awt.Color;

import javax.swing.border.LineBorder;

/**
 * Class of CasillaTeleport
 * @author Camilo Murcia & Jeisson Casallas
 * Version 1.0 (16/12/23)
 */

public class CasillaTeleport extends Casilla {
    
	/**
     * Constructor de la clase CasillaTeleport.
     * 
     * @param row La fila de la casilla.
     * @param col La columna de la casilla.
     */
    public CasillaTeleport(int row, int col){
        super(row, col);
    }
    
    
    /**
     * Método que realiza la acción de la casilla Teleport. Pone la piedra del jugador en una posicion aleatoria.
     * 
     * @param row   La fila en la que se encuentra la casilla.
     * @param col   La columna en la que se encuentra la casilla.
     * @param color El color de la piedra a colocar encima de la casilla.
     * @param g     El objeto Gomoku que contiene el estado del juego.
     * @return      La piedra en la posicion dada.
     */
	@Override
	public Piedra ActCasilla(int row, int col ,Color color, Gomoku g) {
        int nuevaFila = (int) (Math.random() * g.tam);
        int nuevaColumna = (int) (Math.random() * g.tam);
        Piedra p = g.getPiedraRowCol(row, col);
        if(g.getCasillaRowCol(nuevaFila, nuevaColumna)!=null && g.getPiedraRowCol(nuevaFila, nuevaColumna) != null){
            if(!g.getCasillaRowCol(row, col).getOcupada()){
            	p = g.getPiedraRowCol(nuevaFila, nuevaColumna);
            	if(p != null && !g.getPiedraRowCol(nuevaFila, nuevaColumna).getPuesta()){
            		p.ponerPiedraEncima(color);
            		g.getCasillaRowCol(nuevaFila, nuevaColumna).setOcupada(true);
            		g.getCasillaRowCol(nuevaFila, nuevaColumna).setFondo(color);
            		g.getCasillaRowCol(nuevaFila,nuevaColumna).setBorder(new LineBorder(Color.BLUE));
            	}
            }
        }
        return p;
	}

}