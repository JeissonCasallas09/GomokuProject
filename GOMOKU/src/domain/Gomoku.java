package domain;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Class for Gomoku
 *
 * @author Jeisson Casallas & Camilo Murcia
 * @version 5.0
 */
public class Gomoku implements Serializable{
    private HashMap<Color, Jugador> jugadores;
    
    //Tablero
    private List<Piedra> listPiedras;
    private List<Piedra> listBlackStones;
    private List<Piedra> listWhiteStones;
    private List<Casilla> listCasillas;
    private int numberOfCols;
    private int numberOfRows;
    private boolean isThereAWinner;
    private List<Integer> chainsFound;
    private Color emptyColor = new Color(153,76,0);
    private static Gomoku juego=null;
    public int tam = 15;
    private String pP ="10%";
    private String pC="10%";
    private Jugador jugador1;
    private Jugador jugador2;
    private int piedrasLimitadas;
    private static boolean ok=false;
    
    private int numPiedrasNormales;
    private int numPiedrasEspeciales;
    private int numPiedrasPesadas;
    private int numPiedrasTemporales;
    
    private int numCasillasNormales;
    private int numCasillasEspeciales;
    private int numCasillasTeleport;
    private int numCasillasExplosivas;
    private Random r = new Random();
    
    /**
     * Constructor de la clase Gomoku.
     * 
     */
    public Gomoku(){
        dibujar(tam, pP, pC);
    }
    
    /**
     * Verificar si se realiza correctamente la acción.
     * 
     * @return True si se realizo correctamente, de lo contrario false.
     */
    public boolean ok() {
    	return ok;
    }
    
    /**
     * Método para inicializar el tablero y configurar el juego.
     * 
     * @param tamTablero   Tamaño del tablero.
     * @param porPiedras   Porcentaje de piedras en el tablero.
     * @param porCasillas  Porcentaje de casillas en el tablero.
     */
    
    public void dibujar(int tamTablero, String porPiedras, String porCasillas){
        if(tamTablero>=10) {
        	ok=true;  
        	this.tam=tamTablero;this.pP=porPiedras;this.pC=porCasillas;
            jugadores = new HashMap<>();
            jugadores.put(Color.BLACK,jugador1= new Humano(Color.BLACK));
            jugadores.put(Color.WHITE,jugador2= new Humano(Color.WHITE));
            prepareArrays();
            this.numberOfCols = tamTablero;this.numberOfRows = tamTablero;

            int porcentajePiedras = parseoPorcentaje(porPiedras);
            //int porcentajeCasillas= parseoPorcentaje(porCasillas);
            int porcentajeCasillas=0;
           
            for (int row = 1; row <= tam; row++) {
                for (int col = 1; col <= tam; col++) {
                    this.listCasillas.add(new CasillaNormal(row, col));
                }
            }
            
            //porcentajeCasillas(tamTablero,porcentajeCasillas);
            //this.generarCasillasAleatorias(numCasillasNormales, "CasillaNormal");
            //this.generarCasillasAleatorias(numCasillasTeleport, "CasillaTeleport");
            //this.generarCasillasAleatorias(numCasillasExplosivas, "CasillaExplosiva");   
            
            porcentajePiedras(tamTablero,porcentajePiedras);
            this.generarPiedrasAleatorias(numPiedrasNormales, "PiedraNormal");
            this.generarPiedrasAleatorias(numPiedrasPesadas, "PiedraPesada");
            this.generarPiedrasAleatorias(numPiedrasTemporales, "PiedraTemporal"); 
                
        }
        ok = false;
    }
    
