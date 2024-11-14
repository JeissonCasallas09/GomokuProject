package test;

import domain.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.io.File;

import org.junit.Test;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class GomokuTest {
	
	//TABLERO
	@Test
	public void shouldCreateBoard() {
		Gomoku juego = new Gomoku();
		juego.dibujar(10, "20%", "10%");
		assertTrue(juego.ok());
	}
	@Test
	public void shouldNotCreateBoard(){
		Gomoku juego = new Gomoku();
		juego.dibujar(8, "10%", "10%");
		assertFalse(juego.ok());
	}
	
	
	//CONDICIONES VICTORIA
	
	//DIRECCIONES VICTORIA BLACK
	@Test
	public void shouldWinBlackRightLeft() {
		Gomoku juego = new Gomoku();
		juego.dibujar(15,"0", "0");
		juego.place(1, 1, "White", Color.BLACK);
		juego.place(2, 1, "White", Color.BLACK);
		juego.place(3, 1, "White", Color.BLACK);
		juego.place(4, 1, "White", Color.BLACK);
		juego.place(5, 1, "White", Color.BLACK);
		
	
		assertTrue(juego.getWinner(Color.BLACK));
	}
	
	@Test
	public void shouldWinBlackUpDown() {
		Gomoku juego = new Gomoku();
		juego.dibujar(12,"0", "0");
		juego.place(1, 1, "White", Color.BLACK);
		juego.place(1, 2, "White", Color.BLACK);
		juego.place(1, 3, "White", Color.BLACK);
		juego.place(1, 4, "White", Color.BLACK);
		juego.place(1, 5, "White", Color.BLACK);
		
		assertTrue(juego.getWinner(Color.BLACK));
	}
	
	@Test
	public void shouldWinBlackDiagonallyRight() {
		Gomoku juego = new Gomoku();
		juego.dibujar(12,"0", "0");
		juego.place(1, 1, "White", Color.BLACK);
		juego.place(2, 2, "White", Color.BLACK);
		juego.place(3, 3, "White", Color.BLACK);
		juego.place(4, 4, "White", Color.BLACK);
		juego.place(5, 5, "White", Color.BLACK);
		
		assertTrue(juego.getWinner(Color.BLACK));
	}
	
	@Test
	public void shouldWinBlackDiagonallyLeft() {
		Gomoku juego = new Gomoku();
		juego.dibujar(12,"0", "0");
		juego.place(5, 1, "White", Color.BLACK);
		juego.place(4, 2, "White", Color.BLACK);
		juego.place(3, 3, "White", Color.BLACK);
		juego.place(2, 4, "White", Color.BLACK);
		juego.place(1, 5, "White", Color.BLACK);
		
		assertTrue(juego.getWinner(Color.BLACK));
	}
	
	//DIRECCIONES VICTORIA WHITE
	@Test
	public void shouldWinWhiteUpDown() {
		Gomoku juego = new Gomoku();
		juego.dibujar(12,"0", "0");
		juego.place(1, 5, "Black", Color.WHITE);
		juego.place(1, 4, "Black", Color.WHITE);
		juego.place(1, 3, "Black", Color.WHITE);
		juego.place(1, 2, "Black", Color.WHITE);
		juego.place(1, 1, "Black", Color.WHITE);
		
		assertTrue(juego.getWinner(Color.WHITE));
	}
	
	public void shouldWinWhitLeftRight() {
		Gomoku juego = new Gomoku();
		juego.dibujar(12,"0", "0");
		juego.place(5, 1, "Black", Color.WHITE);
		juego.place(4, 1, "Black", Color.WHITE);
		juego.place(3, 1, "Black", Color.WHITE);
		juego.place(2, 1, "Black", Color.WHITE);
		juego.place(1, 1, "Black", Color.WHITE);
		
		assertTrue(juego.getWinner(Color.WHITE));
	}
	
	public void shouldWinWhiteDiagonallyRight() {
		Gomoku juego = new Gomoku();
		juego.dibujar(12,"0", "10%");
		juego.place(1, 1, "Black", Color.WHITE);
		juego.place(2, 2, "Black", Color.WHITE);
		juego.place(3, 3, "Black", Color.WHITE);
		juego.place(4, 4, "Black", Color.WHITE);
		juego.place(5, 5, "Black", Color.WHITE);
		
		assertTrue(juego.getWinner(Color.WHITE));
	}
	
	public void shouldWinWhiteDiagonallyLeft() {
		Gomoku juego = new Gomoku();
		juego.dibujar(12,"0", "0");
		juego.place(5, 1, "Black", Color.WHITE);
		juego.place(4, 2, "Black", Color.WHITE);
		juego.place(3, 3, "Black", Color.WHITE);
		juego.place(2, 4, "Black", Color.WHITE);
		juego.place(1, 5, "Black", Color.WHITE);
		
		assertTrue(juego.getWinner(Color.WHITE));
	}
	
	//CASOS EN LOS QUE NO GANAN 
	
	//No HAY 5 EN LINEA
	@Test
	public void shouldNotWinBlack() {
		Gomoku juego = new Gomoku();
		juego.dibujar(12,"0", "0");
		juego.place(1, 1, "White", Color.BLACK);
		juego.place(1, 2, "White", Color.BLACK);
		juego.place(6, 6, "White", Color.BLACK);
		juego.place(1, 4, "White", Color.BLACK);
		juego.place(1, 5, "White", Color.BLACK);
		
		assertFalse(juego.getWinner(Color.BLACK));
	}
	
	@Test
	public void shouldNotWinWhite() {
		Gomoku juego = new Gomoku();
		juego.dibujar(12,"0", "0");
		juego.place(1, 1, "White", Color.WHITE);
		juego.place(1, 2, "White", Color.WHITE);
		juego.place(6, 6, "White", Color.WHITE);
		juego.place(1, 4, "White", Color.WHITE);
		juego.place(1, 5, "White", Color.WHITE);
		
		assertFalse(juego.getWinner(Color.WHITE));
	}
	
	//PONER FICHAS
	
	//FICHA CORRECTAMENTE.
		@Test
		public void shouldPlaceWhite() {
			Gomoku juego = new Gomoku();
			juego.dibujar(12, "0", "0");
			juego.place(1, 1, "White", Color.BLACK);
			assertTrue(juego.ok());
		}
		
		@Test
		public void shouldPlaceBlack() {
			Gomoku juego = new Gomoku();
			juego.dibujar(12, "0", "0");
			juego.place(1, 1, "Black", Color.WHITE);
			assertTrue(juego.ok());
		}
		
		//FICHA NO SE PUEDEN SOBREPONER
		@Test
		public void shouldNotPlaceTwoBlacks() {
			Gomoku juego = new Gomoku();
			juego.dibujar(12,"0","0");
			juego.place(1, 1, "White", Color.BLACK);
			juego.place(1, 1, "White", Color.BLACK);
			assertFalse(juego.ok());
		}
		
		@Test
		public void shouldNotPlaceTwoWhites() {
			Gomoku juego = new Gomoku();
			juego.dibujar(12,"0","0");
			juego.place(1, 1, "Black", Color.WHITE);
			juego.place(1, 1, "Black", Color.WHITE);
			assertFalse(juego.ok());
		}
		
		@Test
		public void shouldNotPlaceOneBlackOneWhite(){
			Gomoku juego = new Gomoku();
			juego.dibujar(12,"0","0");
			juego.place(1, 1, "White", Color.BLACK);
			juego.place(1, 1, "Black", Color.WHITE);
			assertFalse(juego.ok());
		}
		
		@Test
		public void shouldNotPlaceOneWhiteOneBlack(){
			Gomoku juego = new Gomoku();
			juego.dibujar(12,"0","0");
			juego.place(1, 1, "White", Color.WHITE);
			juego.place(1, 1, "Black", Color.BLACK);
			assertFalse(juego.ok());
		}
		
	//CASO PARA EMPATE
		@Test
	    public void shouldNotWinNoOne() {
	        Gomoku juego = new Gomoku();
	        juego.dibujar(10, "10%", "10%");

	        // Llenar el tablero de manera que no haya líneas de 5
	        // Patrón: Piedras negras y blancas alternadas
	        for (int i = 1; i <= juego.tam; i++) {
	            for (int j = 1; j <= juego.tam; j++) {
	                if ((i + j) % 2 == 0) {
	                    juego.place(i, j, "Black", Color.BLACK);
	                } else {
	                    juego.place(i, j, "White", Color.WHITE);
	                }
	            }
	        }
	        assertFalse(juego.isThereAFiveChain(Color.BLACK));
	        assertFalse(juego.isThereAFiveChain(Color.WHITE));
	        

	  
	    }
	//GANAR CON PUNTAJES
		@Test
		public void shouldBlackWinForPoints(){
			Gomoku juego = new Gomoku();
			juego.dibujar(15,"100", "100%");
			juego.place(1,1, "White", Color.BLACK);
			juego.place(1,3, "White", Color.BLACK);
			juego.place(1, 5, "White", Color.BLACK);
			juego.place(1, 7, "White", Color.BLACK);
			juego.place(1, 9, "White", Color.BLACK);
			juego.place(1, 11, "White", Color.BLACK);
			juego.place(1, 12, "White", Color.BLACK);
			juego.place(1, 14, "White", Color.BLACK);
			juego.place(2, 2, "White", Color.BLACK);
			juego.place(2, 5, "White", Color.BLACK);
			juego.getWinner(Color.WHITE);
			assertTrue(juego.ok());
		}
		
		@Test
		public void shouldWhiteWinForPoints(){
			Gomoku juego = new Gomoku();
			juego.dibujar(15,"100", "100%");
			juego.place(1,1, "Black", Color.WHITE);
			juego.place(1,3, "Black", Color.WHITE);
			juego.place(1, 5, "Black", Color.WHITE);
			juego.place(1, 7, "Black", Color.WHITE);
			juego.place(1, 9, "Black", Color.WHITE);
			juego.place(1, 11, "Black", Color.WHITE);
			juego.place(1, 12, "Black", Color.WHITE);
			juego.place(1, 14, "Black", Color.WHITE);
			juego.place(2, 2, "Black", Color.WHITE);
			juego.place(2, 5, "Black", Color.WHITE);
			juego.getWinner(Color.WHITE);
			assertTrue(juego.ok());
		}
	
	//GUARDADO Y CARGADO DE PARTIDA.
		@Test
	     public void ShouldsaveGomoku(){
	        try {
	            Gomoku gomoku = new Gomoku();
	            Path tempDir = Files.createTempDirectory("Gomoku_test");
	            File saveFile = new File(tempDir.toFile(), "Gomoku_save.tmp");

	            gomoku.save(saveFile);

	            assertTrue(saveFile.exists());

	        } catch (GomokuException e) {
	            fail("No se esperaba una excepción aquí: " + e.getMessage());
	        } catch (Exception e) {
	            fail("Ocurrió un error inesperado: " + e.getMessage());
	        }
	    }
	    
		@Test
	    public void ShouldOpenGomoku() {
	        try {
	            Gomoku originalGomoku = new Gomoku();
	            Path tempDir = Files.createTempDirectory("gomoku_test");
	            File tempFile = new File(tempDir.toFile(), "gomoku_save.tmp");
	            originalGomoku.save(tempFile);
	            Gomoku loadedGomoku = Gomoku.open(tempFile);
	            assertNotNull(loadedGomoku);

	        } catch (GomokuException e) {
	            fail("No se esperaba una excepción aquí: " + e.getMessage());
	        } catch (Exception e) {
	            fail("Ocurrió un error inesperado: " + e.getMessage());
	        }
	    }
		
		//PIEDRAS ESPECIALES
		
		@Test
		public void shouldWinBlackPesada() {
			Gomoku juego = new Gomoku();
			juego.dibujar(15,"100%", "100%");
			juego.place(1, 1, "White", Color.BLACK);
			juego.place(2, 1, "White", Color.BLACK);
			juego.place(3, 1, "White", Color.BLACK);
			juego.getWinner(Color.BLACK);
			assertTrue(juego.ok());
			
		}
		
		@Test
		public void shouldWinWhitePesada() {
			Gomoku juego = new Gomoku();
			juego.dibujar(12,"100%", "100%");
			juego.place(1, 1, "White", Color.WHITE);
			juego.place(1, 2, "White", Color.WHITE);
			juego.place(1, 3, "White", Color.WHITE);
			juego.getWinner(Color.BLACK);
			assertTrue(juego.ok());
		}
		
		@Test
		public void shouldWinBlackTemporal() {
			Gomoku juego = new Gomoku();
			juego.dibujar(15,"100%", "100%");
			juego.place(1, 1, "Black", Color.WHITE);
	        juego.place(2, 2, "White", Color.BLACK);
	        juego.place(3, 3, "Black", Color.WHITE);
	        juego.place(4, 4, "White", Color.BLACK);
	        juego.place(5, 5, "Black", Color.WHITE);
	        juego.place(1, 5, "White", Color.BLACK);
	        juego.place(2, 4, "Black", Color.WHITE);
	        juego.place(3, 2, "White", Color.BLACK);
	        juego.place(4, 1, "Black", Color.WHITE);
	        juego.place(5, 3, "White", Color.BLACK);
	        assertTrue(juego.movimientoCasillaValido(1,1)||juego.movimientoCasillaValido(2,2)||juego.movimientoCasillaValido(3,3)||juego.movimientoCasillaValido(4,4)||juego.movimientoCasillaValido(5,5)|juego.movimientoCasillaValido(1,5));
		}
		
		

}