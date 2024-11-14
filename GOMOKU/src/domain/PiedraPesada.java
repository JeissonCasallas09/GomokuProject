package domain;
import java.awt.Color;


/**
 * Class of PiedraPesada
 * @author Camilo Murcia & Jeisson Casallas
 * Version 1.0 (16/12/23)
 */
public class PiedraPesada extends Piedra {

    protected final int puntacion = 2;
    private int turnosUsados;
    
    
    /**
     * Constructor de la clase PiedraPesada
     * 
     * @param posiX posicion en X de la piedra
     * @param posiY posicion en Y de la piedra
     * @param turnos turno del jugador.
     */
    public PiedraPesada(int row, int col, int turno){
        super(row, col, turno);
        if(super.color.equals(Color.BLACK)){
            //this.setForeground(Color.WHITE);
            this.setText("P");
        }else if(super.color.equals(Color.WHITE)){
            //this.setForeground(Color.BLACK);
            this.setText("P");
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
        this.turnosUsados = 0;
        return false;
    }
}