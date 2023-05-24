package com.example.mvc.bussiness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.mvc.common.Constantes;
import com.example.mvc.dto.Celda;
import com.example.mvc.dto.Posicion;
import com.example.mvc.dto.TableroPlus;

@Service
public class ServicioBuscaminasImpl implements ServicioBuscaminas {
	
	Logger log = LoggerFactory.getLogger(ServicioBuscaminasImpl.class);

	@Override
	public TableroPlus inicializarTablero(int filas, int columnas) {
		TableroPlus tableroPlus = new TableroPlus();
		Celda[][] tablero =  new Celda[filas][columnas];
		for (int fila = 0; fila < tablero.length; fila++) {
			for (int columna = 0; columna < tablero[fila].length; columna++) {
				tablero[fila][columna] = new Celda(fila, columna, false, false);
			}
		}
		int porAbrir = filas*columnas;
		tableroPlus.setTablero(tablero);
		tableroPlus.setPorAbrir(porAbrir);
		return tableroPlus;
	}

	@Override
	public TableroPlus colocarMinas(TableroPlus tableroPlus, int numeroBombas) {
		int contador = 0;
		int porAbrir = tableroPlus.getPorAbrir();
		do {
			int fila = (int) (Math.random() * tableroPlus.getTablero().length);
			int columna = (int) (Math.random() * tableroPlus.getTablero()[0].length);

			if (tableroPlus.getTablero()[fila][columna].getSimbolo() != Constantes.MINA) {
				tableroPlus.getTablero()[fila][columna].setSimbolo(Constantes.MINA);
				contador++;
				porAbrir--;
			}
		} while (contador < Constantes.NUM_MINAS_FACIL);
		tableroPlus.setPorAbrir(porAbrir);
		
		return tableroPlus;
	}

	@Override
	public TableroPlus calcularValorMinasAlrededor(TableroPlus tableroPlus) {
		Celda[][] tablero = tableroPlus.getTablero();
		for (int fila = 0; fila < tablero.length; fila++) {
			for (int columna = 0; columna < tablero[fila].length; columna++) {
				if (tablero[fila][columna].getSimbolo() != Constantes.MINA) {
					int valor = 0;
					if (!fueraTablero(fila - 1, columna - 1, tablero)) {
						if (tablero[fila - 1][columna - 1].getSimbolo() == Constantes.MINA)
							valor++;
					}
					if (!fueraTablero(fila - 1, columna, tablero)) {
						if (tablero[fila - 1][columna].getSimbolo() == Constantes.MINA)
							valor++;
					}
					if (!fueraTablero(fila - 1, columna + 1, tablero)) {
						if (tablero[fila - 1][columna + 1].getSimbolo() == Constantes.MINA)
							valor++;
					}
					if (!fueraTablero(fila, columna - 1, tablero)) {
						if (tablero[fila][columna - 1].getSimbolo() == Constantes.MINA)
							valor++;
					}
					if (!fueraTablero(fila, columna + 1, tablero)) {
						if (tablero[fila][columna + 1].getSimbolo() == Constantes.MINA)
							valor++;
					}
					if (!fueraTablero(fila + 1, columna - 1, tablero)) {
						if (tablero[fila + 1][columna - 1].getSimbolo() == Constantes.MINA)
							valor++;
					}
					if (!fueraTablero(fila + 1, columna, tablero)) {
						if (tablero[fila + 1][columna].getSimbolo() == Constantes.MINA)
							valor++;
					}
					if (!fueraTablero(fila + 1, columna + 1, tablero)) {
						if (tablero[fila + 1][columna + 1].getSimbolo() == Constantes.MINA)
							valor++;
					}
					tablero[fila][columna].setSimbolo((char) (valor + 48));
				}
			}
		}
		tableroPlus.setTablero(tablero);
		return tableroPlus;
	}
	
	private boolean fueraTablero(int fila, int columna, Celda[][] tablero) {
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
	
	@Override
	public TableroPlus hayMina (Posicion posicion,TableroPlus tableroPlus) {
		if (tableroPlus.getTablero()[posicion.getFila()][posicion.getColumna()].getSimbolo() == Constantes.MINA) {
			tableroPlus.setHayMina(true);
			Celda[][] tablero = tableroPlus.getTablero();
			for (int fila = 0; fila < tablero.length; fila++) {
				for (int columna = 0; columna < tablero[fila].length; columna++) {
					tablero[fila][columna].setAbierto(true);
				}
			}
			tableroPlus.setTablero(tablero);
		} else {
			tableroPlus.setHayMina(false);
		}
		return tableroPlus;
	}
	
	@Override
	public TableroPlus abrir(int fila, int columna, TableroPlus tableroPlus) {
		Celda[][] tablero = tableroPlus.getTablero();
		if (!tablero[fila][columna].isAbierto()) {
			tableroPlus.getTablero()[fila][columna].setAbierto(true);
			tableroPlus.decPorAbrir();

			if (tablero[fila][columna].getSimbolo() == Constantes.CERO) {
				if (!fueraTablero(fila - 1, columna - 1,tablero)) {
					int nuevaFila = fila - 1;
					int nuevaColumna = columna - 1;
					tableroPlus = abrir(nuevaFila,nuevaColumna, tableroPlus);
				}
				if (!fueraTablero(fila - 1, columna, tablero)) {
					int nuevaFila = fila - 1;
					int nuevaColumna = columna;
					tableroPlus = abrir(nuevaFila,nuevaColumna, tableroPlus);
				}
				if (!fueraTablero(fila - 1, columna + 1, tablero)) {
					int nuevaFila = fila - 1;
					int nuevaColumna = columna + 1;
					tableroPlus = abrir(nuevaFila,nuevaColumna, tableroPlus);
				}
				if (!fueraTablero(fila, columna - 1, tablero)) {
					int nuevaFila = fila;
					int nuevaColumna = columna - 1;
					tableroPlus = abrir(nuevaFila,nuevaColumna, tableroPlus);
				}
				if (!fueraTablero(fila, columna + 1, tablero)) {
					int nuevaFila = fila;
					int nuevaColumna = columna + 1;
					tableroPlus = abrir(nuevaFila,nuevaColumna, tableroPlus);
				}
				if (!fueraTablero(fila + 1, columna - 1, tablero)) {
					int nuevaFila = fila + 1;
					int nuevaColumna = columna - 1;
					tableroPlus = abrir(nuevaFila,nuevaColumna, tableroPlus);
				}
				if (!fueraTablero(fila + 1, columna, tablero)) {
					int nuevaFila = fila + 1;
					int nuevaColumna = columna;
					tableroPlus = abrir(nuevaFila,nuevaColumna, tableroPlus);
				}
				if (!fueraTablero(fila + 1, columna + 1, tablero)) {
					int nuevaFila = fila + 1;
					int nuevaColumna = columna + 1;
					tableroPlus = abrir(nuevaFila,nuevaColumna, tableroPlus);
				}
			}
		}
		return tableroPlus;
	}

	@Override
	public TableroPlus colocarBandera(int fila, int columna, TableroPlus tableroPlus) {
		tableroPlus.getTablero()[fila][columna].cambiarBandera();;
		return tableroPlus;
	}

}
