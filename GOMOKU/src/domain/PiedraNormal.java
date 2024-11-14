package domain;

import java.awt.Color;

public class PiedraNormal extends Piedra {

    protected final int puntacion = 1;
    private int turnosUsados;

    public PiedraNormal(int row, int col,int turno){
        super(row, col,turno);
        if(super.color.equals(Color.BLACK)){
            //this.setForeground(Color.WHITE);
            this.setText("HOLA");
        }else if(super.color.equals(Color.WHITE)){
            //this.setForeground(Color.BLACK);
            this.setText("HOLA");
        }
    }

    public int getPuntuacion(){
        return this.puntacion;
    }

    public boolean aumentarTurno(){
        this.turnosUsados = 0;
        return false;
    }

}
