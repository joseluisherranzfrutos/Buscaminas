package com.example.mvc.bussiness;

import com.example.mvc.dto.Posicion;
import com.example.mvc.dto.TableroPlus;

public interface ServicioBuscaminas {
	
	TableroPlus inicializarTablero(int filas, int columnas);

	TableroPlus colocarMinas(TableroPlus tableroPlus, int numeroBombas);

	TableroPlus calcularValorMinasAlrededor(TableroPlus tableroPlus);

	TableroPlus hayMina (Posicion posicion,TableroPlus tableroPlus);
	
	TableroPlus abrir(int fila, int columna, TableroPlus tableroPlus);

	TableroPlus colocarBandera(int fila, int columna, TableroPlus tableroPlus);

}