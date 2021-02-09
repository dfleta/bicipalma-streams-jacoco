package domain.estacion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.bicicleta.Bicicleta;

public class EstacionTest {

    @Test
    public void constructorEstacionTest() {
        Estacion estacion = new Estacion(1, "Manacor", 6);
        assertNotNull(estacion);
        assertEquals("id: 1 \ndireccion: Manacor \nanclajes: 6", estacion.toString());
    }

    @Test
    public void anclajesLibresTest() {
        Estacion estacion = new Estacion(1, "Manacor", 6);
        assertEquals(6, estacion.anclajesLibres());

        estacion = new Estacion(1, "Manacor", 0);
        assertEquals(0, estacion.anclajesLibres());

        // cubrir las dos ramas de !a.isOcupado()
        estacion = new Estacion(1, "Manacor", 1);
        estacion.anclarBicicleta(new Bicicleta(999));
        assertEquals(0, estacion.anclajesLibres());
    }
    
}
