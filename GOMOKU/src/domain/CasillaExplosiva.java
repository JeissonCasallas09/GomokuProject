package domain;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

/**
 * Class of CasillaExplosiva
 * @author Camilo Murcia & Jeisson Casallas
 * Version 1.0 (16/12/23)
 */
public class CasillaExplosiva extends Casilla {
	
	/**
     * Constructor de la clase CasillaExplosiva.
     * 
     * @param row La fila de la casilla.
     * @param col La columna de la casilla.
     */
	
    public CasillaExplosiva(int row, int col){
        super(row, col);
    }
    
    /**
     * Método que realiza la acción de la casilla explosiva. Elimina casillas en un espacio 3x3
     * 
     * @param row   La fila en la que se encuentra la casilla.
     * @param col   La columna en la que se encuentra la casilla.
     * @param color El color de la piedra a colocar encima de la casilla.
     * @param g     El objeto Gomoku que contiene el estado del juego.
     * @return      La piedra colocada encima de la casilla.
     */
	@Override
	public Piedra ActCasilla(int row, int col, Color color, Gomoku g) {
		Piedra p = g.getPiedraRowCol(row, col);
		p.ponerPiedraEncima(color);
		this.setOcupada(true);this.setFondo(color);
		g.getCasillaRowCol(row,col).setBorder(new LineBorder(Color.RED));
		for (int i = row-1; i <= row+1; i++) {
	        for (int j = col-1; j <= col+1; j++){
	            if(i > 0 && i <= g.tam && j > 0 && j <= g.tam && !(i == row && j == col)){
					g.getPiedraRowCol(i, j).limpiarPiedra();
					g.getCasillaRowCol(i, j).limpiarCasilla();
				}
	        }
	    }
	    JOptionPane.showMessageDialog(null, "Casilla explosiva ha explotado y eliminado piedras en un espacio 3x3.", "Explosión", JOptionPane.INFORMATION_MESSAGE);
		return p;
	}
}