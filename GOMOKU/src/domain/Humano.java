package domain;

import java.awt.Color;
import java.io.Serializable;

/**
 * Clase para representar a un jugador en el juego.
 * Implementa la interfaz Jugador.
 *
 * @author Jeisson Casallas & Camilo Murcia
 * @version 5/12/2023
 */
public class  Humano implements Jugador,Serializable{
    private Color color;
    private Piedra token;
    private int totalTime;
    private int score;
    private String nombre;
    private int limit;

    /**
     * Constructor de la clase Humano
     * 
     * @param color el que va a tener el jugador dentro del juego.
     */
    public Humano(Color color) {
        this.color = color;
        this.totalTime = 0;
        this.score = 0;
    }
    
    /**
     * Establece la ficha (piedra) del jugador.
     *
     * @param piedra La ficha a establecer.
     */
    public void setNombre(String n) {
    	this.nombre = n;
    }
    
    /**
     * Obtiene el nombre del jugador.
     *
     * @return El nombre del jugador.
     */
    public String getNombre() {
    	return this.nombre;
    }
    
    /**
     * Establece el color del jugador.
     *
     * @param color El color a establecer.
     * @return La instancia del jugador con el color establecido.
     */
    public Humano setColor(Color color) {
        this.color = color;
        return this;
    }
    
    /**
     * Obtiene el color del jugador.
     *
     * @return El color del jugador.
     */
    public Color getColor() {
        return this.color;
    }
    
    

    /**
     * Inicia el tiempo del jugador.
     */
    public void startTime() {
        // Iniciar tiempo (puedes implementar lógica específica aquí)
    }
    
    /**
     * Obtiene el tiempo total acumulado del jugador.
     *
     * @return El tiempo total acumulado del jugador.
     */
    public int getTotalTime() {
        return this.totalTime;
    }

    
    /**
     * Incrementa el puntaje del jugador.
     *
     * @param points La cantidad de puntos a incrementar.
     */
    public void increaseScore(int points) {
        this.score += points;
    }


    /**
     * Obtiene el puntaje actual del jugador.
     *
     * @return El puntaje actual del jugador.
     */
    public int getScore(){
        return this.score;
    }
    
    /**
     * Reinicia el puntaje del jugador.
     */
    public void resetScore() {
    	this.score=0;
    }
	
    
	public void setToken(Piedra piedra) {
		// TODO Auto-generated method stub
		
	}
	
	public void setPL(int p) {
		this.limit = p;
	}
	
	public void piedrasLimitadas() {
		this.limit --;
	}
	@Override
	public int getPL() {
		return this.limit;
		
	}
}

