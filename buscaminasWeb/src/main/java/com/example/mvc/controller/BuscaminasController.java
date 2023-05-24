package com.example.mvc.controller;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.mvc.bussiness.ServicioBuscaminas;
import com.example.mvc.common.Constantes;
import com.example.mvc.dto.Posicion;
import com.example.mvc.dto.TableroPlus;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class BuscaminasController {
	Logger log = LoggerFactory.getLogger(BuscaminasController.class);

	@Autowired
	ServicioBuscaminas servicio;
	
	@GetMapping
	public String genearTablero(Model model, HttpSession session) {
		log.info("[generarTablero]");
		
		TableroPlus tableroPlus = new TableroPlus();
		 
		int filas = Constantes.NUM_FILAS_TABLERO_FACIL;
		int columnas =  Constantes.NUM_COLUMNAS_TABLERO_FACIL;
		int numeroMinas = Constantes.NUM_MINAS_FACIL;
		tableroPlus = servicio.inicializarTablero(filas, columnas);
		tableroPlus = servicio.colocarMinas(tableroPlus, numeroMinas);
		tableroPlus = servicio.calcularValorMinasAlrededor(tableroPlus);
		tableroPlus.setHoraInicio(LocalTime.now());
	
		session.setAttribute("tableroPlus", tableroPlus);

		return "buscaminas";
	}
	
	@PostMapping("/a")
	public String abrirTablero( HttpSession session, @RequestParam Integer fila, @RequestParam Integer columna, Model model) {
		log.info("[abrirTablero]");
		
		TableroPlus tableroPlus = (TableroPlus) session.getAttribute("tableroPlus");

		tableroPlus = servicio.hayMina(new Posicion (fila,columna), tableroPlus);
		if (tableroPlus.isHayMina())
			model.addAttribute("message","aviso.perdedor");
		if (!tableroPlus.isHayMina())
			tableroPlus = servicio.abrir(fila, columna, tableroPlus);
		if (tableroPlus.haGanado()) {
			long tiempoTardado = ChronoUnit.SECONDS.between(tableroPlus.getHoraInicio(), LocalTime.now());
			model.addAttribute("message","aviso.ganador");
			model.addAttribute("parametro", tiempoTardado);
		}
		session.removeAttribute("tableroPlus");
		session.setAttribute("tableroPlus", tableroPlus);
		
		return "buscaminas";
	}
	
	@PostMapping("/b")
	public String banderaTablero( HttpSession session, @RequestParam int fila, @RequestParam int columna, Model model) {
		log.info("[banderaTablero]");
		
		TableroPlus tableroPlus = (TableroPlus) session.getAttribute("tableroPlus");

		tableroPlus = servicio.colocarBandera(fila, columna, tableroPlus);

		return "buscaminas";
	}

}
