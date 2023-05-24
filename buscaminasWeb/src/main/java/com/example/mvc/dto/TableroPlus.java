package com.example.mvc.dto;

import java.time.LocalTime;

public class TableroPlus {
	
	private Celda[][] tablero;
	
	private int porAbrir;
	
	private String mensaje;
	
	private boolean hayMina;
	
	private LocalTime horaInicio;

	public Celda[][] getTablero() {
		return tablero;
	}

	public void setTablero(Celda[][] tablero) {
		this.tablero = tablero;
	}

	public int getPorAbrir() {
		return porAbrir;
	}

	public void setPorAbrir(int porAbrir) {
		this.porAbrir = porAbrir;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public boolean isHayMina() {
		return hayMina;
	}

	public void setHayMina(boolean hayMina) {
		this.hayMina = hayMina;
	}

	public void decPorAbrir() {
		this.porAbrir--;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}
	
	public boolean haGanado() {
		if (porAbrir==0) return true;
		return false;
	}
}
