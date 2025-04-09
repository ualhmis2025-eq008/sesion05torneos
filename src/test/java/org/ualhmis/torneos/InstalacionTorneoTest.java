package org.ualhmis.torneos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class InstalacionTorneoTest {

    @ParameterizedTest
    @MethodSource("proveedorSettersGetters")
    void testSettersGetters(String nombreInicial, String tipoInicial, String deporteInicial,
                          String nombreNuevo, String tipoNuevo, String deporteNuevo,
                          int cantidadHorarios) {
        
        // Crear instalación inicial
        InstalacionDeportiva instalacion = new InstalacionDeportiva(nombreInicial, tipoInicial, deporteInicial);
        
        // Verificar valores iniciales
        assertEquals(nombreInicial, instalacion.getNombre());
        assertEquals(tipoInicial, instalacion.getTipo());
        assertEquals(deporteInicial, instalacion.getDeporteAdecuado());
        assertNotNull(instalacion.getHorariosOcupados());
        assertTrue(instalacion.getHorariosOcupados().isEmpty());

        // Crear lista de horarios para el test
        List<HorarioPartido> horarios = new ArrayList<>();
        LocalDateTime ahora = LocalDateTime.now();
        for (int i = 0; i < cantidadHorarios; i++) {
            horarios.add(new HorarioPartido(ahora.plusHours(i), ahora.plusHours(i + 1)));
        }

        // Modificar valores con setters
        instalacion.setNombre(nombreNuevo);
        instalacion.setTipo(tipoNuevo);
        instalacion.setDeporteAdecuado(deporteNuevo);
        instalacion.setHorariosOcupados(horarios);

        // Verificar nuevos valores
        assertEquals(nombreNuevo, instalacion.getNombre());
        assertEquals(tipoNuevo, instalacion.getTipo());
        assertEquals(deporteNuevo, instalacion.getDeporteAdecuado());
        assertEquals(cantidadHorarios, instalacion.getHorariosOcupados().size());
        
        // Verificar copia defensiva en getHorariosOcupados
        List<HorarioPartido> horariosObtenidos = instalacion.getHorariosOcupados();
        assertNotSame(horarios, horariosObtenidos, "Debería devolver una copia de la lista");
        assertEquals(horarios, horariosObtenidos, "El contenido de la lista debería ser igual");
    }
    
    private static Stream<Arguments> proveedorSettersGetters() {
        return Stream.of(
            Arguments.of("Cancha 1", "Campo", "Fútbol", "Cancha Principal", "Campo Premium", "Fútbol 11", 3),
            Arguments.of("Pabellón A", "Pabellón", "Baloncesto", "Pabellón Central", "Pabellón", "Baloncesto", 1),
            Arguments.of("Pista 1", "Pista", "Atletismo", "Pista Olímpica", "Pista", "Atletismo", 0)
        );
    }
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
            Arguments.of("Campo", "fútbol", "Fútbol", false),
            Arguments.of("Pabellón", "BALONCESTO", "Baloncesto", false)
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
            Arguments.of("Fútbol", "Fútbol", ahora, ahora, true)
        );
    }
    
    @ParameterizedTest
    @MethodSource("proveedorAgregarHorario")
    void testAgregarHorarioOcupado(String nombreInstalacion, int vecesAgregar) {
        InstalacionDeportiva instalacion = new InstalacionDeportiva(nombreInstalacion, "Campo", "Fútbol");
        LocalDateTime ahora = LocalDateTime.now();
        
        for (int i = 0; i < vecesAgregar; i++) {
            HorarioPartido horario = new HorarioPartido(
                ahora.plusHours(i * 2), 
                ahora.plusHours(i * 2 + 1)
            );
            instalacion.agregarHorarioOcupado(horario);
        }
        
        assertEquals(vecesAgregar, instalacion.getHorariosOcupados().size());
        
        // Verificar que no se pueden agregar horarios solapados
        if (vecesAgregar > 0) {
            HorarioPartido horarioSolapado = new HorarioPartido(
                ahora.plusMinutes(30), 
                ahora.plusHours(1).plusMinutes(30)
            );
            assertThrows(IllegalArgumentException.class, 
                () -> instalacion.agregarHorarioOcupado(horarioSolapado));
        }
    }
    
    private static Stream<Arguments> proveedorAgregarHorario() {
        return Stream.of(
            Arguments.of("Cancha Fútbol", 3),
            Arguments.of("Pabellón Baloncesto", 1),
            Arguments.of("Pista Atletismo", 0)
        );
    }

    
}