package org.ualhmis.torneos;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

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
    void testNoRegistrarEquipoRepetido() {
        Torneo torneo = new Torneo("Liga Juvenil", "Fútbol", "Juvenil", "Masculino", "Liga");
        Entrenador entrenador = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 3, 10));
        Equipo equipo = new Equipo("Tigres", "Juvenil", "Masculino", entrenador);

        torneo.registrarEquipo(equipo);
        torneo.registrarEquipo(equipo); // Intento duplicado

        assertEquals(1, torneo.getEquipos().size(), "No debe duplicarse el equipo");
    }

    @ParameterizedTest
    @MethodSource("equipoInvalidoProvider")
    void testRegistrarEquipoInvalido(String categoriaEquipo, String modalidadEquipo) {
        Torneo torneo = new Torneo("Liga Juvenil", "Fútbol", "Juvenil", "Masculino", "Liga");
        Entrenador entrenador = new Entrenador("Ana", "Femenino", LocalDate.of(1990, 5, 20));
        Equipo equipo = new Equipo("Fieras", categoriaEquipo, modalidadEquipo, entrenador);

        assertThrows(IllegalArgumentException.class, () -> torneo.registrarEquipo(equipo));
        
    }

    static Stream<Arguments> equipoInvalidoProvider() {
        return Stream.of(
            Arguments.of("Cadete", "Masculino"),  // categoría incorrecta
            Arguments.of("Juvenil", "Femenino"),   // modalidad incorrecta
            Arguments.of("Infantil", "Femenino")   // ambos incorrectos
        );
    }

    @Test
    void testGettersAndSetters() {
        Torneo torneo = new Torneo("Inicial", "Baloncesto", "Cadete", "Mixto", "Eliminatoria");

        torneo.setNombre("Nuevo Torneo");
        torneo.setDeporte("Voleibol");
        torneo.setCategoria("Juvenil");
        torneo.setModalidad("Femenino");
        torneo.setTipo("Liga");

        List<Equipo> lista = List.of();
        torneo.setEquipos(lista);

        assertEquals("Nuevo Torneo", torneo.getNombre());
        assertEquals("Voleibol", torneo.getDeporte());
        assertEquals("Juvenil", torneo.getCategoria());
        assertEquals("Femenino", torneo.getModalidad());
        assertEquals("Liga", torneo.getTipo());
        assertEquals(lista, torneo.getEquipos());
    }
}
