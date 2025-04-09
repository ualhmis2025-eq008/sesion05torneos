package org.ualhmis.torneos;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

// Registro de equipos en torneos con validación de categoría y modalidad

class PartidoTest {
	
	
    @Test
    void testRegistrarResultado() {
        Entrenador entrenador1 = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 3, 10));
        Entrenador entrenador2 = new Entrenador("Ana", "Femenino", LocalDate.of(1985, 6, 20));

        Equipo equipo1 = new Equipo("Tigres", "Juvenil", "Masculino", entrenador1);
        Equipo equipo2 = new Equipo("Leones", "Juvenil", "Masculino", entrenador2);

        Partido partido = new Partido(equipo1, equipo2);
        partido.registrarResultado(2, 1);

        assertEquals(2, partido.getGolesEquipo1());
        assertEquals(1, partido.getGolesEquipo2());
    }
}
