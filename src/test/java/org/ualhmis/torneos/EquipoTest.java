package org.ualhmis.torneos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class EquipoTest {

    // Test parametrizado para agregar jugadores con diferentes categorías
    @ParameterizedTest
    @MethodSource("proveedorJugadoresCategoria")
    void testAgregarJugador(String nombreEquipo, String categoriaEquipo, String modalidadEquipo,
                            String nombreJugador, String generoJugador, LocalDate fechaNacimientoJugador,
                            int cantidadEsperada) {
        Entrenador entrenador = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 3, 10));
        Equipo equipo = new Equipo(nombreEquipo, categoriaEquipo, modalidadEquipo, entrenador);

        Jugador jugador = new Jugador(nombreJugador, generoJugador, fechaNacimientoJugador);
        equipo.agregarJugador(jugador);

        assertEquals(cantidadEsperada, equipo.getJugadores().size());
    }

    private static Stream<Arguments> proveedorJugadoresCategoria() {
        return Stream.of(
                // Jugador de misma categoría (Juvenil)
                Arguments.of("Tigres", "Juvenil", "Masculino",
                        "Luis", "Masculino", LocalDate.of(2006, 7, 15),
                        0),

                // Jugador de diferente categoría (Infantil) - no debe agregarse
                Arguments.of("Tigres", "Juvenil", "Masculino",
                        "Pedro", "Masculino", LocalDate.of(2015, 5, 10),
                        0),

                // Jugador de categoría límite (justo antes de Juvenil) - debe agregarse
                Arguments.of("Juvenil", "Juvenil", "Femenino",
                        "Maria", "Femenino", LocalDate.of(2008, 1, 1),
                        1),

                // Jugador de misma categoría (Absoluta) - debe agregarse
                Arguments.of("Leones", "Absoluta", "Masculino",
                        "Juan", "Masculino", LocalDate.of(1990, 3, 20),
                        1),

                // Jugador de diferente modalidad - no debe agregarse (aunque la categoría coincida)
                Arguments.of("Águilas", "Junior", "Femenino",
                        "Laura", "Masculino", LocalDate.of(2002, 8, 12),
                        0)
        );
    }

    // Test parametrizado para asignar segundo entrenador
    @ParameterizedTest
    @MethodSource("proveedorSegundoEntrenador")
    void testAsignarSegundoEntrenador(String nombreEquipo, String categoriaEquipo, String modalidadEquipo,
                                      String nombreEntrenador1, String generoEntrenador1, LocalDate fechaEntrenador1,
                                      String nombreEntrenador2, String generoEntrenador2, LocalDate fechaEntrenador2,
                                      String nombreEsperado) {
        Entrenador entrenador1 = new Entrenador(nombreEntrenador1, generoEntrenador1, fechaEntrenador1);
        Entrenador entrenador2 = new Entrenador(nombreEntrenador2, generoEntrenador2, fechaEntrenador2);

        Equipo equipo = new Equipo(nombreEquipo, categoriaEquipo, modalidadEquipo, entrenador1);
        equipo.asignarSegundoEntrenador(entrenador2);

        assertNotNull(equipo.getSegundoEntrenador());
        assertEquals(nombreEsperado, equipo.getSegundoEntrenador().getNombre());
    }

    private static Stream<Arguments> proveedorSegundoEntrenador() {
        return Stream.of(
                Arguments.of("Tigres", "Juvenil", "Masculino",
                        "Carlos", "Masculino", LocalDate.of(1980, 3, 10),
                        "Ana", "Femenino", LocalDate.of(1985, 6, 20),
                        "Ana"),

                Arguments.of("Leones", "Absoluta", "Femenino",
                        "Marta", "Femenino", LocalDate.of(1975, 9, 15),
                        "Sofia", "Femenino", LocalDate.of(1982, 11, 30),
                        "Sofia"),

                Arguments.of("Águilas", "Cadete", "Masculino",
                        "Pedro", "Masculino", LocalDate.of(1988, 4, 22),
                        "Luis", "Masculino", LocalDate.of(1990, 7, 18),
                        "Luis")
        );
    }

    @ParameterizedTest
    @MethodSource("proveedorConstructorInvalido")
    void testConstructorInvalido(String nombre, String categoria, String modalidad, Entrenador entrenador, Class<? extends Exception> excepcionEsperada) {
        assertThrows(excepcionEsperada, () -> new Equipo(nombre, categoria, modalidad, entrenador));
    }

    private static Stream<Arguments> proveedorConstructorInvalido() {
        Entrenador entrenadorValido = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 1, 1));
        return Stream.of(
                Arguments.of(null, "Juvenil", "Masculino", entrenadorValido, IllegalArgumentException.class),
                Arguments.of("", "Juvenil", "Masculino", entrenadorValido, IllegalArgumentException.class),
                Arguments.of("  ", "Juvenil", "Masculino", entrenadorValido, IllegalArgumentException.class),
                Arguments.of("Tigres", null, "Masculino", entrenadorValido, IllegalArgumentException.class),
                Arguments.of("Tigres", "Juvenil", null, entrenadorValido, IllegalArgumentException.class),
                Arguments.of("Tigres", "Juvenil", "Masculino", null, IllegalArgumentException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("proveedorEquals")
    void testEquals(Object obj, boolean esperado) {
        Entrenador entrenador = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 1, 1));
        Equipo equipo = new Equipo("Tigres", "Juvenil", "Masculino", entrenador);
        assertEquals(esperado, equipo.equals(obj));
    }

    private static Stream<Arguments> proveedorEquals() {
        Entrenador entrenador = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 1, 1));
        Equipo equipo1 = new Equipo("Tigres", "Juvenil", "Masculino", entrenador);
        Equipo equipo2 = new Equipo("Tigres", "Juvenil", "Masculino", entrenador);
        Equipo equipo3 = new Equipo("Leones", "Juvenil", "Masculino", entrenador);
        Equipo equipo4 = new Equipo("Tigres", "Absoluta", "Masculino", entrenador);
        Equipo equipo5 = new Equipo("Tigres", "Juvenil", "Femenino", entrenador);

        return Stream.of(
                Arguments.of(null, false),
                Arguments.of("No soy un equipo", false),
                Arguments.of(equipo1, true),
                Arguments.of(equipo2, true),
                Arguments.of(equipo3, false),
                Arguments.of(equipo4, false),
                Arguments.of(equipo5, false)
        );
    }

    @Test
    void testEqualsMismoObjeto() {
        Entrenador entrenador = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 1, 1));
        Equipo equipo = new Equipo("Tigres", "Juvenil", "Masculino", entrenador);

        assertTrue(equipo.equals(equipo));
    }


    @Test
    void testToString() {
        Entrenador entrenador = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 1, 1));
        Entrenador entrenador2 = new Entrenador("Lucía", "Femenino", LocalDate.of(1985, 2, 2));

        Equipo equipo = new Equipo("Tigres", "Juvenil", "Masculino", entrenador);
        Jugador jugador = new Jugador("Luis", "Masculino", LocalDate.of(2006, 1, 1));
        equipo.agregarJugador(jugador);
        equipo.asignarSegundoEntrenador(entrenador2);

        String resultado = equipo.toString();
        assertTrue(resultado.contains("Tigres"));
        assertTrue(resultado.contains("Juvenil"));
        assertTrue(resultado.contains("Masculino"));
    }

    @Test
    void testAgregarJugadorDuplicado() {
        Entrenador entrenador = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 1, 1));
        Equipo equipo = new Equipo("Tigres", "Juvenil", "Masculino", entrenador);

        Jugador jugador = new Jugador("Luis", "Masculino", LocalDate.of(2008, 4, 1)); // tiene 17 años → categoría "Juvenil"

        equipo.agregarJugador(jugador);
        equipo.agregarJugador(jugador);

        assertEquals(1, equipo.getJugadores().size());
    }


    @Test
    void testAgregarJugadorDistintaCategoria() {
        Entrenador entrenador = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 1, 1));
        Equipo equipo = new Equipo("Tigres", "Juvenil", "Masculino", entrenador);

        Jugador jugador = new Jugador("Luis", "Masculino", LocalDate.of(2012, 1, 1)); // categoría ≠ Juvenil
        equipo.agregarJugador(jugador);

        assertEquals(0, equipo.getJugadores().size());
    }



    @Test
    void testSetJugadores() {
        Entrenador entrenador = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 1, 1));
        Equipo equipo = new Equipo("Tigres", "Juvenil", "Masculino", entrenador);

        Jugador jugador1 = new Jugador("Luis", "Masculino", LocalDate.of(2006, 1, 1));
        Jugador jugador2 = new Jugador("Ana", "Femenino", LocalDate.of(2005, 1, 1));
        List<Jugador> nuevosJugadores = List.of(jugador1, jugador2);

        equipo.setJugadores(nuevosJugadores);
        assertEquals(2, equipo.getJugadores().size());
        assertTrue(equipo.getJugadores().containsAll(nuevosJugadores));
    }

    @ParameterizedTest
    @MethodSource("proveedorSettersGetters")
    void testSettersGetters(String nombre, String categoria, String modalidad,
                            String nombre2, String categoria2, String modalidad2) {

        Entrenador entrenador1 = new Entrenador("Carlos", "Masculino", LocalDate.of(1980, 1, 1));
        Entrenador entrenador2 = new Entrenador("Lucía", "Femenino", LocalDate.of(1985, 2, 2));

        Equipo equipo = new Equipo(nombre, categoria, modalidad, entrenador1);

        equipo.setNombre(nombre2);
        equipo.setCategoria(categoria2);
        equipo.setModalidad(modalidad2);
        equipo.setEntrenador(entrenador2);
        equipo.setSegundoEntrenador(entrenador1); // Invirtiendo
        equipo.setJugadores(new ArrayList<>());

        assertEquals(nombre2, equipo.getNombre());
        assertEquals(categoria2, equipo.getCategoria());
        assertEquals(modalidad2, equipo.getModalidad());
        assertEquals(entrenador2, equipo.getEntrenador());
        assertEquals(entrenador1, equipo.getSegundoEntrenador());
        assertNotNull(equipo.getJugadores());
        assertTrue(equipo.getJugadores().isEmpty());
    }

    private static Stream<Arguments> proveedorSettersGetters() {
        return Stream.of(
                Arguments.of("Tigres", "Juvenil", "Masculino", "Águilas", "Cadete", "Femenino"),
                Arguments.of("Leones", "Infantil", "Femenino", "Panteras", "Absoluta", "Masculino")
        );
    }


}