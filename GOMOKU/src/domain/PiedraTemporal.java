package domain;

import java.awt.Color;



/**
 * Class of PiedraTemporal
 * @author Camilo Murcia & Jeisson Casallas
 * Version 1.0 (16/12/23)
 */
public class PiedraTemporal extends Piedra {

    protected final int puntacion = 1;
    private int turnosUsados = 0;
    
    /**
     * Constructor de la clase PiedraTemporal
     * 
     * @param posiX posicion en X de la piedra
     * @param posiY posicion en Y de la piedra
     * @param turnos turno del jugador.
     */
    public PiedraTemporal(int row, int col, int turnos){
        super(row, col,turnos);
        if(super.color.equals(Color.BLACK)){
            //this.setForeground(Color.WHITE);
            this.setText("T");
        }else if(super.color.equals(Color.WHITE)){
            //this.setForeground(Color.BLACK);
            this.setText("T");
        }
    }
    
    /**
     * Obtiene la puntuación asociada a la piedra.
     * 
     * @return La puntuación de la piedra.
     */
    public int getPuntuacion(){
        return this.puntacion;
    }
    
    /**
     * Aumenta el turno asociado a la piedra.
     * 
     * @return true si se aumentó el turno, false de lo contrario.
     */
    
    public boolean aumentarTurno(){
        this.turnosUsados++;
        boolean bandera = false;
        if(this.turnosUsados == 3){
            this.limpiarPiedra();
            bandera = true;
            turnosUsados = 0;
        }
        return bandera;
    }

}