    /*
	 *genera el porcentaje para cada tipo de piedra segun el dado por el usuario.
	 * 
	 * @param tamTablero tamaño del tablero
	 * @param porcentajePiedras el porcentaje inicial que va a manejar las piedas especiales
	 */
	private void porcentajePiedras(int tamTablero,int porcentajePiedras) {
		numPiedrasNormales = (int) (tamTablero * tamTablero * (1 - (porcentajePiedras/100.0)));
		numPiedrasEspeciales = tamTablero * tamTablero - numPiedrasNormales;
		numPiedrasPesadas = (int) (numPiedrasEspeciales * 0.5);
        numPiedrasTemporales = numPiedrasEspeciales - numPiedrasPesadas;
	}
	/*
	 * Genera piedras aleatorias para cada tipo de piedra que exista.
	 * 
	 * @param cantidad representa la cantidad de piedras que se van a generar en el tablero.
	 * @param tipo representa el tipo de piedra que se va a utiliza manejada como String.
	 */
    private void generarPiedrasAleatorias(int cantidad, String tipo) {
        for (int i = 0; i < cantidad; i++) {
            int fila = randomFila(r);
            int columna = randomColumna(r);

            while (hayPiedraEn(fila, columna)) {
                fila = randomFila(r);
                columna = randomColumna(r);
            }
            Piedra piedra = null;
            if(tipo=="PiedraNormal") {
            	piedra = new PiedraNormal(fila, columna, 0);
            }
            else if(tipo=="PiedraPesada"){
            	piedra = new PiedraPesada(fila, columna, 0);
            }
            else if(tipo=="PiedraTemporal") {
            	 piedra = new PiedraTemporal(fila, columna, 0);
            }
            this.listPiedras.add(piedra);
        }
    }
        
    /*
	 *genera el porcentaje para cada tipo de casilla segun el dado por el usuario.
	 * 
	 * @param tamTablero tamaño del tablero
	 * @param porcentajeCasillas el porcentaje inicial que va a manejar las casillas especiales
	 */
	private void porcentajeCasillas(int tamTablero,int porcentajeCasillas) {
		numCasillasNormales = (int) (tamTablero * tamTablero * (1 - (porcentajeCasillas/100.0)));
		numCasillasEspeciales = tamTablero * tamTablero - numPiedrasNormales;
		numCasillasTeleport = (int) (numCasillasEspeciales * 0.5);
        numCasillasExplosivas = numCasillasEspeciales - numCasillasTeleport;
	}
	
	/*
	 * Genera piedras aleatorias para cada tipo de casilla que exista.
	 * 
	 * @param cantidad representa la cantidad de casilla que se van a generar en el tablero.
	 * @param tipo representa el tipo de piedra que se va a utiliza manejada como String.
	 */
    private void generarCasillasAleatorias(int cantidad, String tipo) {
        for (int i = 1; i <= cantidad; i++) {
            int fila = randomFila(r);
            int columna = randomColumna(r);

            while (hayCasillaEn(fila, columna)) {
                fila = randomFila(r);
                columna = randomColumna(r);
            }
            Casilla c = null;
            if(tipo=="CasillaNormal") {
            	c = new CasillaNormal(fila, columna);
            }
            else if(tipo=="CasillaTeleport"){
            	c = new CasillaTeleport(fila, columna);
            }
            else if(tipo=="CasillaExplosiva") {
            	 c = new CasillaExplosiva(fila, columna);
            }
            this.listCasillas.add(c);
        }
    }

    /**
     * Método estático para obtener una instancia única de Gomoku.
     * 
     * @return Instancia de Gomoku.
     */
    
    public static Gomoku getGomoku2(){
        if(juego==null){
            juego = new Gomoku();
        }
        return juego;
    }
    
    /**
     * Método para establecer el tamaño del tablero.
     * 
     * @param t Tamaño del tablero.
     */
    
    public void setTam(int t){
        this.tam = t;
    }
    
    /**
     * Método para establecer el porcentaje de piedras en el tablero.
     * 
     * @param porP Porcentaje de piedras.
     */
    
    public void setpP(String porP){
        this.pP = porP;
    }
    
    /**
     * Método para establecer el porcentaje de casillas en el tablero.
     * 
     * @param porC Porcentaje de casillas.
     */
    
    public void setpC(String porC){
        this.pC = porC;
    }
    
    /**
     * Método para preparar los arrays y configurar el estado inicial del juego.
     */
    
    private void prepareArrays(){
        this.isThereAWinner = false;
        this.listPiedras = new ArrayList<Piedra>();
        this.listCasillas = new ArrayList<Casilla>();
        this.listBlackStones = new ArrayList<Piedra>();
        this.listWhiteStones = new ArrayList<Piedra>();
        this.chainsFound = new ArrayList<Integer>();
        jugador1.setPL(piedrasLimitadas);
        jugador2.setPL(piedrasLimitadas);
    }
    
    /**
     * Método para verificar si hay una piedra en una posición específica.
     * 
     * @param fila    Fila de la posición.
     * @param columna Columna de la posición.
     * @return true si hay una piedra en la posición, false de lo contrario.
     */
    public boolean hayPiedraEn(int fila, int columna) {
        for (Piedra piedra : this.listPiedras) {
            if (piedra.getPositionX() == fila && piedra.getPositionY() == columna) {
            return true;
            }
        }
        return false;
    }
    
