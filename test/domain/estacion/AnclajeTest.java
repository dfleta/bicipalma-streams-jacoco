package domain.estacion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import domain.bicicleta.Bicicleta;

public class AnclajeTest {

    @Test
    public void anclarBiciTest() {
        Anclaje anclaje = new Anclaje();
        anclaje.anclarBici(new Bicicleta(911));
        assertTrue(anclaje.isOcupado());
    }

    @Test
    public void getBiciTest() {
        Anclaje anclaje = new Anclaje();
        anclaje.anclarBici(new Bicicleta(911));
        assertTrue(anclaje.isOcupado());
        anclaje.getBici();
        assertTrue(anclaje.isOcupado());
    }

    @Test
    public void liberarBiciTest() {
        Anclaje anclaje = new Anclaje();
        anclaje.anclarBici(new Bicicleta(911));
        assertTrue(anclaje.isOcupado());

        anclaje.liberarBici();
        assertFalse(anclaje.isOcupado());        
    }
    
}
