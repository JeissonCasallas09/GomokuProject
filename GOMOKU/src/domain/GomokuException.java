package domain;

import java.io.Serializable;


/**
 * Class for GomokuException
 *
 * @author Jeisson Casallas & Camilo Murcia
 * @version 1.0
 */
public class GomokuException extends Exception implements Serializable{
	
    public static final String INVALID_TIME = "El tiempo deseado es incorrecto";
    public static final String PLAYER_NOT_FOUND = "El jugador no existe";//Cargar jugador y asociados
    public static final String SAVE_ERROR = "Error al guardar el archivo";//Metodo Save
    public static final String OPEN_ERROR = "Error al abrir el archivo";//Metodo Open
    public static final String FILE_NOT_FOUND = "Archivo no encontrado";
    public static final String CASILLAS_VACIAS="Error al verificar casillas vacías";//almenosUnaCasillaVacia
    public static final String FINALIZADO="Error al verificar si el juego ha finalizado"; //finalizado, finished, isFinished 
    public static final String NUEVO_JUEGO="Error al iniciar un nuevo juego"; //Nuevo juego
    public static final String ERROR_GANADOR="Error al verificar el ganador"; //GetWinner
    /**
     * Constructor de la clase GomokuException.
     * @param message Mensaje de error asociado a la excepción.
     */
    public GomokuException(String message) {
        super(message);
    }
    
}
