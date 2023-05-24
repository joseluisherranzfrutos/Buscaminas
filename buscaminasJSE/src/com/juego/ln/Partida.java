package com.juego.ln;

import java.util.Scanner;

import com.juego.common.Constantes;
import com.juego.common.Mensajes;
import com.juego.data.Posicion;
import com.juego.exceptions.GanadorException;
import com.juego.exceptions.PerdedorException;

public class Partida {

	Tablero tablero;

	public void jugar(Scanner sc) {
		
		this.mostrarMensajeInicioPartida();		

		try {
			this.tablero=Tablero.inicializar(Constantes.NUM_FILAS_TABLERO_FACIL, Constantes.NUM_COLUMNAS_TABLERO_FACIL);
			this.tablero.colocarMinas();
			this.tablero.calcularValorMinasAlrededor();
			while(true) {
				Posicion posicion=null;
				boolean pedir=Boolean.TRUE;
				Boolean abrir=null;
				
				//this.tablero.mostrarTableroSolucion();
				//System.out.println();
				this.tablero.mostrarTablero();
				
				while (abrir == null) {
					this.mostrarMensajeBanderaOAbrir();
					String banderaOAbrir = sc.nextLine();
					
					if (Constantes.RESPUESTA_BANDERA.contains(banderaOAbrir)) abrir = false;
					else if (Constantes.RESPUESTA_ABRIR.contains(banderaOAbrir)) abrir = true;
					if (abrir==null) this.mostrarMensajeOpcionNoValida();
				}
				
				while(pedir) {
					try {
						posicion=this.pedirPosicion(sc);
						pedir = this.tablero.fueraTablero(posicion.getFila(),posicion.getColumna());
					}catch(Exception e) {sc.nextLine();};
				    if(pedir) this.mostrarMensajePosicionNoValida();
				}
				if (abrir) {
					if (this.tablero.hayBomba(posicion)) throw new PerdedorException();
					this.tablero.abrir(posicion.getFila(), posicion.getColumna());
					if (this.tablero.haGanado()) throw new GanadorException();
				}else {
					this.tablero.colocarBandera(posicion);
				}
				sc.nextLine();
			}

		} catch (GanadorException ge) {
			this.mostrarMensajeGanador();
		} catch (PerdedorException ge) {
			this.mostrarMensajePerdedor();
		}
		this.mostrarMensajeFinPartida();

	}
	
	private void mostrarMensajeOpcionNoValida() {
		System.out.println(Mensajes.OPCION_NO_VALIDA);
		
	}

	private Posicion pedirPosicion(Scanner sc) {
		System.out.println(Mensajes.POSICION_PEDIR);
		System.out.println(Mensajes.POSICION_FILA_PEDIR);
		int fila = sc.nextInt()-1;
		System.out.println(Mensajes.POSICION_COLUMNA_PEDIR);
		int columna = sc.nextInt()-1;
		return new Posicion(fila, columna);
	}
	
	private void mostrarMensajeBanderaOAbrir() {
		System.out.println(Mensajes.BANDERA_O_ABRIR);
			
	}

	private void mostrarMensajeInicioPartida() {
		System.out.println(Mensajes.INICIO_PARTIDA);

	}
	private void mostrarMensajeFinPartida() {
		System.out.println(Mensajes.FIN_PARTIDA);
	}

	private void mostrarMensajeGanador() {
		System.out.println(Mensajes.GANADOR);
	}

	private void mostrarMensajePerdedor() {
		System.out.println(Mensajes.PERDEDOR);
	}
	
	private void mostrarMensajePosicionNoValida() {
		System.out.println(Mensajes.POSICION_NO_VALIDA);
		
	}
}
