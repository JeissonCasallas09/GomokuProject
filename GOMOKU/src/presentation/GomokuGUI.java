package presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//import domain.Piedra;
import domain.*;

/**
 * Class of GomokuGUI
 * @author Camilo Murcia Jeisson Casallas
 * Version 1.0 (16/12/23)
 */
public class GomokuGUI extends JFrame {
    //Gomoku
    private Gomoku gomoku;

    //Principal
    private JPanel mainMenu;
    private JLabel jLabel1;
    //MENU
    private JLabel creacion;
    private JButton nuevoJuego;
    private JButton reanudarP;
    private JButton configuracion;
    //Modo de juego
    private JPanel newGame;
    //Jugador vs IA
    private JPanel aiConfigPanel;
    //Jugador vs Jugador
    private JPanel playerConfigPanel;
    //configuracion
    private JPanel settings;
    //Jugadores
    private JTextField textField1= new JTextField("Jugador 1",10);
    private JTextField textField2 = new JTextField("Maquina",10);
    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;
    private JButton colorFicha;
    private JButton colorFicha2;
    private ArrayList<Color> selectedColors;
    private HashMap<Color, Jugador> jugadores;
    //tipo de juego
    private JPanel gameMode;
    //Panel del tablero
    private JPanel game;
    private int tamañoTablero =15;
    private String porcentajePiedras = "10%";
    private String porcentajeCasillas ="10%";
    private JPanel gomokuGame;
    private JPanel gameOptions;
    private List<Casilla> listCasillas;
    
    //botones tablero
    private JButton save;
    private JButton finish;
    private JButton reset;
    private JPanel buttonPanel;
    private JPanel infoPanel;

    //Turnos
    private Color color1 = Color.BLACK;
    private Color color2 = Color.WHITE;
    private String nombre1;
    private String nombre2;
    private Color colorVer;
    private int contador = 1;
    private String colorTurnoJug = "White";
    private JLabel turnLabel;
    
    /**
     * Constructor privado para crear una instancia de GomokuGUI.
     * Inicializa los componentes de la interfaz de usuario, establece las propiedades de la ventana
     * y hace visible la interfaz de usuario.
     * 
     */
    private GomokuGUI(){
        prepareElements();
        prepareActions();
        setVisible(true);
    }
    
    /**
     * Prepara los elementos principales de la interfaz gráfica del menu
     * 
     */
    private void prepareElements(){
        setSize(560, 600);
        getContentPane().setBackground(new Color(255,0,0));
        setLocationRelativeTo(null);
        setResizable(false);     
        prepareElementsMenu();
        prepareLogo();
        getContentPane().add(mainMenu);
    }
    
    /**
     * Prepara el logo de Gomoku y lo agrega al menú principal, ademas del logo del ejecutable.
     * 
     */
    private void prepareLogo(){
        setTitle("Gomoku");
        setIconImage(new ImageIcon(getClass().getResource("images/icon.png")).getImage());
        ImageIcon imagen = new ImageIcon(getClass().getResource("images/logo.png"));
        jLabel1 = new JLabel(imagen);
        jLabel1.setBounds(125,15,300,150);
        mainMenu.add(jLabel1);
    }
    
    /**
     * Prepara los componentes del menú principal, como botones para iniciar un nuevo juego,
     * reanudar un juego anterior y acceder a la configuración del juego.
     */
    private void prepareElementsMenu(){
        mainMenu = new JPanel();
        mainMenu.setLayout(null);
        nuevoJuego = new JButton("Nuevo juego");
        reanudarP = new JButton("Reanudar partida");
        configuracion = new JButton("Configuracion");
        mainMenu.setBackground(new Color(255,128,0));
        nuevoJuego.setBounds(125,180,300,40);
        mainMenu.add(nuevoJuego);
        reanudarP.setBounds(125,250,300,40);
        mainMenu.add(reanudarP);
        configuracion.setBounds(125, 320, 300, 40);
        mainMenu.add(configuracion);
        creacion = new JLabel("Hecho por: Jeisson Casallas & Camilo Murcia");
        creacion.setFont(new Font("Andale Mono",1,10));
        creacion.setBounds(85,375,300,30);
        mainMenu.add(creacion);
    }
    
    /**
     * Prepara las acciones del usuario, como cerrar la ventana y responder a los clics de los botones.
     */
    
