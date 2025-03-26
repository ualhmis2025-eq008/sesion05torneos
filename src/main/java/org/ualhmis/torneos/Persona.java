package org.ualhmis.torneos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Clase Persona para jugadores y entrenadores
class Persona {
    private String nombre;
    private String genero;
    private LocalDate fechaNacimiento;

    public Persona(String nombre, String genero, LocalDate fechaNacimiento) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (genero == null || genero.trim().isEmpty()) {
            throw new IllegalArgumentException("El género no puede estar vacío");
        }
        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula");
        }
        this.nombre = nombre;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() { return nombre; }
    public String getGenero() { return genero; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

    public int calcularEdad() {
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }
    
    
}
