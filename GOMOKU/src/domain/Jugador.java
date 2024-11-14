package domain;

import java.awt.Color;

/**
 * La interfaz Jugador define los métodos que deben implementar las clases que representan a los jugadores en el juego.
 *
 * @author Jeisson Casallas & Camilo Murcia
 * @version 5/12/2023
 */
public interface Jugador {

    /**
     * Establece el color del jugador.
     *
     * @param color El color a establecer.
     * @return La instancia del jugador con el color establecido.
     */
    Jugador setColor(Color color);

    /**
     * Obtiene el color del jugador.
     *
     * @return El color del jugador.
     */
    Color getColor();

    /**
     * Establece la ficha (piedra) del jugador.
     *
     * @param piedra La ficha a establecer.
     */
    void setToken(Piedra piedra);

    /**
     * Establece el nombre del jugador.
     *
     * @param n El nombre a establecer.
     */
    void setNombre(String n);

    /**
     * Obtiene el nombre del jugador.
     *
     * @return El nombre del jugador.
     */
    String getNombre();

    /**
     * Inicia el tiempo del jugador.
     */
    void startTime();

    /**
     * Obtiene el tiempo total acumulado del jugador.
     *
     * @return El tiempo total acumulado del jugador.
     */
    int getTotalTime();

    /**
     * Incrementa el puntaje del jugador.
     *
     * @param points La cantidad de puntos a incrementar.
     */
    void increaseScore(int points);

    /**
     * Obtiene el puntaje actual del jugador.
     *
     * @return El puntaje actual del jugador.
     */
    int getScore();

    /**
     * Reinicia el puntaje del jugador.
     */
    void resetScore();
    /**
     * Resta las piedras que quedan restantes
     */
	void piedrasLimitadas();
	/**
     * Establece las piedras que tendra
     */
	void setPL(int p);
	/**
     * retorna las piedras que tendra
     */
	int getPL();
}
