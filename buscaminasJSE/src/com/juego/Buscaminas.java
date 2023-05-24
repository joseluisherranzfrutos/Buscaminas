package com.juego;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.juego.common.Constantes;
import com.juego.common.Mensajes;
import com.juego.ln.Partida;

public class Buscaminas {
	
	List<Partida> partidas = new ArrayList<Partida>();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Buscaminas juego = new Buscaminas();
		
		juego.mostrarMensajeInicioJuego();
		
		do{
			Partida partida = new Partida();
			juego.partidas.add(partida);
			partida.jugar(sc);
			sc.nextLine();
		}while(juego.seguirPartida(sc));
		
		juego.mostrarMensajeFinJuego();
		
		sc.close();
	}


	private boolean seguirPartida(Scanner sc) {
		
		System.out.println("¿Jugar otra Partida(s/S)?");
		String respuesta =sc.nextLine();
		respuesta=respuesta.trim();
		
		return Constantes.RESPUESTA_CONTINUAR.contains(respuesta);
	}

	private void mostrarMensajeInicioJuego() {
		System.out.println(Mensajes.INICIO_JUEGO);
	}
	
	private void mostrarMensajeFinJuego() {
		System.out.println(Mensajes.FIN_JUEGO);
	}
}
