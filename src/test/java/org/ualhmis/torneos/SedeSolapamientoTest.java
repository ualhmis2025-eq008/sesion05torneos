package org.ualhmis.torneos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

class SedeSolapamientoTest {

    // Test parametrizado para evitar solapamientos de horarios
    @ParameterizedTest
    @MethodSource("proveedorHorariosSolapados")
    void testSolapamientoHorarios(LocalDateTime inicio1, LocalDateTime fin1,
                                 LocalDateTime inicio2, LocalDateTime fin2,
                                 boolean esperadoSolapamiento) {
        // Configuración
        InstalacionDeportiva instalacion = new InstalacionDeportiva("Cancha 1", "Campo", "Fútbol");
        Entrenador entrenador = new Entrenador("Entrenador", "Masculino", LocalDate.of(1980, 1, 1));
        Equipo equipo1 = new Equipo("Equipo 1", "Absoluta", "Fútbol", entrenador);
        Equipo equipo2 = new Equipo("Equipo 2", "Absoluta", "Fútbol", entrenador);
        Equipo equipo3 = new Equipo("Equipo 3", "Absoluta", "Fútbol", entrenador);
        
        Partido partido1 = new Partido(equipo1, equipo2);
        Partido partido2 = new Partido(equipo1, equipo3);

        // Asignar primer partido
        partido1.asignarInstalacion(instalacion, inicio1, fin1);

        // Intentar asignar segundo partido
        if (esperadoSolapamiento) {
            assertThrows(IllegalArgumentException.class, 
                       () -> partido2.asignarInstalacion(instalacion, inicio2, fin2));
        } else {
            assertDoesNotThrow(() -> partido2.asignarInstalacion(instalacion, inicio2, fin2));
        }
    }

    private static Stream<Arguments> proveedorHorariosSolapados() {
        LocalDateTime ahora = LocalDateTime.now();
        return Stream.of(
            // Solapamiento total
            Arguments.of(ahora, ahora.plusHours(2), 
                       ahora, ahora.plusHours(2), 
                       true),
            
            // Solapamiento parcial (inicio durante otro partido)
            Arguments.of(ahora, ahora.plusHours(2), 
                       ahora.plusHours(1), ahora.plusHours(3), 
                       true),
            
            // Solapamiento parcial (fin durante otro partido)
            Arguments.of(ahora.plusHours(1), ahora.plusHours(3), 
                       ahora, ahora.plusHours(2), 
                       true),
            
            // Sin solapamiento (antes)
            Arguments.of(ahora.plusHours(3), ahora.plusHours(5), 
                       ahora, ahora.plusHours(2), 
                       false),
            
            // Sin solapamiento (después)
            Arguments.of(ahora, ahora.plusHours(2), 
                       ahora.plusHours(3), ahora.plusHours(5), 
                       false),
            
            // Justo después (no solapamiento)
            Arguments.of(ahora, ahora.plusHours(2), 
                       ahora.plusHours(2), ahora.plusHours(4), 
                       false),
            
            // Justo antes (no solapamiento)
            Arguments.of(ahora.plusHours(2), ahora.plusHours(4), 
                       ahora, ahora.plusHours(2), 
                       false)
        );
    }

    // Test parametrizado para gestión completa de sedes
    @ParameterizedTest
    @MethodSource("proveedorGestionSedes")
    void testGestionCompletaSede(String nombreSede, int cantidadInstalaciones, 
                                String[] tiposInstalacion, String[] deportesInstalacion,
                                boolean esperadoValido) {
        // Configuración
        Sede sede = new Sede(nombreSede, "Dirección " + nombreSede);
        
        // Agregar instalaciones
        for (int i = 0; i < cantidadInstalaciones; i++) {
            InstalacionDeportiva instalacion = new InstalacionDeportiva(
                "Instalación " + (i+1), 
                tiposInstalacion[i], 
                deportesInstalacion[i]
            );
            sede.agregarInstalacion(instalacion);
        }

        // Verificaciones
        assertEquals(cantidadInstalaciones, sede.getInstalaciones().size());
        
        if (esperadoValido) {
            assertDoesNotThrow(() -> {
                // Verificar que todas las instalaciones tienen tipo y deporte válidos
                for (InstalacionDeportiva inst : sede.getInstalaciones()) {
                    assertNotNull(inst.getTipo());
                    assertNotNull(inst.getDeporteAdecuado());
                    assertFalse(inst.getTipo().isEmpty());
                    assertFalse(inst.getDeporteAdecuado().isEmpty());
                }
            });
        } else {
            // Al menos una instalación no es válida
            assertThrows(IllegalArgumentException.class, () -> {
                for (InstalacionDeportiva inst : sede.getInstalaciones()) {
                    if (inst.getTipo().isEmpty() || inst.getDeporteAdecuado().isEmpty()) {
                        throw new IllegalArgumentException("Instalación inválida");
                    }
                }
            });
        }
    }

    private static Stream<Arguments> proveedorGestionSedes() {
        return Stream.of(
            // Sede válida con múltiples instalaciones
            Arguments.of("Polideportivo Central", 3, 
                        new String[]{"Campo", "Pabellón", "Pista"},
                        new String[]{"Fútbol", "Baloncesto", "Atletismo"},
                        true),
            
            // Sede válida con una sola instalación
            Arguments.of("Canchas Municipales", 1, 
                        new String[]{"Campo"},
                        new String[]{"Fútbol"},
                        true),
            
            // Sede con instalación inválida (tipo vacío)
            Arguments.of("Sede Inválida 1", 2, 
                        new String[]{"", "Pabellón"},
                        new String[]{"Fútbol", "Baloncesto"},
                        false),
            
            // Sede con instalación inválida (deporte vacío)
            Arguments.of("Sede Inválida 2", 2, 
                        new String[]{"Campo", "Pista"},
                        new String[]{"Fútbol", ""},
                        false)
        );
    }
}