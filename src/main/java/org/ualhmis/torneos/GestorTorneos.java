package org.ualhmis.torneos;

import java.util.ArrayList;
import java.util.List;

class GestorTorneos {
    private List<Torneo> torneos;

    public GestorTorneos() {
        this.torneos = new ArrayList<>();
    }

    public void crearTorneo(String nombre, String deporte, String categoria, String modalidad, String tipo) {
        torneos.add(new Torneo(nombre, deporte, categoria, modalidad, tipo));
    }
}