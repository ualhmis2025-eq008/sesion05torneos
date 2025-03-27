package org.ualhmis.torneos;

import java.time.LocalDateTime;

class HorarioPartido {
  

	private LocalDateTime inicio;
    private LocalDateTime fin;

    public HorarioPartido(LocalDateTime inicio, LocalDateTime fin) {
        if (fin.isBefore(inicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la de inicio");
        }
        this.inicio = inicio;
        this.fin = fin;
    }

    public boolean seSolapa(LocalDateTime otroInicio, LocalDateTime otroFin) {
        return !inicio.isAfter(otroFin) && !fin.isBefore(otroInicio);
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }
    
    public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}
}