    /**
     * Método para verificar si hay una casilla en una posición específica.
     * 
     * @param fila    Fila de la posición.
     * @param columna Columna de la posición.
     * @return true si hay una casila en la posición, false de lo contrario.
     */
    public boolean hayCasillaEn(int fila, int columna) {
        for (Casilla c : this.listCasillas) {
            if (c.getPositionX() == fila && c.getPositionY() == columna) {
            return true;
            }
        }
        return false;
    }
    
    /**
     * Método para generar un número de fila aleatorio.
     * 
     * @param random Generador de números aleatorios.
     * @return Número de fila aleatorio.
     */
    
    private int randomFila(Random random) {
        return 1 + random.nextInt(numberOfRows);
    }
    
    /**
     * Método para generar un número de columna aleatorio.
     * 
     * @param random Generador de números aleatorios.
     * @return Número de columna aleatorio.
     */
    
    private int randomColumna(Random random) {
        return 1 + random.nextInt(numberOfCols);
    }
    
    /**
     * Método para convertir un porcentaje representado como cadena a un entero.
     * 
     * @param porcentaje Porcentaje representado como cadena.
     * @return Valor entero del porcentaje.
     */
    
    private int parseoPorcentaje(String porcentaje){
        // Eliminar el símbolo "%"
        String soloNumeroStr = porcentaje.replaceAll("%", "");
        int retorno = 0;
        try {
            // Convertir la cadena a un entero
            int numero = Integer.parseInt(soloNumeroStr);
            retorno = numero;
        } catch (NumberFormatException e) {
            System.out.println("No se pudo convertir la cadena a un entero.");
        }
        return retorno;
    }
    
    /**
     * Método para obtener los jugadores del juego.
     * 
     * @return HashMap con los jugadores.
     */
    public HashMap<Color, Jugador> getJugadores(){
        return this.jugadores;
    }
    
    /**
     * Método para establecer el color de un jugador.
     * 
     * @param name  Color a establecer.
     * @param selectedColor  Jugador al que se le asignará el color.
     */
    public void setColor(Color name, Jugador selectedColor){
        if(jugadores.get(Color.BLACK) == this.jugador1){
            this.jugadores.remove(Color.BLACK);
            this.jugadores.put(name, jugador1);
            jugador1.setPL(piedrasLimitadas);
        }else if(jugadores.get(Color.WHITE) == this.jugador2){
            this.jugadores.remove(Color.WHITE);
            this.jugadores.put(name, jugador2);
            jugador2.setPL(piedrasLimitadas);
        }
    }
    
    public String getNombre(Color color) {
    	return this.jugadores.get(color).getNombre();
    }
    
    /**
     * Método para verificar si hay una cadena de cinco piedras del mismo color en el tablero.
     * 
     * @param color Color de las piedras a buscar.
     * @return true si hay una cadena de cinco piedras del mismo color, false de lo contrario.
     */
    
    public boolean isThereAFiveChain(Color color){
        this.collectHorizontalChains(color);
        this.collectVerticalChains(color);
        this.collectDiagonallyRightChains(color);
        this.collectDiagonallyLeftChains(color);
            
        if(this.chainsFound.size()>0){
            if (Collections.max(this.chainsFound) == 5){
                this.isThereAWinner = true;

            }else{
                //
            }
        }
        this.chainsFound.clear();
        return this.isThereAWinner;
    }
    
    /**
     * Método privado para recolectar cadenas horizontales del mismo color.
     * 
     * @param color Color de las piedras a buscar.
     */
    
    private void collectHorizontalChains(Color color){
        int count = 0;
        for (int row = 1; row <= this.numberOfRows; row++) {
            for (int column = 1; column <= this.numberOfCols+1 ; column++){
                Piedra p = this.getPiedraRowCol(row,column);
                if (p != null && p.getStoneColor().equals(color) && count < 5)
                    count += p.getPuntuacion();
                else if(count >= 3){
                    this.chainsFound.add(count);
                    count = 0;
                }else{
                    count = 0;
                }
            }
        }
    }
    
    /**
     * Método privado para recolectar cadenas verticales del mismo color.
     * 
     * @param color Color de las piedras a buscar.
     */
    
