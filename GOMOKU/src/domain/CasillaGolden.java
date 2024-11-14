package domain;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;


/**
 * Class of CasillaGolden
 * @author Camilo Murcia & Jeisson Casallas
 * Version 1.0 (16/12/23)
 */
public class CasillaGolden extends Casilla {

    private Piedra piedra;
    private String[] tiposDePiedras = {"Pesada, Temporal, Normal"}; 
    
    /**
     * Constructor de la clase CasillaGolden.
     * 
     * @param row La fila de la casilla.
     * @param col La columna de la casilla.
     */
    public CasillaGolden(int row, int col){
        super(row, col);
    }
    
    /**
     * Método que genera una piedra aleatoria y la muestra al jugador.
     * 
     * @param row La fila en la que se encuentra la casilla.
     * @param col La columna en la que se encuentra la casilla.
     * @return La piedra generada aleatoriamente.
     */
    public Piedra piedraRandom(int row, int col){
        int aleatorio = (int)(Math.random() * tiposDePiedras.length);
        String type = tiposDePiedras[aleatorio];
        try {
            Class<?> clasePiedra= Class.forName("piedra." + type);
            this.piedra = (Piedra)clasePiedra.getDeclaredConstructor(String.class, int.class, int.class).newInstance(row, col);
            JOptionPane.showMessageDialog(null, "Has recibido una piedra de tipo " + type + ".", "Casilla Golden", JOptionPane.INFORMATION_MESSAGE);
        }catch(InstantiationException | IllegalAccessException | NoSuchMethodException | ClassNotFoundException | InvocationTargetException e) {
            JOptionPane.showMessageDialog(null, "No se pudo crear la piedra " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return piedra;
    }
    
    /**
     * Método que realiza la acción de la casilla Golden. Le da al jugador una piedra aleatoria.
     * 
     * @param row   La fila en la que se encuentra la casilla.
     * @param col   La columna en la que se encuentra la casilla.
     * @param color El color de la piedra a colocar encima de la casilla.
     * @param g     El objeto Gomoku que contiene el estado del juego.
     * @return      La piedra que estaba en la casilla.
     */
    @Override
    public Piedra ActCasilla(int row, int col, Color color, Gomoku g) {
        Piedra p = g.getPiedraRowCol(row,col);
        if(p instanceof PiedraNormal) {
        	
        }
    	return p;
    }
    
}