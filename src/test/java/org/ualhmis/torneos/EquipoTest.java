package org.ualhmis.torneos;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

// Creación de jugadores y cálculo automático de categoría

class EquipoTest {

    @Test
    void testAgregarJugadorCorrectamente() {
        Entrenador entrenador = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 3, 10));
        Equipo equipo = new Equipo("Tigres", "Juvenil", "Masculino", entrenador);

        Jugador jugador = new Jugador("Luis", "Masculino", LocalDate.of(2006, 7, 15)); // Juvenil
        equipo.agregarJugador(jugador);

        assertEquals(1, equipo.getJugadores().size());
    }

    @Test
    void testNoAgregarJugadorDeDiferenteCategoria() {
        Entrenador entrenador = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 3, 10));
        Equipo equipo = new Equipo("Tigres", "Juvenil", "Masculino", entrenador);

        Jugador jugador = new Jugador("Luis", "Masculino", LocalDate.of(2015, 5, 10)); // Infantil

        equipo.agregarJugador(jugador);

        assertEquals(0, equipo.getJugadores().size()); // No debe agregarse
    }

    @Test
    void testAsignarSegundoEntrenador() {
        Entrenador entrenador1 = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 3, 10));
        Entrenador entrenador2 = new Entrenador("Ana", "Femenino", LocalDate.of(1985, 6, 20));

        Equipo equipo = new Equipo("Tigres", "Juvenil", "Masculino", entrenador1);
        equipo.asignarSegundoEntrenador(entrenador2);

        assertNotNull(equipo.getSegundoEntrenador());
        assertEquals("Ana", equipo.getSegundoEntrenador().getNombre());
    }
}