    private void collectVerticalChains(Color color){
        int count = 0;
        for (int row = 1; row <= this.numberOfRows; row++) {
            for (int column = 0; column <= this.numberOfCols +1; column++){
                Piedra p = this.getPiedraRowCol(column,row);
                if (p != null && p.getStoneColor().equals(color) && count < 5){
                    count += p.getPuntuacion();
                }
                else if(count >= 3){
                    this.chainsFound.add(count);
                    count = 0;
                }else{
                    count = 0;
                }
            }
        }
    }

    /**
     * Método privado para recolectar cadenas diagonales hacia la derecha del mismo color.
     * 
     * @param color Color de las piedras a buscar.
     */
    private void collectDiagonallyRightChains(Color color){
        int count = 1;
        for (int row = 1; row < this.numberOfRows; row++) {
            int rowAux = row;
            for (int column = 0; column <= this.numberOfCols +1; column++) {
                Piedra p = this.getPiedraRowCol(column,row);
                if (rowAux <= this.numberOfRows){
                    if (p != null && this.getPiedraRowCol(rowAux,column).getStoneColor().equals(color)&& count < 5){
                        count += p.getPuntuacion();
                        rowAux++;
                    }else if(count >= 3){
                        this.chainsFound.add(count);
                        count = 0;
                    }else{
                        rowAux = row;
                        count = 0;
                    }
                }
            }
            for (int column = this.numberOfCols; column >= 0; column--) {
                Piedra p = this.getPiedraRowCol(column,row);
                if (rowAux <= this.numberOfRows){
                    if (p != null && this.getPiedraRowCol(rowAux,column).getStoneColor().equals(color) && count < 5){
                        count += p.getPuntuacion();
                        rowAux++;
                    }else if(count >= 3){
                        this.chainsFound.add(count);
                        count = 0;
                    }else{
                        rowAux = row;
                        count = 0;
                    }
                }
            }
        }
    }
    
    /**
     * Método privado para recolectar cadenas diagonales hacia la izquierda del mismo color.
     * 
     * @param color Color de las piedras a buscar.
     */

    private void collectDiagonallyLeftChains(Color color){
        int count = 1;
        int rowAux = 0;
        for (int row = 1; row <= this.numberOfRows; row++){
            rowAux = row;
            for (int column = 0; column <= this.numberOfCols +1; column++) {
                Piedra p = this.getPiedraRowCol(column,row);
                if (rowAux <= this.numberOfRows){
                    if (p!= null && this.getPiedraRowCol(rowAux,column).getStoneColor().equals(color) && count < 5){
                        count += p.getPuntuacion();
                        rowAux++;
                    }else if(count >= 3){
                        chainsFound.add(count);
                        count = 0;
                    }else{
                        isThereAWinner = false;
                        rowAux = row;
                        count = 0;
                    }
                }
            }
            for (int column = this.numberOfCols; column >= 0; column--) {
                Piedra p = this.getPiedraRowCol(column,row);
                if (rowAux <= this.numberOfRows){
                    if (p != null && this.getPiedraRowCol(rowAux,column).getStoneColor().equals(color) && count < 5){
                        count += p.getPuntuacion();
                        rowAux++;
                    }else if(count >= 3){
                        this.chainsFound.add(count);
                        count = 0;
                    }else{
                        rowAux = row;
                        count = 0;
                    }
                }
            }
        }
    }
    
    /**
     * Método para obtener una piedra en una posición específica del tablero.
     * 
     * @param row Fila de la piedra.
     * @param col Columna de la piedra.
     * @return Piedra en la posición dada o null si no hay ninguna piedra en esa posición.
     */

    public Piedra getPiedraRowCol(int row, int col){
        for (Piedra p : listPiedras) {
            if (p.getPositionX() == row && p.getPositionY() == col)
                return p;
            }
        return null;
    }
    
    /**
     * Método para realizar un movimiento en el tablero.
     *
     * @param row    Fila en la que se realizará el movimiento.
     * @param col    Columna en la que se realizará el movimiento.
     * @param turno  Turno del jugador que realiza el movimiento ("Black" o "White").
     * @param color  Color de la piedra que se colocará en la casilla.
     * @return true si el movimiento fue válido y se realizó con éxito, false de lo contrario.
     */
    
