package org.ualhmis.torneos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class InstalacionDeportiva {
  

	private String nombre;
    private String tipo; // Campo, Pabellón, Pista
    private String deporteAdecuado;
    private List<HorarioPartido> horariosOcupados;

    public InstalacionDeportiva(String nombre, String tipo, String deporteAdecuado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.deporteAdecuado = deporteAdecuado;
        this.horariosOcupados = new ArrayList<>();
    }

    public boolean estaDisponible(LocalDateTime inicio, LocalDateTime fin) {
        return horariosOcupados.stream().noneMatch(horario -> 
            horario.seSolapa(inicio, fin));
    }

    public void agregarHorarioOcupado(HorarioPartido horario) {
        if (estaDisponible(horario.getInicio(), horario.getFin())) {
            horariosOcupados.add(horario);
        } else {
            throw new IllegalArgumentException("El horario se solapa con otro partido existente");
        }
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDeporteAdecuado() {
        return deporteAdecuado;
    }

    public List<HorarioPartido> getHorariosOcupados() {
        return new ArrayList<>(horariosOcupados); // Devuelve copia para proteger encapsulamiento
    }  
    
    public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setDeporteAdecuado(String deporteAdecuado) {
		this.deporteAdecuado = deporteAdecuado;
	}

	public void setHorariosOcupados(List<HorarioPartido> horariosOcupados) {
		this.horariosOcupados = horariosOcupados;
	}
}