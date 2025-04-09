package org.ualhmis.torneos;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

// Restricciones en los equipos (jugadores de la misma categoría y modalidad)

class JugadorTest {

    @ParameterizedTest
    @MethodSource("proveedorJugadoresYCategorias")
    void testCategoriaPorEdadParametrizado(LocalDate fechaNacimiento, String categoriaEsperada) {
        Jugador jugador = new Jugador("Test", "Masculino", fechaNacimiento);
        assertEquals(categoriaEsperada, jugador.getCategoria());
    }

    private static Stream<Arguments> proveedorJugadoresYCategorias() {
        return Stream.of(
                Arguments.of(LocalDate.of(2015, 5, 10), "Infantil"),
                Arguments.of(LocalDate.of(2011, 3, 15), "Cadete"),
                Arguments.of(LocalDate.of(2010, 8, 22), "Juvenil"),
                Arguments.of(LocalDate.of(2005, 1, 30), "Junior"),
                Arguments.of(LocalDate.of(1998, 6, 5), "Absoluta")
        );
    }

    @ParameterizedTest
    @MethodSource("proveedorDatosInvalidos")
    void testCreacionJugadorInvalido(String nombre, String genero, LocalDate fechaNacimiento) {
        assertThrows(IllegalArgumentException.class, () -> new Jugador(nombre, genero, fechaNacimiento));
    }

    private static Stream<Arguments> proveedorDatosInvalidos() {
        return Stream.of(
                Arguments.of("", "Masculino", LocalDate.of(2010, 1, 1)),         // nombre vacío
                Arguments.of("Juan", "", LocalDate.of(2010, 1, 1)),              // género vacío
                Arguments.of("Juan", "Masculino", null),                         // fecha nula
                Arguments.of(null, "Femenino", LocalDate.of(2005, 3, 3)),        // nombre nulo
                Arguments.of("Ana", null, LocalDate.of(2007, 7, 7))              // género nulo
        );
    }

    @ParameterizedTest
    @MethodSource("proveedorEdadesCategorias")
    void testCategoriaPorEdadLimites(LocalDate fechaNacimiento, String categoriaEsperada) {
        Jugador jugador = new Jugador("Test", "Masculino", fechaNacimiento);
        assertEquals(categoriaEsperada, jugador.getCategoria());
    }

    private static Stream<Arguments> proveedorEdadesCategorias() {
        LocalDate hoy = LocalDate.now();
        return Stream.of(
            // Límites inferiores
            Arguments.of(hoy.minusYears(11).minusDays(1), "Infantil"),
            Arguments.of(hoy.minusYears(12).plusDays(1), "Cadete"),
            Arguments.of(hoy.minusYears(14).minusDays(1), "Cadete"),
            Arguments.of(hoy.minusYears(15).plusDays(1), "Juvenil"),
            Arguments.of(hoy.minusYears(17).minusDays(1), "Juvenil"),
            Arguments.of(hoy.minusYears(18).plusDays(1), "Junior"),
            Arguments.of(hoy.minusYears(20).minusDays(1), "Junior"),
            Arguments.of(hoy.minusYears(21).plusDays(1), "Absoluta"),
            
            // Casos exactos en el límite (día del cumpleaños)
            Arguments.of(hoy.minusYears(12), "Cadete"),
            Arguments.of(hoy.minusYears(15), "Juvenil"),
            Arguments.of(hoy.minusYears(18), "Junior"),
            Arguments.of(hoy.minusYears(21), "Absoluta"),
            
            // Casos extremos
            Arguments.of(hoy.minusYears(0), "Infantil"), // Recién nacido
            Arguments.of(hoy.minusYears(100), "Absoluta") // Persona mayor
        );
    }
    
    @Test
    void testMetodosHeredados() {
        LocalDate fechaNac = LocalDate.of(2010, 5, 15);
        Jugador jugador = new Jugador("Ana", "Femenino", fechaNac);
        
        assertEquals("Ana", jugador.getNombre());
        assertEquals("Femenino", jugador.getGenero());
        assertEquals(fechaNac, jugador.getFechaNacimiento());
        assertEquals(LocalDate.now().getYear() - 2010, jugador.calcularEdad());
    }
    
    @Test
    void testGetCategoria() {
        Jugador jugador = new Jugador("Luis", "Masculino", LocalDate.of(2008, 3, 10));
        assertEquals("Juvenil", jugador.getCategoria());
        
        // Verificar que no cambia después de llamar múltiples veces
        assertEquals("Juvenil", jugador.getCategoria());
        assertEquals("Juvenil", jugador.getCategoria());
    }
    
    @Test
    void testEdadMinima() {
        Jugador recienNacido = new Jugador("Bebe", "Masculino", LocalDate.now());
        assertEquals("Infantil", recienNacido.getCategoria());
    }

    @Test
    void testEdadMuyAvanzada() {
        Jugador mayor = new Jugador("Abuelo", "Masculino", LocalDate.now().minusYears(120));
        assertEquals("Absoluta", mayor.getCategoria());
    }
}
