package org.ualhmis.torneos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

class PartidoTest {

    private Equipo equipo1;
    private Equipo equipo2;
    private InstalacionDeportiva instalacion;
    private Partido partido;
    private HorarioPartido horario;

    @BeforeEach
    void setUp() {
        Entrenador entrenador = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 1, 1));
        equipo1 = new Equipo("Águilas", "Juvenil", "Masculino", entrenador);
        equipo2 = new Equipo("Halcones", "Juvenil", "Masculino", entrenador);

        partido = new Partido(equipo1, equipo2);

        // Instalación válida
        instalacion = new InstalacionDeportiva("instalacion", "Pista", "Tenis");
        // Horario válido
        horario = new HorarioPartido(LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(3).plusHours(2));
    }

    @Test
    void testRegistrarResultado() {
        partido.registrarResultado(3, 2);
        assertEquals(3, partido.getGolesEquipo1());
        assertEquals(2, partido.getGolesEquipo2());
    }

    @Test
    void testGettersAndSetters() {
        partido.setEquipo1(equipo2);
        partido.setEquipo2(equipo1);
        partido.setGolesEquipo1(1);
        partido.setGolesEquipo2(1);
        partido.setHorario(horario);
        partido.setInstalacion(instalacion);
        
        assertEquals(equipo2, partido.getEquipo1());
        assertEquals(equipo1, partido.getEquipo2());
        assertEquals(1, partido.getGolesEquipo1());
        assertEquals(1, partido.getGolesEquipo2());
        assertEquals(horario, partido.getHorario());
        assertEquals(instalacion, partido.getInstalacion());
    }

    @Test
    void testAsignarInstalacionNula() {
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);
        LocalDateTime fin = inicio.plusHours(2);

        assertThrows(IllegalArgumentException.class, () -> {
            partido.asignarInstalacion(null, inicio, fin);
        });
    }

    @Test
    void testAsignarInstalacionNoDisponible() {
        // Instalación con un horario ya ocupado
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);
        LocalDateTime fin = inicio.plusHours(2);

        HorarioPartido ocupado = new HorarioPartido(inicio, fin);
        instalacion.agregarHorarioOcupado(ocupado);

        assertThrows(IllegalArgumentException.class, () -> {
            partido.asignarInstalacion(instalacion, inicio, fin);
        });
    }
    
    @Test
    void testAsignarInstalacionCorrecta() {
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);
        LocalDateTime fin = inicio.plusHours(2);
        partido.asignarInstalacion(instalacion, inicio, fin);

        assertEquals(instalacion, partido.getInstalacion());
        assertNotNull(partido.getHorario());
        assertEquals(inicio, partido.getHorario().getInicio());
        assertEquals(fin, partido.getHorario().getFin());
    }
}
