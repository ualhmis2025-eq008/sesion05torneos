package org.ualhmis.torneos;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

// Restricciones en los equipos (jugadores de la misma categoría y modalidad)

class JugadorTest {

    @Test
    void testCategoriaPorEdad() {
        Jugador jugador1 = new Jugador("Carlos", "Masculino", LocalDate.of(2015, 5, 10));
        assertEquals("Infantil", jugador1.getCategoria());

        Jugador jugador2 = new Jugador("Luis", "Masculino", LocalDate.of(2010, 3, 15));
        assertEquals("Cadete", jugador2.getCategoria());

        Jugador jugador3 = new Jugador("Ana", "Femenino", LocalDate.of(2005, 8, 22));
        assertEquals("Juvenil", jugador3.getCategoria());

        Jugador jugador4 = new Jugador("Pedro", "Masculino", LocalDate.of(2002, 1, 30));
        assertEquals("Junior", jugador4.getCategoria());

        Jugador jugador5 = new Jugador("Marta", "Femenino", LocalDate.of(1998, 6, 5));
        assertEquals("Absoluta", jugador5.getCategoria());
    }

    @Test
    void testCreacionJugadorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Jugador("", "Masculino", LocalDate.of(2010, 1, 1)));
        assertThrows(IllegalArgumentException.class, () -> new Jugador("Juan", "", LocalDate.of(2010, 1, 1)));
        assertThrows(IllegalArgumentException.class, () -> new Jugador("Juan", "Masculino", null));
    }
}
