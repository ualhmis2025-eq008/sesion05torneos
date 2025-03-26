package org.ualhmis.torneos;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

// Registro de partidos y validación de resultados


class TorneoTest {

    @Test
    void testRegistrarEquipoCorrectamente() {
        Torneo torneo = new Torneo("Liga Juvenil", "Fútbol", "Juvenil", "Masculino", "Liga");

        Entrenador entrenador = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 3, 10));
        Equipo equipo = new Equipo("Tigres", "Juvenil", "Masculino", entrenador);

        torneo.registrarEquipo(equipo);

        assertEquals(1, torneo.getEquipos().size());
    }

    @Test
    void testNoRegistrarEquipoDeDiferenteCategoria() {
        Torneo torneo = new Torneo("Liga Juvenil", "Fútbol", "Juvenil", "Masculino", "Liga");

        Entrenador entrenador = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 3, 10));
        Equipo equipo = new Equipo("Tigres", "Cadete", "Masculino", entrenador);

        assertThrows(IllegalArgumentException.class, () -> torneo.registrarEquipo(equipo));
    }

    @Test
    void testNoRegistrarEquipoDeDiferenteModalidad() {
        Torneo torneo = new Torneo("Liga Juvenil", "Fútbol", "Juvenil", "Masculino", "Liga");

        Entrenador entrenador = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 3, 10));
        Equipo equipo = new Equipo("Leonas", "Juvenil", "Femenino", entrenador);

        assertThrows(IllegalArgumentException.class, () -> torneo.registrarEquipo(equipo));
    }
}
