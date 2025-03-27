package org.ualhmis.torneos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

class InstalacionTorneoTest {

    // Test parametrizado para validar instalaciones adecuadas para el deporte del torneo
    @ParameterizedTest
    @MethodSource("proveedorInstalacionesDeportes")
    void testInstalacionAdecuadaParaTorneo(String tipoInstalacion, String deporteInstalacion, 
                                         String deporteTorneo, boolean esperado) {
        // Configuración
        InstalacionDeportiva instalacion = new InstalacionDeportiva("Instalación 1", tipoInstalacion, deporteInstalacion);
        Sede sede = new Sede("Sede Principal", "Calle Principal 123");
        sede.agregarInstalacion(instalacion);
        Torneo torneo = new Torneo("Torneo Prueba", deporteTorneo, "Absoluta", "Masculino", "Liga");

        // Ejecución y verificación
        if (esperado) {
            assertDoesNotThrow(() -> torneo.setSede(sede));
            assertEquals(sede, torneo.getSede());
        } else {
            assertThrows(IllegalArgumentException.class, () -> torneo.setSede(sede));
        }
    }

    private static Stream<Arguments> proveedorInstalacionesDeportes() {
        return Stream.of(
            // Instalación adecuada para el deporte
            Arguments.of("Campo", "Fútbol", "Fútbol", true),
            Arguments.of("Pabellón", "Baloncesto", "Baloncesto", true),
            Arguments.of("Pista", "Atletismo", "Atletismo", true),
            
            // Instalación no adecuada para el deporte
            Arguments.of("Campo", "Fútbol", "Baloncesto", false),
            Arguments.of("Pabellón", "Baloncesto", "Voleibol", false),
            Arguments.of("Pista", "Atletismo", "Natación", false),
            
            // Casos límite con mayúsculas/minúsculas
            Arguments.of("Campo", "fútbol", "Fútbol", true),
            Arguments.of("Pabellón", "BALONCESTO", "Baloncesto", true)
        );
    }

    // Test parametrizado para asignación de partidos a instalaciones
    @ParameterizedTest
    @MethodSource("proveedorAsignacionPartidos")
    void testAsignacionPartidoAInstalacion(String deporteInstalacion, String modalidadEquipo, 
                                          LocalDateTime inicio, LocalDateTime fin, 
                                          boolean esperado) {
        // Configuración
        InstalacionDeportiva instalacion = new InstalacionDeportiva("Cancha 1", "Campo", deporteInstalacion);
        Entrenador entrenador = new Entrenador("Entrenador", "Masculino", LocalDate.of(1980, 1, 1));
        Equipo equipo1 = new Equipo("Equipo 1", "Absoluta", modalidadEquipo, entrenador);
        Equipo equipo2 = new Equipo("Equipo 2", "Absoluta", modalidadEquipo, entrenador);
        Partido partido = new Partido(equipo1, equipo2);

        // Ejecución y verificación
        if (esperado) {
            assertDoesNotThrow(() -> partido.asignarInstalacion(instalacion, inicio, fin));
            assertEquals(instalacion, partido.getInstalacion());
        } else {
            assertThrows(IllegalArgumentException.class, 
                       () -> partido.asignarInstalacion(instalacion, inicio, fin));
        }
    }

    private static Stream<Arguments> proveedorAsignacionPartidos() {
        LocalDateTime ahora = LocalDateTime.now();
        return Stream.of(
            // Asignación válida
            Arguments.of("Fútbol", "Fútbol", ahora, ahora.plusHours(2), true),
            Arguments.of("Baloncesto", "Baloncesto", ahora.plusDays(1), ahora.plusDays(1).plusHours(2), true),
            
            // Deporte no compatible
            Arguments.of("Fútbol", "Baloncesto", ahora, ahora.plusHours(2), false),
            
            // Horario inválido (fin antes de inicio)
            Arguments.of("Fútbol", "Fútbol", ahora.plusHours(2), ahora, false),
            
            // Duración cero
            Arguments.of("Fútbol", "Fútbol", ahora, ahora, false)
        );
    }
}