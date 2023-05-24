package com.juego.ln;

import com.juego.common.Constantes;
import com.juego.data.Celda;
import com.juego.data.Posicion;

/**
 * EJEMPLO DE SINGLETON: -- Variable privada estática de la instancia --
 * Constructor privado -- Método público estático que devuelve la instancia
 * 
 */
public class Tablero {

	private static Tablero instancia;
	
	private static int porAbrir;

	private Celda[][] tablero;

	private Tablero(int filas, int columnas) {
		super();
		this.tablero = new Celda[filas][columnas];
	}

	public static Tablero inicializar(int filas, int columnas) {
		if (instancia == null)
			instancia = new Tablero(filas, columnas);
		for (int fila = 0; fila < instancia.tablero.length; fila++) {
			for (int columna = 0; columna < instancia.tablero[fila].length; columna++) {
				instancia.tablero[fila][columna] = new Celda(fila, columna, false, false);
			}
		}
		porAbrir = filas*columnas;
		return instancia;
	}

	public void colocarMinas() {
		int contador = 0;
		do {
			int fila = (int) (Math.random() * Constantes.NUM_FILAS_TABLERO_FACIL);
			int columna = (int) (Math.random() * Constantes.NUM_COLUMNAS_TABLERO_FACIL);

			if (tablero[fila][columna].getSimbolo() != Constantes.MINA) {
				tablero[fila][columna].setSimbolo(Constantes.MINA);
				contador++;
				porAbrir--;
			}
		} while (contador < Constantes.NUM_MINAS_FACIL);
	}

	public void calcularValorMinasAlrededor() {
		for (int fila = 0; fila < tablero.length; fila++) {
			for (int columna = 0; columna < tablero[fila].length; columna++) {
				if (tablero[fila][columna].getSimbolo() != Constantes.MINA) {
					int valor = 0;
					if (!fueraTablero(fila - 1, columna - 1)) {
						if (tablero[fila - 1][columna - 1].getSimbolo() == Constantes.MINA)
							valor++;
					}
					if (!fueraTablero(fila - 1, columna)) {
						if (tablero[fila - 1][columna].getSimbolo() == Constantes.MINA)
							valor++;
					}
					if (!fueraTablero(fila - 1, columna + 1)) {
						if (tablero[fila - 1][columna + 1].getSimbolo() == Constantes.MINA)
							valor++;
					}
					if (!fueraTablero(fila, columna - 1)) {
						if (tablero[fila][columna - 1].getSimbolo() == Constantes.MINA)
							valor++;
					}
					if (!fueraTablero(fila, columna + 1)) {
						if (tablero[fila][columna + 1].getSimbolo() == Constantes.MINA)
							valor++;
					}
					if (!fueraTablero(fila + 1, columna - 1)) {
						if (tablero[fila + 1][columna - 1].getSimbolo() == Constantes.MINA)
							valor++;
					}
					if (!fueraTablero(fila + 1, columna)) {
						if (tablero[fila + 1][columna].getSimbolo() == Constantes.MINA)
							valor++;
					}
					if (!fueraTablero(fila + 1, columna + 1)) {
						if (tablero[fila + 1][columna + 1].getSimbolo() == Constantes.MINA)
							valor++;
					}
					tablero[fila][columna].setSimbolo((char) (valor + 48));
				}
			}
		}
	}

	public boolean fueraTablero(int fila, int columna) {
		if (fila < 0)
			return true;
		if (columna < 0)
			return true;
		if (fila >= tablero.length)
			return true;
		if (columna >= tablero[fila].length)
			return true;
		return false;
	}

	/**
	 * Se pinta el tablero
	 */
	public void mostrarTablero() {

		System.out.print("     ");
		for (int columna = 0; columna < tablero.length; columna++) {
			System.out.print("| " + (columna + 1) + " |");
		}
		System.out.println();
		for (int fila = 0; fila < tablero.length; fila++) {
			System.out.print("| " + (fila + 1) + " |");
			for (int columna = 0; columna < tablero[fila].length; columna++) {
				tablero[fila][columna].mostrarCelda();
			}
			System.out.println();
		}
	}

	public void mostrarTableroSolucion() {

		System.out.print("     ");
		for (int columna = 0; columna < tablero.length; columna++) {
			System.out.print("| " + (columna + 1) + " |");
		}
		System.out.println();
		for (int fila = 0; fila < tablero.length; fila++) {
			System.out.print("| " + (fila + 1) + " |");
			for (int columna = 0; columna < tablero[fila].length; columna++) {
				tablero[fila][columna].mostrarCeldaSolucion();
			}
			System.out.println();
		}
	}

	public void abrir(int fila, int columna) {
		if (!tablero[fila][columna].isAbierto()) {
			tablero[fila][columna].setAbierto(true);
			porAbrir--;
			if (tablero[fila][columna].getSimbolo() == Constantes.CERO) {
				if (!fueraTablero(fila - 1, columna - 1)) {
					int nuevaFila = fila - 1;
					int nuevaColumna = columna - 1;
					abrir(nuevaFila,nuevaColumna);
				}
				if (!fueraTablero(fila - 1, columna)) {
					int nuevaFila = fila - 1;
					int nuevaColumna = columna;
					abrir(nuevaFila,nuevaColumna);
				}
				if (!fueraTablero(fila - 1, columna + 1)) {
					int nuevaFila = fila - 1;
					int nuevaColumna = columna + 1;
					abrir(nuevaFila,nuevaColumna);
				}
				if (!fueraTablero(fila, columna - 1)) {
					int nuevaFila = fila;
					int nuevaColumna = columna - 1;
					abrir(nuevaFila,nuevaColumna);
				}
				if (!fueraTablero(fila, columna + 1)) {
					int nuevaFila = fila;
					int nuevaColumna = columna + 1;
					abrir(nuevaFila,nuevaColumna);
				}
				if (!fueraTablero(fila + 1, columna - 1)) {
					int nuevaFila = fila + 1;
					int nuevaColumna = columna - 1;
					abrir(nuevaFila,nuevaColumna);
				}
				if (!fueraTablero(fila + 1, columna)) {
					int nuevaFila = fila + 1;
					int nuevaColumna = columna;
					abrir(nuevaFila,nuevaColumna);
				}
				if (!fueraTablero(fila + 1, columna + 1)) {
					int nuevaFila = fila + 1;
					int nuevaColumna = columna + 1;
					abrir(nuevaFila,nuevaColumna);
				}
			}
		}
	}
	
	public boolean hayBomba (Posicion posicion) {
		if (tablero[posicion.getFila()][posicion.getColumna()].getSimbolo() == Constantes.MINA) return true;
		return false;
	}

	public void colocarBandera(Posicion posicion) {
		tablero[posicion.getFila()][posicion.getColumna()].setBandera(true);
		
	}
	
	public boolean haGanado() {
		if (porAbrir==0) return true;
		return false;
	}
}
