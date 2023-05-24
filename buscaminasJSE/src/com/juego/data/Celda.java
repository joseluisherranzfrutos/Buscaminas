package com.juego.data;

public class Celda {
	
	private boolean abierto;
	private boolean bandera;
	private char simbolo;
	private final Posicion posicion;
	
	public Celda(int fila, int columna, boolean abierto, boolean bandera) {
		super();
		this.posicion = new Posicion(fila,columna);
		this.abierto = abierto;
	}
	
	public char getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(char simbolo) {
		this.simbolo = simbolo;
	}

	public boolean isAbierto() {
		return abierto;
	}

	public void setAbierto(boolean abierto) {
		this.abierto = abierto;
	}

	public boolean isBandera() {
		return bandera;
	}

	public void setBandera(boolean bandera) {
		this.bandera = bandera;
	}

	public Posicion getPosicion() {
		return posicion;
	}

	public void mostrarCelda() {
		if(abierto) System.out.print("| "+simbolo+" |");
		if(!abierto) {
			if (bandera) System.out.print("| P |"); 
			else System.out.print("|   |");
		}
	}
	
	public void mostrarCeldaSolucion() {
		System.out.print("| "+simbolo+" |");
		
	}
}
