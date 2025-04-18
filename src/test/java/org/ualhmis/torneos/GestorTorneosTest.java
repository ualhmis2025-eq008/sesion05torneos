package org.ualhmis.torneos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class GestorTorneosTest {

    @Test
    public void testCrearTorneo() {
        GestorTorneos gestor = new GestorTorneos();
        gestor.crearTorneo("Torneo Primavera", "Fútbol", "Sub-18", "Individual", "Eliminación");

        List<Torneo> torneos = gestor.getTorneos();

        assertEquals(1, torneos.size());

        Torneo torneo = torneos.get(0);
        assertEquals("Torneo Primavera", torneo.getNombre());
        assertEquals("Fútbol", torneo.getDeporte());
        assertEquals("Sub-18", torneo.getCategoria());
        assertEquals("Individual", torneo.getModalidad());
        assertEquals("Eliminación", torneo.getTipo());
    }

    @Test
    public void testCrearMultiplesTorneos() {
        GestorTorneos gestor = new GestorTorneos();
        gestor.crearTorneo("Torneo 1", "Tenis", "Adultos", "Individual", "Liga");
        gestor.crearTorneo("Torneo 2", "Baloncesto", "Junior", "Equipos", "Eliminación");

        assertEquals(2, gestor.getTorneos().size());
    }
}