    public boolean place(int row, int col, String turno, Color color){		
    	boolean bandera = false;
    	if(movimientoCasillaValido(row, col)) {
        	Casilla c = this.getCasillaRowCol(row, col);
            Piedra p = c.ActCasilla(row, col, color, this);
            
            agregarPiedrasAL(turno, p,color);
        	bandera = true;
        	ok = true;
        }
        else {
        	ok = false;
        }
        return bandera;   
    }
    
    /**
     * Método privado para agregar piedras a la lista de piedras del jugador correspondiente.
     *
     * @param turno Turno del jugador que realiza el movimiento ("Black" o "White").
     * @param p     Piedra que se agregará a la lista.
     */
    
    private void agregarPiedrasAL(String turno,Piedra p,Color color){
    	
    	if(turno.equals("Black")){
    		//jugadores.get(color).piedrasLimitadas();
    		piedrasLimitadas--;
            this.listBlackStones.add(p);
            if(!(p instanceof PiedraNormal)) {
            	setPuntuacion(color,100);
            }
            for(Piedra piedra:listBlackStones){
            	if(piedra.aumentarTurno()){
                    Casilla casilla = this.getCasillaRowCol(piedra.getPositionX(), piedra.getPositionY());
                    casilla.limpiarCasilla();
                }
            }
        }if(turno.equals("White")){
            this.listWhiteStones.add(p);
            if(!(p instanceof PiedraNormal)) {
            	setPuntuacion(color,100);
            }
            for(Piedra piedra:listWhiteStones){
            	if(piedra.aumentarTurno()){
                    Casilla casilla = this.getCasillaRowCol(piedra.getPositionX(), piedra.getPositionY());
                    casilla.limpiarCasilla();
                }
            }
        }
    }


	/**
	 * Método para limpiar todo el tablero.
	 */
    public void cleanTheBoard() {
        for (Piedra p : this.listPiedras){
            p.limpiarPiedra();
        }       
        for(Casilla c : this.listCasillas){
            c.limpiarCasilla();
        }
        this.listBlackStones.clear();
        this.listWhiteStones.clear();
        this.chainsFound.clear();
        this.isThereAWinner = false;
    }
     
    //Conexion con piedra
    
    /**
     * Verifica si hay al menos una piedra vacía en el tablero.
     *
     * @return True si hay al menos una piedra vacía, False si no hay piedras vacías.
     */
    public boolean almenosUnaPiedraEmpty(){
        boolean ban = false;
        for (Piedra p : this.listPiedras){
            if(p.getStoneColor().equals(emptyColor)){
                ban = true;
            }
        }
        return ban;
    }
    
    /**
     * Limpia una piedra en la posición especificada.
     *
     * @param row Fila de la piedra a limpiar.
     * @param col Columna de la piedra a limpiar.
     */
    public void cleanOnePiedra(int row, int col) {
        Piedra p = getPiedraRowCol(row, col);
        p.limpiarPiedra();
    }
    
    /**
     * Verifica si un movimiento es válido en la posición especificada.
     *
     * @param row Fila del movimiento.
     * @param col Columna del movimiento.
     * @return True si el movimiento es válido, False si no es válido.
     */
    public boolean movimientoValido(int row, int col){
        return (this.getPiedraRowCol(row,col).getStoneColor().equals(emptyColor));
    }
    
    /**
     * Obtiene la lista de todas las piedras en el tablero.
     *
     * @return Lista de piedras en el tablero.
     */
    public List<Piedra>getListPiedras() {
        return listPiedras;
    }


    //Casillas
    
  
    
    /**
     * Obtiene la lista de todas las casillas en el tablero.
     *
     * @return Lista de casillas en el tablero.
     */
    public List<Casilla> getListCasillas(){
        return this.listCasillas;
    }
    
    
    /**
     * Obtiene la casilla en la posición especificada.
     *
     * @param row Fila de la casilla.
     * @param col Columna de la casilla.
     * @return Casilla en la posición especificada.
     */
    public Casilla getCasillaRowCol(int row, int col){
        for (Casilla c : listCasillas) {
            if (c.getPositionX() == row && c.getPositionY() == col)
                return c;
            }
        return null;
    }
     
    
    /**
     * Verifica si un movimiento de casilla es válido en la posición especificada.
     *
     * @param row Fila del movimiento de casilla.
     * @param col Columna del movimiento de casilla.
     * @return True si el movimiento de casilla es válido, False si no es válido.
     */
    
    public boolean movimientoCasillaValido(int row, int col){
        return (!this.getCasillaRowCol(row,col).getOcupada());
    }
    
