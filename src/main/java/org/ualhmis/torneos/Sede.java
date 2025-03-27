package org.ualhmis.torneos;

import java.util.ArrayList;
import java.util.List;

class Sede {
   

	private String nombre;
    private String direccion;
    private List<InstalacionDeportiva> instalaciones;

    public Sede(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.instalaciones = new ArrayList<>();
    }

    public void agregarInstalacion(InstalacionDeportiva instalacion) {
        instalaciones.add(instalacion);
    }

    // Getters y setters
    
    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public List<InstalacionDeportiva> getInstalaciones() {
		return instalaciones;
	}

	public void setInstalaciones(List<InstalacionDeportiva> instalaciones) {
		this.instalaciones = instalaciones;
	}
}