    private void prepareActions(){
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitOptions();
            }
        });
        nuevoJuego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showGameModeDialog();
            }
        });
        reanudarP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try
                {
                    resumeOption();
                }catch (GomokuException ce){
                    JOptionPane.showMessageDialog(GomokuGUI.this, ce.getMessage(), "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        configuracion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                GameSettingsDialog();
            }
        });
        
    }
    
    /**
     * Muestra un cuadro de diálogo para seleccionar el modo de juego.
     * Luego, inicia el juego según el modo seleccionado.
     */
    
    private void showGameModeDialog() {
        gameMode = new JPanel();
        JLabel label = new JLabel("Selecciona el tipo de juego:");
        JComboBox<String> GameModeBox = new JComboBox<>(new String[]{"Modo Normal","Modo limite de tiempo","Modo Piedras limitadas"});
        gameMode.add(label);
        gameMode.add(GameModeBox);
        int result = JOptionPane.showOptionDialog(this, gameMode, "Modo de Juego",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (result == JOptionPane.OK_OPTION) {
            String typeSelected = (String) GameModeBox.getSelectedItem();
            JOptionPane.showMessageDialog(this, "Modo seleccionado: " + typeSelected);
            if(typeSelected.equals("Modo Normal")){
                normalModeGomoku();
                newGameModeDialog();
            }else if(typeSelected.equals("Modo limite de tiempo")){
                quickModeGomoku();
                newGameModeDialog();
            }else if(typeSelected.equals("Modo Piedras limitadas")){
                limitModeGomoku();
                
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Operación cancelada");
        }
    }
    
    /**
     * Establece el modo de juego normal para Gomoku, crea una nueva instancia de Gomoku
     * y prepara los elementos del tablero.
     */
    
    private void normalModeGomoku(){
        this.gomoku = new Gomoku();
        this.gomoku.dibujar(tamañoTablero, porcentajePiedras, porcentajeCasillas);
        prepareElementsBoard();
        gomoku.setPiedrasLimitadas(1000000);
    }
    
    /**
     * Establece el modo de juego rápido para Gomoku, crea una nueva instancia de Gomoku
     * y prepara los elementos del tablero.
     */
    private void quickModeGomoku(){
        this.gomoku = new Gomoku();
        this.gomoku.dibujar(tamañoTablero, porcentajePiedras, porcentajeCasillas);
        gomoku.setPiedrasLimitadas(1000000);
        prepareElementsBoard();
    }
    
    /**
     * Establece el modo de juego piedras limitadas para Gomoku, crea una nueva instancia de Gomoku
     * y prepara los elementos del tablero.
     */
    private void limitModeGomoku(){
    	newGame = new JPanel();
    	JLabel cantP = new JLabel("Limite de piedras (Minimo 18): ");
    	TextField tf = new TextField();
    	newGame.add(cantP);
        newGame.add(tf);
        
        int result = JOptionPane.showOptionDialog(this, newGame, "Cantidad de piedras",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        int cant = Integer.parseInt(tf.getText());
        if(cant>=18 && cant<(tamañoTablero*tamañoTablero)/2) {
  
        	if (result == JOptionPane.OK_OPTION ) {
                JOptionPane.showMessageDialog(this, "Cantidad de piedras por jugador: " + cant);
                this.gomoku = new Gomoku();
                this.gomoku.dibujar(tamañoTablero, "0%", "0%");
                this.gomoku.setPiedrasLimitadas(cant);
                prepareElementsBoardLimited();
                newGameModeDialog();
            } else {
                JOptionPane.showMessageDialog(this, "Operación cancelada");
            }
        }else {
        	JOptionPane.showMessageDialog(this, "Numero invalido");
        	showGameModeDialog();
        }
        
    }
    
    /**
     * Muestra un cuadro de diálogo para seleccionar el modo de juego (Jugador vs IA o Jugador vs Jugador)
     * y realiza la configuración correspondiente según la selección.
     */
    private void newGameModeDialog() {
        // Crear un panel con componentes personalizados
        newGame = new JPanel();
        JLabel label = new JLabel("Selecciona el modo de juego:");
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Jugador vs IA", "Jugador vs Jugador"});
        newGame.add(label);
        newGame.add(comboBox);

        int result = JOptionPane.showOptionDialog(this, newGame, "Modo de Juego",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        
        if (result == JOptionPane.OK_OPTION) {
            String selectedMode = (String) comboBox.getSelectedItem();
            JOptionPane.showMessageDialog(this, "Modo seleccionado: " + selectedMode);
            
            if(selectedMode.equals("Jugador vs IA")){
                showAIConfiguration();
            }else if(selectedMode.equals("Jugador vs Jugador")){
                showPlayerConfiguration();
            }
            
        } else {
            JOptionPane.showMessageDialog(this, "Operación cancelada");
        }
    }
    
    /**
     * Muestra un cuadro de diálogo para configurar el juego en el modo "Jugador vs IA".
     */
    private void showAIConfiguration() {
        aiConfigPanel = new JPanel();
        jugadores = gomoku.getJugadores();
        //aiConfigPanel.add(new JLabel("Nuevo juego"));
        textField1 = new JTextField("Jugador 1",10);
        colorFicha= new JButton("     ");
        aiConfigPanel.add(new JLabel());
        this.nombre1 = textField1.getText();jugadores.get(color1).setNombre(nombre1);
        this.nombre2 = "Maquina";jugadores.get(color2).setNombre(nombre2);
        aiConfigPanel.add(textField1);
        aiConfigPanel.add(new JLabel("Color ficha: "));
        aiConfigPanel.add(colorFicha);
        aiConfigPanel.add(new JLabel("Dificultad Maquina: "));
        JComboBox<String> DificultBox = new JComboBox<>(new String[]{"Agresiva", "Experta","Miedosa"});
        aiConfigPanel.add(DificultBox);
        aiConfigPanel.add(new JLabel("Color Ficha:"));
        colorFicha2= new JButton("     ");
        aiConfigPanel.add(colorFicha2);
        colorFicha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
               changeColor();
            }
        });
        colorFicha2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                changeColor();
            }
        });
        int result = JOptionPane.showOptionDialog(this, aiConfigPanel, "Configuración jugador",JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        this.nombre1 = textField1.getText();
        this.nombre2 = "Maquina";
        if (result == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(this, "Configuración Exitosa");
        } else {
            JOptionPane.showMessageDialog(this, "Operación cancelada");
        }
    }
    
    /**
     * Muestra un cuadro de diálogo para configurar el juego en el modo "Jugador vs Jugador".
     * 
     */
    
    private void showPlayerConfiguration() {
        playerConfigPanel = new JPanel();
        jugadores = gomoku.getJugadores();
        //playerConfigPanel.add(new JLabel("Nuevo juego"));
        textField1 = new JTextField("Jugador 1: ");        
        colorFicha= new JButton();
        playerConfigPanel.add(new JLabel("Jugador 1: ", 10));
        this.nombre1 = textField1.getText();jugadores.get(color1).setNombre(nombre1);
        playerConfigPanel.add(textField1);
        playerConfigPanel.add(new JLabel("Color ficha: "));
        playerConfigPanel.add(colorFicha);
        
        textField2 = new JTextField("Jugador 2:",10);
        colorFicha2= new JButton();
        playerConfigPanel.add(new JLabel("Jugador 2: "));
        this.nombre2 = textField2.getText();jugadores.get(color2).setNombre(nombre2);
        playerConfigPanel.add(textField2);
        playerConfigPanel.add(new JLabel("Color ficha: "));
        playerConfigPanel.add(colorFicha2);
        
        colorFicha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
               changeColor();
            }
        });
        colorFicha2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
               changeColor();
            }
        });
        int result = JOptionPane.showOptionDialog(this, playerConfigPanel, "Configuración jugador",JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        this.nombre1 = textField1.getText();
        this.nombre2 = textField2.getText();
        if (result == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(this, "Configuración Exitosa");
        } else {
            JOptionPane.showMessageDialog(this, "Operación cancelada");
        }
    }
    
    /**
     * 
     * Muestra un cuadro de diálogo para configurar ajustes generales del juego, como el tamaño del tablero,
     * porcentaje de casillas especiales y porcentaje de piedras especiales.
     * 
     */
    private void GameSettingsDialog() {
        settings = new JPanel();
        JTextField textField3 = new JTextField(10);
        JComboBox<String> comboBoxPiedras = new JComboBox<>(new String[]{"0%","10%", "20%","30%","40%","50%","60%","70%","80%","90%","100%"});
        JComboBox<String> comboBoxCasillas = new JComboBox<>(new String[]{"0%","10%", "20%","30%","40%","50%","60%","70%","80%","90%","100%"});
        settings.add(new JLabel("Tamaño tablero:"));
        settings.add(textField3);
        settings.add(new JLabel("Casillas especiales:"));
        settings.add(comboBoxCasillas);
        settings.add(new JLabel("Piedras especiales:"));
        settings.add(comboBoxPiedras);
        int result = JOptionPane.showOptionDialog(this,settings, "Configuración",JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int newSize = Integer.parseInt(textField3.getText());
                if (newSize >= 10) {
                    tamañoTablero = newSize;
                    String value1 = textField3.getText();
                    String value2 = (String) comboBoxPiedras.getSelectedItem();
                    this.porcentajePiedras = value2;
                    String value3 = (String) comboBoxCasillas.getSelectedItem();
                    this.porcentajeCasillas = value3;
                    JOptionPane.showMessageDialog(this, "Tamaño tablero: " + value1 + "\n Casillas especiales: " + value3+ "\n Piedras especiales: " + value2);
                } else {
                    JOptionPane.showMessageDialog(this, "Ingrese un tamaño de tablero válido (mayor que 10).",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ingrese un número válido para el tamaño del tablero.",
                         "Error", JOptionPane.ERROR_MESSAGE);
            } 
        }
    }
    
    /**
     * Cambia el color de las fichas de los jugadores.
     * 
     */
    public void changeColor(){
        this.jugadores = gomoku.getJugadores();
        jugadores.get(color1).setNombre(nombre1);jugadores.get(color2).setNombre(nombre2);
        selectedColors = new ArrayList<>();
        if(this.color1 == Color.BLACK){
            Color selectedColor = selectColor();
            gomoku.setColor(selectedColor, jugadores.get(color1).setColor(color1));
            this.color1 = selectedColor;
            selectedColors.add(selectedColor);
            this.jugadores = gomoku.getJugadores();
        }else if(color2 == Color.WHITE){
            Color selectedColor = selectColor();
            gomoku.setColor(selectedColor, jugadores.get(color2).setColor(color1));
            this.color2 = selectedColor; 
            selectedColors.add(selectedColor);
            this.jugadores = gomoku.getJugadores();
        }
        this.jugadores = gomoku.getJugadores();
    }
   
    /**
     * Permite al usuario seleccionar un color utilizando el JColorChooser.
     * Evita la selección de colores nulos o colores ya en uso.
     *
     * @return El color seleccionado por el usuario.
     */
    
    private Color selectColor() {
        Color selectedColor = null;
        while (selectedColor == null || selectedColors.contains(selectedColor)) {
            selectedColor = JColorChooser.showDialog(null, 
                "Elige el color para tus piedras: ", null);
            if (selectedColor == null) {
                JOptionPane.showMessageDialog(this, "No puedes seleccionar un color nulo", "Alerta", JOptionPane.ERROR_MESSAGE);
            }else if(selectedColors.contains(selectedColor)) {
                JOptionPane.showMessageDialog(this, "No puedes seleccionar un color ya en uso", "Alerta", JOptionPane.WARNING_MESSAGE);
            }
        }
        return selectedColor;
    }
    
    /**
     * Muestra un cuadro de diálogo de confirmación al intentar cerrar la aplicación,
     * permitiendo al usuario decidir si desea cerrarla o no.
     */
    
    private void exitOptions() {
        int respuesta = JOptionPane.showInternalConfirmDialog(null, "Desea cerrar la aplicación?",
                "Confirmacion de cierre", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    
    /**
     * Crea y devuelve un panel que representa el tablero del juego.
     *
     * @return El panel que contiene las casillas del tablero del juego.
     */
    private JPanel drawBoard(){
        listCasillas = gomoku.getListCasillas();
        this.game = new JPanel();
        this.game.setBounds(10, 20, (8 * tamañoTablero) + 100, (8 * tamañoTablero) + 100);
        this.game.setBackground(Color.BLACK);
        this.game.setLayout(new GridLayout(tamañoTablero, tamañoTablero));
        this.game.setPreferredSize(new Dimension(300, 300));
        for(Casilla c : listCasillas){
            this.game.add(c);
            c.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clickOnButton2(c);
                }
            });
        }
        return game;
    }
    
    /**
     * Maneja el evento de clic en una casilla del tablero del juego.
     * Realiza las acciones correspondientes al turno del jugador y verifica si hay un ganador.
     *
     * @param boton La casilla en la que se hizo clic.
     * @throws GomokuException 
     * @throws HeadlessException 
     */
    
    private void clickOnButton2(Casilla boton) { 
    	boolean movValido = false;
    	if(contador%2 == 1){
    		this.colorTurnoJug = "White";
            refreshTurno();
            movValido = gomoku.place(boton.getPositionX(), boton.getPositionY(),this.colorTurnoJug, color1);
            this.colorVer = color1;
        }
        else{   
        	this.colorTurnoJug = "Black";refreshTurno();        
            movValido = gomoku.place(boton.getPositionX(), boton.getPositionY(),this.colorTurnoJug, color2);
            this.colorVer = color2;
        }
        if(movValido){
            ++contador;
        }else{
        	JOptionPane.showMessageDialog(null, String.format("Movimiento Invalido, intenta nuevamente."), "Informacion", JOptionPane.INFORMATION_MESSAGE);
        }
        if(gomoku.getWinner(colorVer)){
        	JOptionPane.showMessageDialog(null, String.format("El ganador es: %s ", jugadores.get(colorVer).getNombre()),"Informacion", JOptionPane.INFORMATION_MESSAGE);
            restartGame();
        }
        if (gomoku.finished()){
        	JOptionPane.showMessageDialog(null, String.format("El juego queda empatado! \n No mas movimientos disponibles"), "Informacion", JOptionPane.INFORMATION_MESSAGE);
        	restartGame();
        }
    }


    /**
     * Reinicia el juego a su estado inicial, permitiendo a los jugadores volver a jugar.
     * 
     */
    private void restartGame(){
        int resp = JOptionPane.showConfirmDialog(null,"Desea jugar nuevamente?", "Informacion", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION){
            gomoku.nuevoJuego();
            this.contador = 1;
            this.colorTurnoJug = "White";
            refreshTurno();
            restartPuntuacion();
            HashMap<Color, Jugador> jugadoresMap = gomoku.getJugadores();
            for (Jugador jugador : jugadoresMap.values()) {
                jugador.resetScore();
            }
        }
        else{
            this.dispose();
        }
    }
    
    /**
     * Actualiza la etiqueta de turno y los puntajes de los jugadores en el panel de información del juego.
     * 
     */
    private void refreshTurno(){
    	String nombrever = nombre1;
    	if(colorTurnoJug == "White") {
    		nombrever = nombre1;
    	}else{
    		nombrever = nombre2;		
    	}
        turnLabel.setText("Turno: " + nombrever);
        player1ScoreLabel.setText(this.nombre1 +": "+ gomoku.getScore(color1));
        player2ScoreLabel.setText(this.nombre2+  ": "+gomoku.getScore(color2));
        //cantPiedras.setText("Cantidad de piedras: " + (gomoku.getPiedrasLimitadas()-1));
    }
    /**
     * Reinicia la puntuacion
     */
    private void restartPuntuacion(){
        turnLabel.setText("Turno: " + this.colorTurnoJug);
        player1ScoreLabel.setText(this.nombre1 + ": " + 0);
        player2ScoreLabel.setText(this.nombre2 + ": " + 0);
    }
    
    /**
     * Prepara los elementos del tablero y muestra la interfaz gráfica del juego limited
     * 
     */
    public void prepareElementsBoardLimited() {
        mainMenu.setVisible(false);
        gomokuGame = new JPanel();
        prepareLogo();
        this.add(gomokuGame);
        prepareElementsGameLimited();
        gomokuGame.setVisible(true);
    }
    
    /**
     * Prepara los elementos del tablero y muestra la interfaz gráfica del juego.
     * 
     */
    public void prepareElementsBoard() {
        mainMenu.setVisible(false);
        gomokuGame = new JPanel();
        prepareLogo();
        this.add(gomokuGame);
        prepareElementsGame();
        gomokuGame.setVisible(true);
    }
    
    /**
     * Prepara los elementos visuales y de diseño para la interfaz gráfica del juego.
     * 
     */
    
    public void prepareElementsGame() {
        gomokuGame.setSize(this.getWidth(), this.getHeight());
        gomokuGame.setBackground(new Color(255,128,0));
        game = drawBoard();
        JPanel buttonContainer= addGameButtons();
        JPanel informationContainer=addGameInformation();
        gomokuGame.add(jLabel1);
        gomokuGame.add(informationContainer);
        gomokuGame.add(game);
        gomokuGame.add(buttonContainer);
    }
    
    /**
     * Prepara los elementos visuales y de diseño para la interfaz gráfica del juego limited
     * 
     */
    
    public void prepareElementsGameLimited() {
        gomokuGame.setSize(this.getWidth(), this.getHeight());
        gomokuGame.setBackground(new Color(255,128,0));
        game = drawBoard();
        JPanel buttonContainer= addGameButtons();
        JPanel informationContainer=addGameInformationLimited();
        gomokuGame.add(jLabel1);
        gomokuGame.add(informationContainer);
        gomokuGame.add(game);
        gomokuGame.add(buttonContainer);
    }
    
    /**
     * Agrega un panel que muestra información sobre el juego, como el tiempo, los puntajes y el turno actual.
     *
     * @return El panel que contiene la información del juego.
     */
    private JLabel cantPiedras;
    private JPanel addGameInformationLimited(){
        infoPanel = new JPanel();
        JLabel timeLabel = new JLabel("Tiempo: pendiente");
        player1ScoreLabel = new JLabel("Jugador 1: 0");
        player2ScoreLabel = new JLabel("Jugador 2: 0");
        cantPiedras = new JLabel("Cantidad de piedras: " + gomoku.getPiedrasLimitadas());
        turnLabel = new JLabel("Turno: " + this.colorTurnoJug);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(timeLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(player1ScoreLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(player2ScoreLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(turnLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(cantPiedras);
        

        return infoPanel;
    }
    
    /**
     * Agrega un panel que muestra información sobre el juego, como el tiempo, los puntajes y el turno actual.
     *
     * @return El panel que contiene la información del juego.
     */

    private JPanel addGameInformation(){
        infoPanel = new JPanel();
        JLabel timeLabel = new JLabel("Tiempo: pendiente");
        player1ScoreLabel = new JLabel("Jugador 1: 0");
        player2ScoreLabel = new JLabel("Jugador 2: 0");
        turnLabel = new JLabel("Turno: " + this.colorTurnoJug);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(timeLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(player1ScoreLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(player2ScoreLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(turnLabel);

        return infoPanel;
    }
    
    /**
     * Agrega un panel con botones de juego, como "Terminar juego", "Reiniciar juego" y "Guardar juego".
     *
     * @return El panel que contiene los botones del juego.
     */
    
    private JPanel addGameButtons(){
        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255,128,0));
        finish= new JButton("Terminar juego");
        reset = new JButton("Reiniciar juego");
        save = new JButton("Guardar juego");
        buttonPanel.add(finish);
        buttonPanel.add(reset);
        buttonPanel.add(save);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    saveGame();
                } catch(GomokuException ce){
                    JOptionPane.showMessageDialog(GomokuGUI.this, ce.getMessage(), "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                restartGame();
            }
        });
        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                finishGame();
            }
        });  
        return buttonPanel;
    }
    
    /**
     * Muestra un cuadro de diálogo de confirmación al intentar finalizar el juego,
     * permitiendo al usuario decidir si desea finalizarlo o no.
     * 
     */
    
    public void finishGame(){
        int respuesta = JOptionPane.showInternalConfirmDialog(null, "Desea finalizar el juego?",
                "Confirmacion de cierre", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION) {
            this.remove(gomokuGame);
            mainMenu.setVisible(true);
            color1 = Color.BLACK;
            color2 = Color.WHITE;
        }
    }
    
    /**
     * Guarda el estado actual del juego en un archivo seleccionado por el usuario.
     *
     * @throws GomokuException Si hay un error al guardar el juego.
     */
    private void saveGame() throws GomokuException{
        JFileChooser save = new JFileChooser();
        save.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = save.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = save.getSelectedFile();
            gomoku.save(file);
        }
    }

    /**
     * Permite al usuario cargar un juego guardado previamente desde un archivo.
     * 
     * @throws GomokuException Si hay un error al cargar el juego.
     */
    public void resumeOption() throws GomokuException{
        JFileChooser load = new JFileChooser();
        load.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = load.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = load.getSelectedFile();
            Gomoku loadedColony = Gomoku.open(file);
            if (loadedColony != null) {
                gomoku = loadedColony;
                prepareElementsBoard();
            }
        }
    }


    /**
     * Metodo main para cargar el juego.
     * 
     */
    public static void main(String args[]) {
        new GomokuGUI();
    }
}

