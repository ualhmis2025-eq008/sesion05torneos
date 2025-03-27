package org.ualhmis.torneos;

import java.util.ArrayList;
import java.util.List;

class Torneo {
    private String nombre;
    private String deporte;
    private String categoria;
    private String modalidad;
    private List<Equipo> equipos;
    private String tipo;
    public Sede getSede() {
		return sede;
	}

	private Sede sede;

    public Torneo(String nombre, String deporte, String categoria, String modalidad, String tipo) {
        this.nombre = nombre;
        this.deporte = deporte;
        this.categoria = categoria;
        this.modalidad = modalidad;
        this.tipo = tipo;
        this.equipos = new ArrayList<>();
    }

    public void registrarEquipo(Equipo equipo) {
        if (!equipo.getCategoria().equals(this.categoria) || !equipo.getModalidad().equals(this.modalidad)) {
            throw new IllegalArgumentException("El equipo no cumple con la categoría y modalidad del torneo");
        }
        if (!equipos.contains(equipo)) {
            equipos.add(equipo);
        }
    }
    
    public void setSede(Sede sede) {
        if (!sede.getInstalaciones().stream().anyMatch(i -> 
            i.getDeporteAdecuado().equals(this.deporte))) {
            throw new IllegalArgumentException("La sede no tiene instalaciones adecuadas para este deporte");
        }
        this.sede = sede;
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDeporte() {
		return deporte;
	}

	public void setDeporte(String deporte) {
		this.deporte = deporte;
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

	public List<Equipo> getEquipos() {
		return equipos;
	}

	public void setEquipos(List<Equipo> equipos) {
		this.equipos = equipos;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
    
    
}
