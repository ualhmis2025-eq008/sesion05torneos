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
