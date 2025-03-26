package org.ualhmis.torneos;

import java.time.LocalDate;

class Entrenador extends Persona {
    public Entrenador(String nombre, String genero, LocalDate fechaNacimiento) {
        super(nombre, genero, fechaNacimiento);
    }
}