package org.ualhmis.torneos;

import java.util.ArrayList;
import java.util.List;

class Equipo {
    private String nombre;
    private String categoria;
    private String modalidad;
    private Entrenador entrenador;
    private Entrenador segundoEntrenador;
    private List<Jugador> jugadores;

    public Equipo(String nombre, String categoria, String modalidad, Entrenador entrenador) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del equipo no puede estar vacío");
        }
        if (categoria == null || modalidad == null || entrenador == null) {
            throw new IllegalArgumentException("Los datos del equipo no pueden ser nulos");
        }
        this.nombre = nombre;
        this.categoria = categoria;
        this.modalidad = modalidad;
        this.entrenador = entrenador;
        this.jugadores = new ArrayList<>();
    }

    
    public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	public String getModalidad() {
		return modalidad;
	}


	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}


	public Entrenador getEntrenador() {
		return entrenador;
	}


	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
	}


	public Entrenador getSegundoEntrenador() {
		return segundoEntrenador;
	}


	public void setSegundoEntrenador(Entrenador segundoEntrenador) {
		this.segundoEntrenador = segundoEntrenador;
	}


	public List<Jugador> getJugadores() {
		return jugadores;
	}


	public void setJugadores(List<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	

	@Override
	public String toString() {
		return "Equipo [nombre=" + nombre + ", categoria=" + categoria + ", modalidad=" + modalidad + ", entrenador="
				+ entrenador + ", segundoEntrenador=" + segundoEntrenador + ", jugadores=" + jugadores + "]";
	}

	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;  
		if (getClass() != obj.getClass())
			return false;
		Equipo other = (Equipo) obj;
		if (!categoria.equals(other.categoria)) {
			return false;
		} 
		if (!modalidad.equals(other.modalidad)) {
			return false;
		} 
		if (!nombre.equals(other.nombre)) {
			return false;
		} 
		return true;
	}


	public void agregarJugador(Jugador jugador) {
        if (!jugadores.contains(jugador) && jugador.getCategoria().equals(this.categoria)) {
            jugadores.add(jugador);
        }
    }

    public void asignarSegundoEntrenador(Entrenador entrenador) {
        this.segundoEntrenador = entrenador;
    }
    
    
}