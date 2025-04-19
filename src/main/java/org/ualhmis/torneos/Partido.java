package org.ualhmis.torneos;

import java.time.LocalDateTime;
import org.ualhmis.torneos.*;

class Partido {
	
	private Equipo equipo1;
    private Equipo equipo2;
    private int golesEquipo1;
    private int golesEquipo2;
    private InstalacionDeportiva instalacion;
    private HorarioPartido horario;
    
    public Partido(Equipo equipo1, Equipo equipo2) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
    }
    
    public Equipo getEquipo1() {
		return equipo1;
	}

	public void setEquipo1(Equipo equipo1) {
		this.equipo1 = equipo1;
	}

	public Equipo getEquipo2() {
		return equipo2;
	}

	public void setEquipo2(Equipo equipo2) {
		this.equipo2 = equipo2;
	}

	public int getGolesEquipo1() {
		return golesEquipo1;
	}

	public void setGolesEquipo1(int golesEquipo1) {
		this.golesEquipo1 = golesEquipo1;
	}

	public int getGolesEquipo2() {
		return golesEquipo2;
	}

	public void setGolesEquipo2(int golesEquipo2) {
		this.golesEquipo2 = golesEquipo2;
	}
    
    public InstalacionDeportiva getInstalacion() {
		return instalacion;
	}


	public void setInstalacion(InstalacionDeportiva instalacion) {
		this.instalacion = instalacion;
	}


	public HorarioPartido getHorario() {
		return horario;
	}


	public void setHorario(HorarioPartido horario) {
		this.horario = horario;
	}

	public void asignarInstalacion(InstalacionDeportiva instalacion, LocalDateTime inicio, LocalDateTime fin) {
        if (instalacion == null) {
            throw new IllegalArgumentException("La instalación no puede ser nula");
        }
//        if (!instalacion.getDeporteAdecuado().equalsIgnoreCase(equipo1.getModalidad())) {
//            throw new IllegalArgumentException("Instalación no adecuada para este deporte");
//        }
        
        HorarioPartido nuevoHorario = new HorarioPartido(inicio, fin);
        if (instalacion.estaDisponible(inicio, fin)) {
            this.instalacion = instalacion;
            this.horario = nuevoHorario;
            instalacion.agregarHorarioOcupado(nuevoHorario);
        } else {
            throw new IllegalArgumentException("Instalación no disponible en ese horario");
        }
    }

    public void registrarResultado(int golesEquipo1, int golesEquipo2) {
        this.golesEquipo1 = golesEquipo1;
        this.golesEquipo2 = golesEquipo2;
    }
}
