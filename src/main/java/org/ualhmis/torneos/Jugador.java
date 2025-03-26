package org.ualhmis.torneos;

import java.time.LocalDate;

class Jugador extends Persona {
    private String categoria;

    public Jugador(String nombre, String genero, LocalDate fechaNacimiento) {
        super(nombre, genero, fechaNacimiento);
        this.categoria = determinarCategoria(calcularEdad());
    }

    private String determinarCategoria(int edad) {
        if (edad < 12) return "Infantil";
        else if (edad < 15) return "Cadete";
        else if (edad < 18) return "Juvenil";
        else if (edad < 21) return "Junior";
        else return "Absoluta";
    }

    public String getCategoria() { return categoria; }
}