    /**
     * Verifica si hay al menos una casilla vacía en el tablero.
     *
     * @return True si hay al menos una casilla vacía, False si no hay casillas vacías.
     */
    public boolean almenosUnaCasillaVacia(){
        boolean ban = false;
        for (Casilla c : this.listCasillas){
            if(!c.getOcupada()){
                ban = true;
            }
        }
        return ban;
    }
    
    
    /**
     * Verifica si el juego ha finalizado, es decir, no hay casillas vacías y no hay piedras vacías.
     *
     * @return True si el juego ha finalizado, False si aún hay casillas vacías o hay piedras vacías.
     */
    public boolean finished() {
    	boolean ban = false;
    	if (finalizado() && isFinished()){
    		ban = true;
    	}
    	return ban;
    }
    
    public void setPiedrasLimitadas(int p) {
    	this.piedrasLimitadas = p;
    }
    
    public int getPiedrasLimitadas() {
    	return this.piedrasLimitadas;
    }
    
    /**
	 * Verifica si el juego ha finalizado, es decir, no hay casillas vacías.
	 *
	 * @return True si el juego ha finalizado, False si aún hay casillas vacías.
	 */
    public boolean finalizado(){
        return !almenosUnaCasillaVacia();
    }
    
    //Juego
    

	/**
	 * Verifica si el juego ha finalizado, es decir, no hay piedras vacías.
	 *
	 * @return True si el juego ha finalizado, False si aún hay piedras vacías.
	 */
    public boolean isFinished(){
        return !almenosUnaPiedraEmpty();
    }
    
    /**
     * Inicia un nuevo juego limpiando el tablero.
     */
    public void nuevoJuego(){
        cleanTheBoard();
    }
    
    /**
     * Verifica si hay un ganador del juego para un color específico.
     *
     * @param color Color del jugador.
     * @return True si hay un ganador, False si no hay un ganador.
     */
    public boolean getWinner(Color color){
    	boolean winner = false;
    	ok=false;
        winner =false;
        if(getScore(color)==1000) {
    		winner=true;
    		ok=true;
    	}else if(piedrasLimitadas == 0){
    		winner = true;
    		ok = true;
    	}else {
    		winner= this.isThereAFiveChain(color);
    		ok=true;
    	}
    	return winner;
    }
    
    
    //Jugador
    
    /**
     * Carga un jugador en función del nombre (color).
     *
     * @param nombre Color del jugador.
     * @return Jugador correspondiente al color especificado.
     */
    
    public Jugador cargarJugador(Color nombre) {
    	Jugador j= jugadores.get(nombre);
        //System.out.println(nombre.toString());
    	return j;
    }
    
    /**
     * Establece la puntuación para un jugador en función del color.
     *
     * @param nombre Color del jugador.
     * @param points Puntos a incrementar.
     */
    
    public void setPuntuacion(Color nombre,int points) {
    	cargarJugador(nombre).increaseScore(points);
    }
    
    /**
     * Obtiene la puntuación de un jugador en función del color.
     *
     * @param nombre Color del jugador.
     * @return Puntuación del jugador.
     */
    public int getScore(Color nombre){
    	Jugador j = cargarJugador(nombre);
    	int score = j.getScore();
    	return score;
    }
    
    /**
     * Método para guardar una instancia de Gomoku en un archivo.
     *
     * @param file El archivo en el que se va a guardar la instancia.
     * @throws GomokuException Si hay un error durante la operación de guardado.
     */
    public void save(File file) throws GomokuException {
    	ok=false;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
            System.out.println("Gomoku guardado exitosamente.");
            ok=true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new GomokuException(GomokuException.SAVE_ERROR);
        }
    }

    /**
     * Método para abrir una instancia de Gomoku desde un archivo.
     *
     * @param file El archivo desde el que se va a abrir la instancia.
     * @return La instancia de Gomoku cargada desde el archivo.
     * @throws GomokuException Si hay un error durante la operación de apertura.
     */
    public static Gomoku open(File file) throws GomokuException {
        ok=false;
    	Gomoku instancia = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            instancia = (Gomoku) ois.readObject();
            System.out.println("Gomoku abierto exitosamente.");
            ok=true;
        } catch (IOException | ClassNotFoundException e) {
            throw new GomokuException(GomokuException.OPEN_ERROR);
        }
        return instancia;
    }

}
